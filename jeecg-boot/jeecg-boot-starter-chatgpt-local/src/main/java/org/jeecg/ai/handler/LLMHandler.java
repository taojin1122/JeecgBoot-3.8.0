//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.ai.handler;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageType;
import dev.langchain4j.data.message.Content;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.rag.AugmentationRequest;
import dev.langchain4j.rag.AugmentationResult;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.query.Metadata;
import dev.langchain4j.service.AiServiceContext;
import dev.langchain4j.service.AiServiceTokenStream;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.output.ServiceOutputParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang.StringUtils;
import org.jeecg.ai.assistant.AiStreamChatAssistant;
import org.jeecg.ai.factory.AiModelFactory;
import org.jeecg.ai.factory.AiModelOptions;
import org.jeecg.ai.prop.AiChatProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LLMHandler {
    private static final Logger log = LoggerFactory.getLogger(LLMHandler.class);
    private AiChatProperties aiChatProperties;
    private final ServiceOutputParser serviceOutputParser = new ServiceOutputParser();

    public LLMHandler(AiChatProperties aiChatProperties) {
        this.aiChatProperties = aiChatProperties;
    }

    public LLMHandler() {
    }

    private AIParams ensureParams(AIParams params) {
        if (null == params || StringUtils.isEmpty(params.getApiKey())) {
            params = this.getDefaultModel(params);
        }

        if (null == params) {
            throw new IllegalArgumentException("大语言模型参数为空");
        } else {
            return params;
        }
    }

    private AIParams getDefaultModel(AIParams params) {
        if (null == this.aiChatProperties) {
            log.warn("未配置默认大预言模型");
            return null;
        } else {
            if (params == null) {
                params = new AIParams();
            }

            params.setProvider(this.aiChatProperties.getProvider());
            params.setModelName(this.aiChatProperties.getModel());
            params.setBaseUrl(this.aiChatProperties.getApiHost());
            params.setApiKey(this.aiChatProperties.getApiKey());
            params.setSecretKey(this.aiChatProperties.getCredential().getSecretKey());
            return params;
        }
    }

    public String completions(String message) {
        return this.completions(Collections.singletonList(UserMessage.from(message)), (AIParams)null);
    }

    public String completions(List<ChatMessage> messages, AIParams params) {
        params = this.ensureParams(params);
        AiModelOptions modelOp = params.toModelOptions();
        // 创建模型实例
        ChatLanguageModel chatModel = AiModelFactory.createChatModel(modelOp);
        CollateMsgResp chatMessage = this.collateMessage(messages, params);
        log.info("[LLMHandler] send message to AI server. message: {}", chatMessage);
        Response<AiMessage> response = chatModel.generate(chatMessage.chatMemory.messages());
        String resp = (String)this.serviceOutputParser.parse(response, String.class);
        log.info("[LLMHandler] Received the AI's response . message: {}", resp);
        return resp;
    }

    public TokenStream chat(List<ChatMessage> messages, AIParams params) {
        params = this.ensureParams(params);
        if (null == params) {
            throw new IllegalArgumentException("大语言模型参数为空");
        } else {
            //TODO  构建并调用流式对话模型（Streaming LLM）进行推理的流程，结合了模型参数配置、消息预处理、上下文构建和最终返回流式响应对象。
            // 将外部入参 params 转换为模型参数配置 AiModelOptions。
            AiModelOptions modelOp = params.toModelOptions();
//            使用工厂类创建一个“流式对话模型”实例
            StreamingChatLanguageModel streamingChatModel = AiModelFactory.createStreamingChatModel(modelOp);
//            对对话历史 messages 进行整理、拼接、上下文增强（如 RAG），生成最终待发送内容。
//            可能同时生成：
//            chatMessage.chatMemory.messages()：消息体
//            chatMessage.augmentationResult.contents()：RAG 检索的知识
            CollateMsgResp chatMessage = this.collateMessage(messages, params);
//            构造一个上下文容器 AiServiceContext，保存当前使用的模型。
            AiServiceContext context = new AiServiceContext(AiStreamChatAssistant.class);
            context.streamingChatModel = streamingChatModel;
            log.info("[LLMHandler] send message to AI server. message: {}", chatMessage);
//            构造并返回一个 流式响应对象 AiServiceTokenStream，用于 SSE/WebSocket 或自定义 token-by-token 推理。
//            可将其绑定到前端实时输出 UI。
            return new AiServiceTokenStream(
                    chatMessage.chatMemory.messages(),// 上下文对话内容
                    (List)null, // 工具列表（可为空）
                    (Map)null, // 函数调用等参数（可为空）
                    chatMessage.augmentationResult != null ? chatMessage.augmentationResult.contents() : null,  // 是否携带知识库增强内容
                    context, // 模型上下文（含模型实例）
                    "default"); // Agent/角色名
        }
    }

    /**
     * 处理并整理一组聊天消息（messages），并返回一个 CollateMsgResp 响应，
     * 包含了整理后的消息和可能的增强结果（augmentationResult）
     *
     * 对用户的最后一条消息进行处理，如果包含增强查询，就进行增强查询设置
     * 1、增强查询 是 引入额外的上下文信息或外部资源来增强查询的质量或准确性，从而改进结果。
     * @param messages
     * @param params
     * @return
     */
    private CollateMsgResp collateMessage(List<ChatMessage> messages, AIParams params) {
//        参数检查与初始化：
//        params 如果为 null，则初始化为一个新的 AIParams()。
        if (null == params) {
            params = new AIParams();
        }
//        messagesCopy 是 messages 的一个副本，用于后续处理。
        List<ChatMessage> messagesCopy = new ArrayList(messages);
        UserMessage userMessage = null;
        ChatMessage lastMessage = (ChatMessage)messagesCopy.get(messagesCopy.size() - 1);
//        验证最后一条消息：
//        检查最后一条消息是否是用户消息（ChatMessageType.USER）。如果不是，则抛出异常。
//        理由是流式对话模型要求最后一条消息必须是用户消息，才能继续处理
        if (!lastMessage.type().equals(ChatMessageType.USER)) {
            throw new IllegalArgumentException("最后一条消息必须是用户消息");
        } else {
//            删除系统消息并合并：
//            在 messagesCopy 中，系统消息（ChatMessageType.SYSTEM）将被合并成一个系统消息。
//            合并过程是将多个系统消息的内容拼接成一个完整的消息，并将其放入 systemMessageAto 中。
            // // 移除并获取用户消息
            userMessage = (UserMessage)messagesCopy.remove(messagesCopy.size() - 1);
            int maxMsgNumber = 6;
            if (null != params.getMaxMsgNumber()) {
                maxMsgNumber = params.getMaxMsgNumber() + 2;
            }

            AtomicReference<SystemMessage> systemMessageAto = new AtomicReference();
            // 将多个系统消息的内容拼接成一个完整的消息，并将其放入 systemMessageAto 中。
            messagesCopy.removeIf((tempMsg) -> {
                if (ChatMessageType.SYSTEM.equals(tempMsg.type())) {
                    if (systemMessageAto.get() == null) {
                        systemMessageAto.set((SystemMessage)tempMsg);
                    } else {
                        SystemMessage systemMessage = (SystemMessage)systemMessageAto.get();
                        String text = systemMessage.text() + "\n" + ((SystemMessage)tempMsg).text();
                        systemMessageAto.set(SystemMessage.from(text));
                    }

                    return true;
                } else {
                    return false;
                }
            });
//            创建聊天记忆：

//            创建一个 ChatMemory 对象，并根据 maxMsgNumber 限制聊天记忆中保留的消息数量。
//            maxMsgNumber 默认为 6，如果 params 中有配置该字段，则会覆盖为 params.getMaxMsgNumber() + 2。
            ChatMemory chatMemory = MessageWindowChatMemory.builder().maxMessages(maxMsgNumber).build();
            if (null != systemMessageAto.get()) {
//                将合并后的系统消息和其他聊天消息添加到 chatMemory 中。
                chatMemory.add((ChatMessage)systemMessageAto.get());
            }

            messagesCopy.forEach(chatMemory::add);
            AugmentationResult augmentationResult = null;
//            如果 params 中设置了 QueryRouter（查询路由器），则使用它来执行一个增强请求，向某个系统或服务请求更多的信息（比如知识库查询等）。
//            这通过 DefaultRetrievalAugmentor 完成。
            if (null != params.getQueryRouter()) {  // 如果配置了查询路由器，则进行增强处理
                DefaultRetrievalAugmentor retrievalAugmentor = DefaultRetrievalAugmentor.builder()
                        .queryRouter(params.getQueryRouter()).build();
                if (retrievalAugmentor != null) {
                    StringBuilder userQuestion = new StringBuilder();
                    List<Content> contents = new ArrayList(userMessage.contents());
                    // 拼接用户消息中的文本内容
                    for(int i = contents.size() - 1; i >= 0; --i) {
                        if (contents.get(i) instanceof TextContent) {
                            userQuestion.append(((TextContent)contents.remove(i)).text());
                            userQuestion.append("\n");
                        }
                    }
//                    userMessage 的内容会进行处理，所有的 TextContent 会被拼接成一个完整的问题，然后发送到增强系统。
                    UserMessage textUserMessage = UserMessage.from(userQuestion.toString());
                    // textUserMessage 这是通过增强（Augmentation）或其他方式处理后的用户消息。
                    // "default"：这个字符串参数通常代表一个默认的角色、主题或标签，在此例中它作为 Metadata 的一个标识或上下文信息。
//                    chatMemory.messages()：这表示当前的聊天历史记录，即 chatMemory 中所有的消息（包括用户消息和系统消息等）。


//                    在执行查询增强（Augmentation）时，Metadata 可以提供上下文信息，帮助增强器理解用户的需求。
//                    例如，Metadata 中的聊天历史可以帮助模型确定当前问题的背景，从而给出更合适的答案。
                    Metadata metadata = Metadata.from(textUserMessage, "default", chatMemory.messages());

                    AugmentationRequest augmentationRequest = new AugmentationRequest(textUserMessage, metadata);

                    // 执行增强请求
                    augmentationResult = retrievalAugmentor.augment(augmentationRequest);
                    // 获取增强后的消息
                    textUserMessage = (UserMessage)augmentationResult.chatMessage();
                    contents.add(TextContent.from(textUserMessage.singleText()));
                    // 更新用户消息
                    userMessage = UserMessage.from(contents);
                }
            }
            // 将最终的用户消息添加到聊天记忆中
            chatMemory.add(userMessage);
            return new CollateMsgResp(chatMemory, augmentationResult);
        }
    }

    private static class CollateMsgResp {
        public final ChatMemory chatMemory;
        public final AugmentationResult augmentationResult;

        public CollateMsgResp(ChatMemory chatMemory, AugmentationResult augmentationResult) {
            this.chatMemory = chatMemory;
            this.augmentationResult = augmentationResult;
        }

        public String toString() {
            return "{messages=" + (this.chatMemory != null ? this.chatMemory.messages() : "null") + "}";
        }
    }
}
