package org.jeecg.modules.airag.app.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.output.FinishReason;
import dev.langchain4j.service.TokenStream;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.AssertUtils;
import org.jeecg.common.util.UUIDGenerator;
import org.jeecg.modules.airag.app.consts.Prompts;
import org.jeecg.modules.airag.app.entity.AiragApp;
import org.jeecg.modules.airag.app.mapper.AiragAppMapper;
import org.jeecg.modules.airag.app.service.IAiragAppService;
import org.jeecg.modules.airag.common.consts.AiragConsts;
import org.jeecg.modules.airag.common.handler.AIChatParams;
import org.jeecg.modules.airag.common.handler.IAIChatHandler;
import org.jeecg.modules.airag.common.utils.AiragLocalCache;
import org.jeecg.modules.airag.common.vo.event.EventData;
import org.jeecg.modules.airag.common.vo.event.EventFlowData;
import org.jeecg.modules.airag.common.vo.event.EventMessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Description: AI应用
 * @Author: jeecg-boot
 * @Date: 2025-02-26
 * @Version: V1.0
 */
@Slf4j
@Service
public class AiragAppServiceImpl extends ServiceImpl<AiragAppMapper, AiragApp> implements IAiragAppService {

    @Autowired
    IAIChatHandler aiChatHandler;

    @Override
    public Object generatePrompt(String prompt, boolean blocking) {
        AssertUtils.assertNotEmpty("请输入提示词", prompt);
        List<ChatMessage> messages = Arrays.asList(new SystemMessage(Prompts.GENERATE_LLM_PROMPT), new UserMessage(prompt));

        AIChatParams params = new AIChatParams();
//        topNumber | 返回最相关的前5条内容（Top5）。比如知识库检索、向量搜索，选最接近的5条。
//        similarity | 相似度阈值，比如至少相似度要达到0.75（75%）以上，才认为是“相关”。
//        maxMsgNumber | 限制最多使用最近4条对话历史，比如上下文记忆的长度限制。
        // 	温度系数，控制回答的随机性（值越高越随机，越低越确定）	0.0 很保守；1.0 很发散
        params.setTemperature(0.8);
        // 核采样，只在累积概率前P 的词里随机选（常和 temperature 配合）    越低越集中（更确定），越高越发散
        params.setTopP(0.9);
        // 出现惩罚，惩罚生成已经提到过的新主题词	数值大，AI更倾向说新的内容
        params.setPresencePenalty(0.1);
        // 频率惩罚，惩罚重复率高的词，减少啰嗦	数值大，AI说话更简洁，不重复
        params.setFrequencyPenalty(0.1);
        if(blocking){
            String promptValue = aiChatHandler.completionsByDefaultModel(messages, params);
            if (promptValue == null || promptValue.isEmpty()) {
                return Result.error("生成失败");
            }
            return Result.OK("success", promptValue);
        }else{
            SseEmitter emitter = new SseEmitter(-0L);
            // 异步运行(流式)
            TokenStream tokenStream = aiChatHandler.chatByDefaultModel(messages, params);
            /**
             * 是否正在思考
             */
            AtomicBoolean isThinking = new AtomicBoolean(false);
            String requestId = UUIDGenerator.generate();
            // ai聊天响应逻辑
            tokenStream.onNext((String resMessage) -> {
                        // 兼容推理模型
                        if ("<think>".equals(resMessage)) {
                            isThinking.set(true);
                            resMessage = "> ";
                        }
                        if ("</think>".equals(resMessage)) {
                            isThinking.set(false);
                            resMessage = "\n\n";
                        }
                        if (isThinking.get()) {
                            if (null != resMessage && resMessage.contains("\n")) {
                                resMessage = "\n> ";
                            }
                        }
                        EventData eventData = new EventData(requestId, null, EventData.EVENT_MESSAGE);
                        EventMessageData messageEventData = EventMessageData.builder()
                                .message(resMessage)
                                .build();
                        eventData.setData(messageEventData);
                        try {
                            String eventStr = JSONObject.toJSONString(eventData);
                            log.debug("[AI应用]接收LLM返回消息:{}", eventStr);
                            emitter.send(SseEmitter.event().data(eventStr));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .onComplete((responseMessage) -> {
                        // 记录ai的回复
                        AiMessage aiMessage = responseMessage.content();
                        FinishReason finishReason = responseMessage.finishReason();
                        String respText = aiMessage.text();
                        if (FinishReason.STOP.equals(finishReason) || null == finishReason) {
                            // 正常结束
                            EventData eventData = new EventData(requestId, null, EventData.EVENT_MESSAGE_END);
                            try {
                                log.debug("[AI应用]接收LLM返回消息完成:{}", respText);
                                emitter.send(SseEmitter.event().data(eventData));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            closeSSE(emitter, eventData);
                        } else if (FinishReason.TOOL_EXECUTION.equals(finishReason)) {
                            // 需要执行工具
                            // TODO author: chenrui for: date:2025/3/7
                        } else {
                            // 异常结束
                            log.error("调用模型异常:" + respText);
                            if (respText.contains("insufficient Balance")) {
                                respText = "大预言模型账号余额不足!";
                            }
                            EventData eventData = new EventData(requestId, null, EventData.EVENT_FLOW_ERROR);
                            eventData.setData(EventFlowData.builder().success(false).message(respText).build());
                            closeSSE(emitter, eventData);
                        }
                    })
                    .onError((Throwable error) -> {
                        // sse
                        String errMsg = "调用大模型接口失败:" + error.getMessage();
                        log.error(errMsg, error);
                        EventData eventData = new EventData(requestId, null, EventData.EVENT_FLOW_ERROR);
                        eventData.setData(EventFlowData.builder().success(false).message(errMsg).build());
                        closeSSE(emitter, eventData);
                    })
                    .start();
            return emitter;
        }
    }

    private static void closeSSE(SseEmitter emitter, EventData eventData) {
        try {
            // 发送完成事件
            emitter.send(SseEmitter.event().data(eventData));
        } catch (IOException e) {
            log.error("终止会话时发生错误", e);
        } finally {
            // 从缓存中移除emitter
            AiragLocalCache.remove(AiragConsts.CACHE_TYPE_SSE, eventData.getRequestId());
            // 关闭emitter
            emitter.complete();
        }
    }
}
