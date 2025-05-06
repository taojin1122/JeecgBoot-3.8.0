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
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jeecg.common.util.AssertUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.airag.common.handler.AIChatParams;
import org.jeecg.modules.airag.common.handler.IAIChatHandler;
import org.jeecg.modules.airag.flow.context.FlowContext;
import org.jeecg.modules.airag.flow.component.base.FlowNodeHelper;
import org.jeecg.modules.airag.flow.exception.FlowNodeHelperException;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowNodeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自定义节点组件
 * 是一个分类器节点（Classifier）组件
 */
@Component("classifier")
public class ClassifierNode extends FlowNodeHelper {
    private static final Logger logger = LoggerFactory.getLogger(ClassifierNode.class);
    @Autowired
    IAIChatHandler aiChatHandler;

    public ClassifierNode() {
    }

    /**
     * // 原 IAiragFlowAiGenServiceImpl() 表明这是处理分类的主方法
     * @param component
     * @return
     */
    @LiteflowMethod(
        value = LiteFlowMethodEnum.PROCESS_SWITCH,
        nodeType = NodeTypeEnum.SWITCH
    )
    public String processClassification(NodeComponent component) {
        logger.info("节点开始-classifier");
        FlowNodeConfig flowNodeConfig = getNodeConfig(component);
        List var3 = flowNodeConfig.getInputParams();
        if (oConvertUtils.isObjectEmpty(flowNodeConfig.getOptions())) {
            throw new FlowNodeHelperException(flowNodeConfig.getNodeId(), flowNodeConfig.getText(), "节点配置错误");
        } else {
            JSONObject var4 = JSONObject.parseObject(JSONObject.toJSONString(flowNodeConfig.getOptions()));
            JSONObject modelConfig = var4.getJSONObject("model");
            if (modelConfig.containsKey("modeId") && !oConvertUtils.isEmpty(modelConfig.containsKey("modeId"))) {
                String var6 = modelConfig.getString("modeId");
                JSONArray var7 = var4.getJSONArray("categories");
                AssertUtils.assertNotEmpty("[classifier]节点至少配置一个分类", var7);
                List<ChatMessage> chatMessageList = this.buildChatMessages(component, var7, var3);
                AIChatParams var9 = buildChatParams(modelConfig);
                String var10 = this.aiChatHandler.completions(var6, chatMessageList, var9);
                FlowContext flowContext = getFlowContext(component);

                for(int var12 = 0; var12 < var7.size(); ++var12) {
                    JSONObject var13 = var7.getJSONObject(var12);
                    String var14 = oConvertUtils.getString(var13.getString("next"), "");
                    if (var10.equalsIgnoreCase(var14)) {
                        String var15 = oConvertUtils.getString(var13.getString("category"), "");
                        flowContext.setNodeFieldValue(flowNodeConfig.getNodeId(), "index", var12);
                        flowContext.setNodeFieldValue(flowNodeConfig.getNodeId(), "content", var15);
                        logger.info("[classifier]已选择节点:[{}]", var10);
                        return this.formatNextNodeTag(flowNodeConfig, var10);
                    }
                }

                Map var16 = (Map)var4.get("else");
                if (null != var16 && !var16.isEmpty()) {
                    logger.info("[classifier]选择else:{}", var16.get("next"));
                    flowContext.setNodeFieldValue(flowNodeConfig.getNodeId(), "index", -1);
                    flowContext.setNodeFieldValue(flowNodeConfig.getNodeId(), "content", "都不符合");
                    return this.formatNextNodeTag(flowNodeConfig, (String)var16.get("next"));
                } else {
                    throw new FlowNodeHelperException(flowNodeConfig.getNodeId(), flowNodeConfig.getText(), "没有找到匹配的结果");
                }
            } else {
                throw new FlowNodeHelperException(flowNodeConfig.getNodeId(), flowNodeConfig.getText(), "请选择模型");
            }
        }
    }

    /**
     *  // 原 IAiragFlowAiGenServiceImpl()
     * 表明这是构建聊天参数的方法
     * @param modelConfig
     * @return
     */
    private static AIChatParams buildChatParams(JSONObject modelConfig) {
        AIChatParams var1 = new AIChatParams();
        JSONObject params = modelConfig.getJSONObject("params");
        if (null != params) {
            Double var3 = params.getDouble("temperature");
            if (null != var3) {
                var1.setTemperature(var3);
            }

            Double var4 = params.getDouble("topP");
            if (null != var4) {
                var1.setTopP(var4);
            }

            Double var5 = params.getDouble("presencePenalty");
            if (null != var5) {
                var1.setPresencePenalty(var5);
            }

            Double var6 = params.getDouble("frequencyPenalty");
            if (null != var6) {
                var1.setFrequencyPenalty(var6);
            }

            Integer var7 = params.getInteger("maxTokens");
            if (null != var7) {
                var1.setMaxTokens(var7);
            }
        }

        return var1;
    }

    /**
     * 表明这是构建聊天消息的方法   // 原 IAiragFlowAiGenServiceImpl()
     * @param component
     * @param categories 分类
     * @param inputParams
     * @return
     */
    private List<ChatMessage> buildChatMessages(NodeComponent component, JSONArray categories, List<FlowNodeConfig.NodeParam> inputParams) {
        FlowContext flowContext = getFlowContext(component);
        ArrayList var5 = new ArrayList();
        AssertUtils.assertNotEmpty("分类器节点,请选择输入变量", inputParams);
        AssertUtils.assertNotEmpty("分类器节点,至少需要配置一个分类", categories);

        for(int var6 = 0; var6 < categories.size(); ++var6) {
            JSONObject var7 = (JSONObject)categories.get(var6);
            String var8 = oConvertUtils.getString(var7.getString("category"), "");
            AssertUtils.assertNotEmpty("第" + (var6 + 1) + "个分类未填写描述", var8);
            AssertUtils.assertNotEmpty("分类" + var8 + "未选择跳转节点", var7.getString("next"));
        }

        String var9 = "请根据用户的问题，从以下分类数组中选择最符合的分类对象。  \n回复该分类对象的 `next` 属性值。  \n如果 **找不到最符合的**，请返回 `-1`。 分类数据如下:\n---------------------------\n" + categories.toJSONString() + "\n---------------------------\n";
        var5.add(new SystemMessage(var9));
        FlowNodeConfig.NodeParam var10 = (FlowNodeConfig.NodeParam)inputParams.get(0);
        Object var11 = flowContext.getNodeFieldValue(var10.getNodeId(), var10.getField());
        var5.add(new UserMessage(var11.toString()));
        return var5;
    }
}
