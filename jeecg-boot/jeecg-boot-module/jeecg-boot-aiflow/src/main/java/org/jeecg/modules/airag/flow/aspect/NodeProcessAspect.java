//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.aspect;

import com.alibaba.fastjson.JSONObject;
import com.yomahub.liteflow.aop.ICmpAroundAspect;
import com.yomahub.liteflow.core.NodeComponent;

import java.io.IOException;

import org.jeecg.modules.airag.common.vo.event.EventData;
import org.jeecg.modules.airag.common.vo.event.EventFlowNodeData;
import org.jeecg.modules.airag.flow.context.FlowContext;
import org.jeecg.modules.airag.flow.component.base.FlowNodeHelper;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowNodeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 *
 * NodeProcessAspect 实现了 ICmpAroundAspect 接口，处理节点执行的切面逻辑
 *
 *
 * 根据代码内容，这个类实现了 ICmpAroundAspect 接口，主要负责节点处理的切面和事件通知
 * <p>
 * 这个命名合适因为：
 * 1、实现了组件处理的切面（Aspect）功能
 * 2、处理节点（Node）的生命周期事件
 * 3、与组件注解名 nodeProcessAspect 保持一致
 * 4、遵循了切面类的常用命名规范（以Aspect结尾）
 */
@Component("nodeProcessAspect")
public class NodeProcessAspect implements ICmpAroundAspect {
    private static final Logger logger = LoggerFactory.getLogger(NodeProcessAspect.class);

    public NodeProcessAspect() {
    }

    @Override
    public void beforeProcess(NodeComponent cmp) {
        EventData var2 = createNodeEventData(cmp, "NODE_STARTED");
        String var3 = JSONObject.toJSONString(var2);
        sendSseEvent(cmp, var3);
        logger.info("[Node-Process-Before]Event:{}", var3);
    }

    @Override
    public void afterProcess(NodeComponent cmp) {
    }

    @Override
    public void onSuccess(NodeComponent cmp) {
        EventData var2 = createNodeEventData(cmp, "NODE_FINISHED");
        EventFlowNodeData var3 = (EventFlowNodeData) var2.getData();
        var3.setSuccess(true);
        String var4 = JSONObject.toJSONString(var2);
        sendSseEvent(cmp, var4);
        logger.info("[Node-Process-After]Event:{}", JSONObject.toJSONString(var2));
    }

    @Override
    public void onError(NodeComponent cmp, Exception e) {
        EventData var3 = createNodeEventData(cmp, "NODE_FINISHED");
        EventFlowNodeData var4 = (EventFlowNodeData) var3.getData();
        var4.setSuccess(false);
        var4.setMessage(e.getMessage());
        String var5 = JSONObject.toJSONString(var3);
        sendSseEvent(cmp, var5);
        logger.info("[Node-Process-Error]Event:{}", JSONObject.toJSONString(var3));
    }

    /**
     * 创建节点事件数据
     *
     * @param component 节点组件
     * @param eventType 事件类型
     * @return 事件数据对象
     */
    private static EventData createNodeEventData(NodeComponent component, String eventType) {
        FlowContext flowContext = (FlowContext) component.getContextBean(FlowContext.class);
        EventData eventData = new EventData(flowContext.getRequestId(), component.getChainId(),
                eventType, flowContext.getConversationId(), flowContext.getTopicId());

        FlowNodeConfig flowNodeConfig = FlowNodeHelper.getNodeConfig(component);
        EventFlowNodeData eventFlowNodeData = EventFlowNodeData.builder().id(flowNodeConfig.getNodeId()).type(flowNodeConfig.getNodeType())
                .text(flowNodeConfig.getText())
                .inputs(FlowNodeHelper.getInputParameters(flowNodeConfig, flowContext))
                .outputs(FlowNodeHelper.getOutputParameters(flowNodeConfig, flowContext)).build();
        eventData.setData(eventFlowNodeData);
        return eventData;
    }

    /**
     * 发送SSE事件
     *
     * @param component 节点组件
     * @param eventData 事件数据JSON字符串
     */
    private static void sendSseEvent(NodeComponent component, String eventData) {
        FlowContext var2 = (FlowContext) component.getContextBean(FlowContext.class);
        SseEmitter var3 = var2.getSseEmitter();
        if (null != var3) {
            try {
                var3.send(SseEmitter.event().data(eventData));
            } catch (IOException var5) {
                logger.error("SSE send error", var5);
                var3.completeWithError(var5);
            }
        }

    }
}
