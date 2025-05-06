//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.component.base;

import com.yomahub.liteflow.core.NodeComponent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.airag.flow.context.FlowContext;
import org.jeecg.modules.airag.flow.exception.FlowNodeHelperException;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowNode;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowNodeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 根据代码内容来看，这个类是一个抽象类，主要处理流程节点（FlowNode）的配置和数据处理，
 * 包含了节点组件（NodeComponent）的各种工具方法。
 *
 * 包含了大量静态工具方法
 * 主要处理 FlowNode 和 NodeComponent 相关的操作
 * 涉及配置（FlowNodeConfig）的获取和处理
 * 处理节点间的数据传递
 *
 */
public abstract class FlowNodeHelper {
    private static final Logger logger = LoggerFactory.getLogger(FlowNodeHelper.class);

    public FlowNodeHelper() {
    }

    /**
     * // 原 IAiragFlowServiceImpl()
     * @param component
     * @return
     */
    public static FlowNode getNodeByComponent(NodeComponent component) {
        String tag = component.getTag();
        if (StringUtils.isEmpty(tag)) {
            tag = component.getNodeId();
        }

        return getNodeByComponentAndTag(component, tag);
    }

    /**
     * // 原 IAiragFlowAiGenServiceImpl()
     * @param component
     * @param tag
     * @return
     */
    public static FlowNode getNodeByComponentAndTag(NodeComponent component, String tag) {
        Map<String, FlowNode> flowNodes = getFlowContext(component).getFlowNodes();
        return null != flowNodes && !flowNodes.isEmpty() ? (FlowNode)flowNodes.get(tag) : null;
    }

    /**
     * // 原 HttpNode()
     * @param component
     * @return
     */
    public static FlowNodeConfig getNodeConfig(NodeComponent component) {
        FlowNode flowNode = getNodeByComponent(component);
        if (null == flowNode) {
            logger.error("获取节点配置信息失败:nodeId:{},nodeTag::{},nodeTag:{}", component.getNodeId(), component.getTag());
            throw new FlowNodeHelperException(component.getNodeId(), "", "获取节点配置信息失败");
        } else {
            FlowNodeConfig flowNodeConfig = flowNode.getProperties();
            if (null == flowNodeConfig) {
                logger.error("获取节点配置信息失败:nodeId:{},nodeTag:{}", component.getNodeId(), component.getTag());
                throw new FlowNodeHelperException(component.getNodeId(), "", "获取节点配置信息失败");
            } else {
                flowNodeConfig.setNodeId(flowNode.getId());
                flowNodeConfig.setNodeType(flowNode.getType());
                return flowNodeConfig;
            }
        }
    }

    /**
     * // 原 KnowledgeNode()
     * @param component
     * @return
     */
    public static Map<String, Object> getRequestData(NodeComponent component) {
        return getFlowContext(component).getRequestDatas();
    }

    /**
     *  // 原 LLMNode()
     * @param component
     * @return
     */
    public static FlowContext getFlowContext(NodeComponent component) {
        return (FlowContext)component.getContextBean(FlowContext.class);
    }

    /**
     *   // 原 IAiragFlowAiGenServiceImpl()
     * @param config
     * @param context
     * @return
     */
    public static Map<String, Object> getInputParameters(FlowNodeConfig config, FlowContext context) {
        HashMap var2 = new HashMap();
        List var3 = config.getInputParams();
        Iterator var4 = var3.iterator();

        while(var4.hasNext()) {
            FlowNodeConfig.NodeParam nodeParam = (FlowNodeConfig.NodeParam)var4.next();
            Object var6;
            if ("start".equalsIgnoreCase(config.getNodeType())) {
                var6 = context.getContextValue(nodeParam.getField());
                var2.put(nodeParam.getField(), var6);
            } else {
                var6 = context.getNodeFieldValue(nodeParam.getNodeId(), nodeParam.getField());
                var2.put(nodeParam.getNodeId() + "." + nodeParam.getField(), var6);
            }
        }

        return var2;
    }

    /**
     * // 原 IAiragFlowServiceImpl()
     * @param config
     * @param context
     * @return
     */
    public static Object getOutputParameters(FlowNodeConfig config, FlowContext context) {
        if ("end".equalsIgnoreCase(config.getNodeType())) {
            return context.getResult();
        } else {
            HashMap var2 = new HashMap();
            List var3 = config.getOutputParams();
            Iterator iterator = var3.iterator();

            while(iterator.hasNext()) {
                FlowNodeConfig.NodeParam param = (FlowNodeConfig.NodeParam)iterator.next();
                Map<String, Object> var6 = context.getContextDatas();
                String var7 = config.getNodeId() + ".";
                var6.forEach((var2x, var3x) -> {
                    if (var2x.startsWith(var7)) {
                        var2.put(var2x, var3x);
                    }

                });
            }

            return var2;
        }
    }

    /**
     *  // 原 IAiragFlowAiGenServiceImpl()
     * @param config
     * @param nextNode
     * @return
     */
    protected String formatNextNodeTag(FlowNodeConfig config, String nextNode) {
        if (oConvertUtils.isEmpty(nextNode)) {
            logger.error("{},{}未指定下一节点", config.getNodeId(), config.getText());
            throw new FlowNodeHelperException(config.getNodeId(), config.getText(), "未指定下一节点");
        } else {
            return nextNode.startsWith("tag:") ? nextNode : "tag:" + nextNode;
        }
    }
}
