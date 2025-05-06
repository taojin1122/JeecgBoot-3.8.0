//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.aspect;

import com.alibaba.fastjson.JSONObject;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.lifecycle.PostProcessFlowExecuteLifeCycle;
import com.yomahub.liteflow.slot.Slot;
import java.io.IOException;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.airag.common.vo.event.EventData;
import org.jeecg.modules.airag.common.vo.event.EventFlowData;
import org.jeecg.modules.airag.flow.context.FlowContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * FlowExecuteEventHandler 实现了 PostProcessFlowExecuteLifeCycle 接口，处理流程执行的生命周期切面
 *
 * 根据代码内容，这个类实现了 PostProcessFlowExecuteLifeCycle 接口，
 * 主要负责流程执行的前后处理和事件通知，
 *
 * 这个命名反映了类的主要职责：
 * 1、处理流程执行（FlowExecute）相关的事件
 * 2、实现了生命周期的切面（Aspect）功能
 * 3、负责处理事件（Event）通知
 * 这个名字能够清晰地表达类的用途和职责。
 */
@Component("flowExecuteAspect")
public class FlowExecuteEventHandler implements PostProcessFlowExecuteLifeCycle {
    private static final Logger logger = LoggerFactory.getLogger(FlowExecuteEventHandler.class);

    public FlowExecuteEventHandler() {
    }

    @Override
    public void postProcessBeforeFlowExecute(String chainId, Slot slot) {
        FlowContext flowContext = (FlowContext)slot.getContextBean(FlowContext.class);
        EventData eventData = new EventData(flowContext.getRequestId(), slot.getChainId(), "FLOW_STARTED", flowContext.getConversationId(), flowContext.getTopicId());
        EventFlowData eventFlowData = EventFlowData.builder().success(true).inputs(flowContext.getRequestDatas()).build();
        eventData.setData(eventFlowData);
        String eventDataStr = JSONObject.toJSONString(eventData);
        sendSseEvent(flowContext, eventDataStr, false);
        logger.info("[Flow-Process-Before]Event:{}", eventDataStr);
    }

    @Override
    public void postProcessAfterFlowExecute(String chainId, Slot slot) {
        LiteflowResponse var3 = LiteflowResponse.newMainResponse(slot);
        FlowContext var4 = (FlowContext)slot.getContextBean(FlowContext.class);
        EventData var5 = new EventData(var4.getRequestId(), slot.getChainId(), "FLOW_FINISHED", var4.getConversationId(), var4.getTopicId());
        EventFlowData var6 = EventFlowData.builder().success(var3.isSuccess()).executeSteps(var3.getExecuteStepStr()).message(var3.getMessage()).inputs(var4.getRequestDatas()).outputs(var4.getResult()).build();
        var5.setData(var6);
        if (oConvertUtils.isObjectNotEmpty(var4.getEventCallback())) {
            try {
                var4.getEventCallback().accept(var5);
            } catch (Throwable var8) {
                logger.error("[flow]run event callback fail:{}", var8.getMessage());
            }
        }

        String var7 = JSONObject.toJSONString(var5);
        sendSseEvent(var4, var7, true);
        logger.info("[Flow-Process-after]Event:{}", var7);
    }

    /**
     * 发送SSE事件
     * @param flowContext 流程上下文
     * @param eventData 事件数据JSON字符串
     * @param shouldComplete 是否需要完成SSE连接
     */
    private static void sendSseEvent(FlowContext flowContext, String eventData, boolean shouldComplete) {
        SseEmitter emitter = flowContext.getSseEmitter();
        if (null != emitter) {
            try {
                emitter.send(SseEmitter.event().data(eventData));
                if (shouldComplete) {
                    emitter.complete();
                }
            } catch (IOException var5) {
                logger.error("SSE send error", var5);
                emitter.completeWithError(var5);
            }
        }

    }
}
