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
import dev.langchain4j.data.message.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.jeecg.common.util.AssertUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.airag.common.handler.AIChatParams;
import org.jeecg.modules.airag.common.handler.IAIChatHandler;
import org.jeecg.modules.airag.common.vo.MessageHistory;
import org.jeecg.modules.airag.flow.context.FlowContext;
import org.jeecg.modules.airag.flow.component.base.FlowNodeHelper;
import org.jeecg.modules.airag.flow.exception.FlowNodeHelperException;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowNode;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowNodeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 这是一个 LLM (Large Language Model) 节点组件
 */
@Component("llm")
public class LLMNode extends FlowNodeHelper {
    private static final Logger logger = LoggerFactory.getLogger(LLMNode.class);
    @Autowired
    IAIChatHandler aiChatHandler;

    public LLMNode() {
    }

    @LiteflowMethod(
        value = LiteFlowMethodEnum.PROCESS,
        nodeType = NodeTypeEnum.COMMON
    )
    public void a(NodeComponent component) {
        logger.info("节点开始-llm");
        FlowContext flowContext = getFlowContext(component);
        FlowNodeConfig flowNodeConfig = getNodeConfig(component);
        List<FlowNodeConfig.NodeParam> inputParams = flowNodeConfig.getInputParams();
        if (oConvertUtils.isObjectEmpty(flowNodeConfig.getOptions())) {
            throw new FlowNodeHelperException(flowNodeConfig.getNodeId(), flowNodeConfig.getText(), "节点配置错误");
        } else {
            JSONObject flowNodeOptions = JSONObject.parseObject(JSONObject.toJSONString(flowNodeConfig.getOptions()));
            MessageContext messageContext = this.buildMessageContext(component, inputParams);
            String messages = flowNodeOptions.getString("messages");
            List<MessageHistory> messageHistories = JSONArray.parseArray(messages, MessageHistory.class);
            AssertUtils.assertNotEmpty("LLM节点至少配置一条用户消息", messageHistories);
            Object var9 = flowContext.getContextValue("history");
            List var10;
            if (var9 instanceof List) {
                var10 = (List)var9;
                if (oConvertUtils.isObjectNotEmpty(var10)) {
                    messageHistories.addAll(0, var10);
                }
            }

            var10 = this.buildChatMessages(messageHistories, messageContext);
            String var11 = this.processModelCompletion(component, flowNodeOptions.getJSONObject("model"),
                    messageContext.knowledgeText.toString(), flowContext, flowNodeConfig, var10);
            List<FlowNodeConfig.NodeParam> outputParams = flowNodeConfig.getOutputParams();
            FlowNodeConfig.NodeParam var13 = (FlowNodeConfig.NodeParam)outputParams.get(0);
            flowContext.setNodeFieldValue(flowNodeConfig.getNodeId(), var13.getField(), var11);
        }
    }

    private String processModelCompletion(NodeComponent component, JSONObject modelConfig, String knowledgeText,
                                          FlowContext context, FlowNodeConfig config, List<ChatMessage> messages) {
        if (modelConfig.containsKey("modeId") && !oConvertUtils.isEmpty(modelConfig.containsKey("modeId"))) {
            String modeId = modelConfig.getString("modeId");
            AIChatParams aiChatParams = buildChatParams(modelConfig, knowledgeText, config);
            return this.aiChatHandler.completions(modeId, messages, aiChatParams);
        } else {
            throw new FlowNodeHelperException(config.getNodeId(), config.getText(), "请选择模型");
        }
    }

    private static AIChatParams buildChatParams(JSONObject modelConfig, String knowledgeText, FlowNodeConfig config) {
        JSONObject var3 = JSONObject.parseObject(JSONObject.toJSONString(config.getOptions()));
        Integer var4 = oConvertUtils.getInt(var3.getInteger("history"), 3);
        AIChatParams var5 = new AIChatParams();
        var5.setKnowledgeTxt(knowledgeText);
        var5.setMaxMsgNumber(var4);
        JSONObject var6 = modelConfig.getJSONObject("params");
        if (null != var6) {
            Double var7 = var6.getDouble("temperature");
            if (null != var7) {
                var5.setTemperature(var7);
            }

            Double var8 = var6.getDouble("topP");
            if (null != var8) {
                var5.setTopP(var8);
            }

            Double var9 = var6.getDouble("presencePenalty");
            if (null != var9) {
                var5.setPresencePenalty(var9);
            }

            Double var10 = var6.getDouble("frequencyPenalty");
            if (null != var10) {
                var5.setFrequencyPenalty(var10);
            }

            Integer var11 = var6.getInteger("maxTokens");
            if (null != var11) {
                var5.setMaxTokens(var11);
            }
        }

        return var5;
    }

    private MessageContext buildMessageContext(NodeComponent component, List<FlowNodeConfig.NodeParam> params) {
        FlowContext flowContext = getFlowContext(component);
        StringBuilder stringBuilder = new StringBuilder();
        HashMap hashMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        Iterator iterator = params.iterator();

        while(true) {
            while(true) {
                FlowNodeConfig.NodeParam nodeParam;
                Object var9;
                do {
                    if (!iterator.hasNext()) {
                        return new MessageContext(stringBuilder, hashMap, arrayList);
                    }

                    nodeParam = (FlowNodeConfig.NodeParam)iterator.next();
                    var9 = flowContext.getNodeFieldValue(nodeParam.getNodeId(), nodeParam.getField());
                } while(oConvertUtils.isEmpty(var9));

                FlowNode var10 = getNodeByComponentAndTag((NodeComponent)component, (String)nodeParam.getNodeId());
                if (null != var10 && "knowledge".equalsIgnoreCase(var10.getType())) {
                    stringBuilder.append(var9).append("\n");
                } else {
                    boolean isPicture = isPictureParam(component, nodeParam);
                    if (isPicture) {
                        if (var9 instanceof List) {
                            List var12 = (List)var9;
                            if (oConvertUtils.isObjectNotEmpty(var12)) {
                                arrayList.addAll(var12);
                            }
                        } else if (var9 instanceof String) {
                            if (((String)var9).contains(",")) {
                                String[] var13 = ((String)var9).split(",");
                                if (oConvertUtils.isObjectNotEmpty(var13)) {
                                    arrayList.addAll(Arrays.asList(var13));
                                }
                            } else {
                                arrayList.add((String)var9);
                            }
                        }
                    } else {
                        String var14 = "{{" + nodeParam.getName() + "}}";
                        hashMap.put(var14, var9);
                    }
                }
            }
        }
    }

    /**
     * 这个函数 isPictureParam 用于检查一个节点参数是否为图片类型参数。让我详细解释其逻辑：
     * @param component
     * @param param
     * @return
     */
    protected static boolean isPictureParam(NodeComponent component, FlowNodeConfig.NodeParam param) {
        // 1. 首先获取对应的流程节点
        FlowNode flowNode = getNodeByComponentAndTag((NodeComponent)component, (String)param.getNodeId());
        if (null == flowNode) {
            return false;
        } else {
            // 2. 获取节点配置
            FlowNodeConfig var3 = flowNode.getProperties();
            // 3. 如果是开始节点
            if ("start".equalsIgnoreCase(flowNode.getType())) {
                // 检查输入参数
                List var4 = var3.getInputParams();
                if (null != var4) {
                    // 遍历所有输入参数
                    Iterator var5 = var4.iterator();

                    while(var5.hasNext()) {
                        FlowNodeConfig.NodeParam var6 = (FlowNodeConfig.NodeParam)var5.next();
                        // 检查字段名是否匹配且类型是否为 picture
                        if (var6.getField().equalsIgnoreCase(param.getField()) && "picture".equalsIgnoreCase(var6.getType())) {
                            return true;
                        }
                    }
                }
                // 4. 如果是其他类型节点，检查输出参数
            } else if (oConvertUtils.isNotEmpty(var3.getOutputParams())) {
                // 遍历所有输出参数
                Iterator var7 = var3.getOutputParams().iterator();

                while(var7.hasNext()) {
                    // 检查字段名是否匹配且类型是否为 picture
                    FlowNodeConfig.NodeParam var8 = (FlowNodeConfig.NodeParam)var7.next();
                    if (var8.getField().equalsIgnoreCase(param.getField()) && "picture".equalsIgnoreCase(param.getType())) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    /**
     *
     * @param messageHistoryList
     * @param messageContext
     * @return
     */
    private List<ChatMessage> buildChatMessages(List<MessageHistory> messageHistoryList,  MessageContext messageContext) {
        Map<String, Object> variables = messageContext.variables;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        List var5 = (List)messageHistoryList.stream().filter(Objects::nonNull).filter((var0) -> {
            return oConvertUtils.isNotEmpty(var0.getContent());
        }).map((messageHistory) -> {
            String content = messageHistory.getContent();

            String key;
            for(Iterator iterator = variables.keySet().iterator(); iterator.hasNext(); content = content.replace(key, variables.get(key).toString())) {
                key = (String)iterator.next();
            }

            if ("system".equalsIgnoreCase(messageHistory.getRole())) {
                return new SystemMessage(content);
            } else if ("user".equalsIgnoreCase(messageHistory.getRole())) {
                List<Content> userContents = new ArrayList<>();
                List<MessageHistory.ImageHistory> images = messageHistory.getImages();
                if (oConvertUtils.isObjectNotEmpty(images) && !images.isEmpty()) {
                    userContents.addAll(images.stream().map((imageHistory) -> {
                        return oConvertUtils.isNotEmpty(imageHistory.getUrl()) ? ImageContent.from(imageHistory.getUrl()) : ImageContent.from(imageHistory.getBase64Data(), imageHistory.getMimeType());
                    }).collect(Collectors.toList()));
                }

                userContents.add(TextContent.from(content));
                atomicInteger.getAndIncrement();
                return UserMessage.from(userContents);
            } else {
                return new AiMessage(content);
            }
        }).collect(Collectors.toList());
        AssertUtils.assertGt("LLM节点至少配置一条用户消息", atomicInteger.get(), 0);
        if (oConvertUtils.isObjectNotEmpty(messageContext.images)) {
            for(int var6 = var5.size() - 1; var6 >= 0; --var6) {
                ChatMessage chatMessage = (ChatMessage)var5.get(var6);
                if (ChatMessageType.USER.equals(chatMessage.type())) {
                    UserMessage userMessage = (UserMessage)chatMessage;
                    List<ImageContent> imageContentList = this.aiChatHandler.buildImageContents(messageContext.images);

                    List<Content> contentList = new ArrayList<>(userMessage.contents());
                    contentList.addAll(imageContentList);
                    var5.set(var6, UserMessage.from(contentList));
                    break;
                }
            }
        }

        return var5;
    }

    // 内部类重命名
    private static class MessageContext {  // 原 IAiragFlowAiGenServiceImpl
        public final StringBuilder knowledgeText;  // 原 IAiragFlowAiGenServiceImpl
        public final Map<String, Object> variables;  // 原 IAiragFlowServiceImpl
        public final List<String> images;  // 原 c

        public MessageContext(StringBuilder knowledgeText, Map<String, Object> variables,
                              List<String> images) {
            this.knowledgeText = knowledgeText;
            this.variables = variables;
            this.images = images;
        }
    }
}
