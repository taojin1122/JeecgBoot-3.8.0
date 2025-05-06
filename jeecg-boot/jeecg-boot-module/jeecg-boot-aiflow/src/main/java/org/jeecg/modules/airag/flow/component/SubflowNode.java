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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.airag.flow.component.base.FlowNodeHelper;
import org.jeecg.modules.airag.flow.context.FlowContext;
import org.jeecg.modules.airag.flow.exception.FlowNodeHelperException;
import org.jeecg.modules.airag.flow.service.IAiragFlowService;
import org.jeecg.modules.airag.flow.vo.api.FlowRunParams;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowNodeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 这是一个子流程节点组件
 */
@Component("subflow")
public class SubflowNode extends FlowNodeHelper {
    private static final Logger logger = LoggerFactory.getLogger(SubflowNode.class);
    @Autowired
    IAiragFlowService airagFlowService;

    public SubflowNode() {
    }

    @LiteflowMethod(
        value = LiteFlowMethodEnum.PROCESS,
        nodeType = NodeTypeEnum.COMMON
    )
    public void processSubflow(NodeComponent component) {
        logger.info("节点开始-sub");
        FlowContext context = getFlowContext(component);
        FlowNodeConfig nodeConfig = getNodeConfig(component);

        // 验证节点配置
        if (oConvertUtils.isObjectEmpty(nodeConfig.getOptions())) {
            throw new FlowNodeHelperException(nodeConfig.getNodeId(),
                    nodeConfig.getText(), "节点配置错误");
        }

        JSONObject options = JSONObject.parseObject(JSONObject.toJSONString(nodeConfig.getOptions()));
        String subflowId = options.getString("subflowId");

        if (oConvertUtils.isEmpty(subflowId)) {
            throw new FlowNodeHelperException(nodeConfig.getNodeId(),
                    nodeConfig.getText(), "请选择子流程");
        }

        // 准备子流程参数
        FlowRunParams runParams = new FlowRunParams();
        runParams.setFlowId(subflowId);
        runParams.setResponseMode("blocking");

        // 收集输入参数
        HashMap<String, Object> inputParams = new HashMap<>();
        Object history = context.getRequestValue("history");
        inputParams.put("history", history);

        // 处理配置的输入参数
        List<FlowNodeConfig.NodeParam> configParams = nodeConfig.getInputParams();
        for (FlowNodeConfig.NodeParam param : configParams) {
            Object value = context.getNodeFieldValue(param.getNodeId(), param.getField());
            inputParams.put(param.getName(), value);
        }

        runParams.setInputParams(inputParams);
        runParams.setHttpRequest(context.getHttpRequest());

        // 执行子流程
        Result result = (Result)airagFlowService.runFlow(runParams);
        if (!result.isSuccess()) {
            throw new FlowNodeHelperException(nodeConfig.getNodeId(),
                    nodeConfig.getText(), "子流程执行失败:" + result.getMessage());
        }

        // 处理子流程结果
        Object resultData = result.getResult();
        if (resultData instanceof String) {
            context.setNodeFieldValue(nodeConfig.getNodeId(), "outputText", resultData);
        } else {
            Map<String, Object> resultMap = (Map<String, Object>) resultData;
            resultMap.forEach((key, value) -> {
                context.setNodeFieldValue(nodeConfig.getNodeId(), key, value);
            });
        }
    }
}
