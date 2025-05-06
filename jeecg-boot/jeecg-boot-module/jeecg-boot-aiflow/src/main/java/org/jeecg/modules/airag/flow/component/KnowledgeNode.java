//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;

import java.util.List;
import java.util.stream.Collectors;

import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.airag.common.handler.IEmbeddingHandler;
import org.jeecg.modules.airag.common.vo.knowledge.KnowledgeSearchResult;
import org.jeecg.modules.airag.flow.component.base.FlowNodeHelper;
import org.jeecg.modules.airag.flow.context.FlowContext;
import org.jeecg.modules.airag.flow.exception.FlowNodeHelperException;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowNodeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 这是一个知识库节点组件
 */
@Component("knowledge")
public class KnowledgeNode extends FlowNodeHelper {
    private static final Logger logger = LoggerFactory.getLogger(KnowledgeNode.class);
    public static final String RESULT_DATA_FIELD = "data";
    public static final String RESULT_DOCUMENTS_FIELD = "documents";
    @Autowired
    IEmbeddingHandler embeddingHandler;

    public KnowledgeNode() {
    }

    @LiteflowMethod(
            value = LiteFlowMethodEnum.PROCESS,
            nodeType = NodeTypeEnum.COMMON
    )
    public void processKnowledge(NodeComponent var1) {
        logger.info("节点开始-knowledge");
        FlowContext flowContext = getFlowContext(var1);
        FlowNodeConfig flowNodeConfig = getNodeConfig(var1);
        List var4 = flowNodeConfig.getInputParams();
        if (oConvertUtils.isObjectEmpty(var4)) {
            throw new FlowNodeHelperException(flowNodeConfig.getNodeId(), flowNodeConfig.getText(), "必须选择输入变量");
        } else {
            FlowNodeConfig.NodeParam var5 = (FlowNodeConfig.NodeParam) var4.get(0);
            Object var6 = flowContext.getNodeFieldValue(var5.getNodeId(), var5.getField());
            if (oConvertUtils.isObjectEmpty(flowNodeConfig.getOptions())) {
                throw new FlowNodeHelperException(flowNodeConfig.getNodeId(), flowNodeConfig.getText(), "节点配置错误");
            } else {
                JSONObject var7 = JSONObject.parseObject(JSONObject.toJSONString(flowNodeConfig.getOptions()));
                Integer var8 = var7.getInteger("topNumber");
                Double var9 = var7.getDouble("similarity");
                JSONArray var10 = var7.getJSONArray("knowIds");
                if (oConvertUtils.isObjectEmpty(var10)) {
                    throw new FlowNodeHelperException(flowNodeConfig.getNodeId(), flowNodeConfig.getText(), "请选择知识库");
                } else {
                    List var11 = (List) var10.stream().map(Object::toString).collect(Collectors.toList());
                    KnowledgeSearchResult var12 = this.embeddingHandler.embeddingSearch(var11, var6.toString(), var8, var9);
                    flowContext.setNodeFieldValue(flowNodeConfig.getNodeId(), "data", var12.getData());
                    flowContext.setNodeFieldValue(flowNodeConfig.getNodeId(), "documents", var12.getData());
                }
            }
        }
    }
}
