//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.component;

import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import java.util.Arrays;
import java.util.List;
import org.jeecg.common.config.TenantContext;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.airag.flow.component.base.FlowNodeHelper;
import org.jeecg.modules.airag.flow.context.FlowContext;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowNodeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 这是一个开始节点组件
 */
@Component("start")
public class StartNode extends FlowNodeHelper {
    private static final Logger logger = LoggerFactory.getLogger(StartNode.class);
    private static final List<String> RESERVED_FIELDS = Arrays.asList("content", "history");

    public StartNode() {
    }

    @LiteflowMethod(
        value = LiteFlowMethodEnum.PROCESS,
        nodeType = NodeTypeEnum.COMMON
    )
    public void processStart(NodeComponent component) {
        logger.info("节点开始-begin");
        FlowNodeConfig nodeConfig = getNodeConfig(component);
        FlowContext context = getFlowContext(component);

        // 设置租户上下文
        if (oConvertUtils.isNotEmpty(context.getTenantId())) {
            TenantContext.setTenant(context.getTenantId());
        }

        List<FlowNodeConfig.NodeParam> inputParams = nodeConfig.getInputParams();

        // 处理预定义字段
        for (String reservedField : RESERVED_FIELDS) {
            Object fieldValue = context.getRequestValue(reservedField);
            context.setNodeFieldValue(nodeConfig.getNodeId(), reservedField, fieldValue);
        }

        // 处理配置的输入参数
        for (FlowNodeConfig.NodeParam param : inputParams) {
            Object paramValue = context.getRequestValue(param.getField());
            context.setNodeFieldValue(nodeConfig.getNodeId(), param.getField(), paramValue);
        }
    }
}
