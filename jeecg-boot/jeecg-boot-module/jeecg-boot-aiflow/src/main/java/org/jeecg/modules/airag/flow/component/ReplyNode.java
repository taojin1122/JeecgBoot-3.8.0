//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.component;

import com.alibaba.fastjson.JSONObject;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.airag.common.vo.event.EventData;
import org.jeecg.modules.airag.common.vo.event.EventMessageData;
import org.jeecg.modules.airag.flow.component.base.FlowNodeHelper;
import org.jeecg.modules.airag.flow.context.FlowContext;
import org.jeecg.modules.airag.flow.consts.FlowConsts;
import org.jeecg.modules.airag.flow.exception.FlowNodeHelperException;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowNodeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * 这是一个回复节点组件
 */
@Component("reply")
public class ReplyNode extends FlowNodeHelper {
    private static final Logger logger = LoggerFactory.getLogger(ReplyNode.class);

    public ReplyNode() {
    }

    @LiteflowMethod(
        value = LiteFlowMethodEnum.PROCESS,
        nodeType = NodeTypeEnum.COMMON
    )
    public void processReply(NodeComponent component) {
        logger.info("节点开始-http");
        FlowContext flowContext = getFlowContext(component);
        FlowNodeConfig flowNodeConfig = getNodeConfig(component);

        // 收集输入参数
        HashMap inputValues = new HashMap();
        List<FlowNodeConfig.NodeParam> inputParams = flowNodeConfig.getInputParams();
        Iterator iterator = inputParams.iterator();

        while(iterator.hasNext()) {
            FlowNodeConfig.NodeParam var7 = (FlowNodeConfig.NodeParam)iterator.next();
            Object var8 = flowContext.getNodeFieldValue(var7.getNodeId(), var7.getField());
            inputValues.put(var7.getName(), var8);
        }

        if (oConvertUtils.isObjectEmpty(flowNodeConfig.getOptions())) {
            throw new FlowNodeHelperException(flowNodeConfig.getNodeId(), flowNodeConfig.getText(), "节点配置错误");
        } else {
            JSONObject options = JSONObject.parseObject(JSONObject.toJSONString(flowNodeConfig.getOptions()));
            SseEmitter emitter = flowContext.getSseEmitter();
            // 处理SSE消息发送
            if (null != emitter) {
                String messageTemplate = options.getString("content");
                if (oConvertUtils.isNotEmpty(messageTemplate)) {
                    // 替换变量
                    Matcher matcher = FlowConsts.VAR_PLACEHOLDER_PATTERN.matcher(messageTemplate);
                    StringBuffer messageContent = new StringBuffer();

                    while(matcher.find()) {
                        String varName = matcher.group(1);
                        Object varValue = oConvertUtils.isObjectNotEmpty(inputValues) ? inputValues.get(varName) : null;
                        matcher.appendReplacement(messageContent, varValue != null ? Matcher.quoteReplacement(varValue.toString()) : "");
                    }

                    matcher.appendTail(messageContent);
                    // 构建事件数据
                    EventData eventData = new EventData(flowContext.getRequestId(), component.getChainId(),
                            "MESSAGE", flowContext.getConversationId(), flowContext.getTopicId());

                    EventMessageData messageData = EventMessageData.builder().fromNodeId(flowNodeConfig.getNodeId())
                            .message(messageContent.toString()).build();
                    eventData.setData(messageData);

                    // 发送SSE消息
                    try {
                        emitter.send(SseEmitter.event().data(JSONObject.toJSONString(eventData)));
                    } catch (IOException var14) {
                        throw new RuntimeException(var14);
                    }
                }
            }

        }
    }
}
