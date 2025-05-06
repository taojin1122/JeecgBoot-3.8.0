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
            AiModelOptions modelOp = params.toModelOptions();
            StreamingChatLanguageModel streamingChatModel = AiModelFactory.createStreamingChatModel(modelOp);
            CollateMsgResp chatMessage = this.collateMessage(messages, params);
            AiServiceContext context = new AiServiceContext(AiStreamChatAssistant.class);
            context.streamingChatModel = streamingChatModel;
            log.info("[LLMHandler] send message to AI server. message: {}", chatMessage);
            return new AiServiceTokenStream(chatMessage.chatMemory.messages(), (List)null, (Map)null, chatMessage.augmentationResult != null ? chatMessage.augmentationResult.contents() : null, context, "default");
        }
    }

    private CollateMsgResp collateMessage(List<ChatMessage> messages, AIParams params) {
        if (null == params) {
            params = new AIParams();
        }

        List<ChatMessage> messagesCopy = new ArrayList(messages);
        UserMessage userMessage = null;
        ChatMessage lastMessage = (ChatMessage)messagesCopy.get(messagesCopy.size() - 1);
        if (!lastMessage.type().equals(ChatMessageType.USER)) {
            throw new IllegalArgumentException("最后一条消息必须是用户消息");
        } else {
            userMessage = (UserMessage)messagesCopy.remove(messagesCopy.size() - 1);
            int maxMsgNumber = 6;
            if (null != params.getMaxMsgNumber()) {
                maxMsgNumber = params.getMaxMsgNumber() + 2;
            }

            AtomicReference<SystemMessage> systemMessageAto = new AtomicReference();
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
            ChatMemory chatMemory = MessageWindowChatMemory.builder().maxMessages(maxMsgNumber).build();
            if (null != systemMessageAto.get()) {
                chatMemory.add((ChatMessage)systemMessageAto.get());
            }

            messagesCopy.forEach(chatMemory::add);
            AugmentationResult augmentationResult = null;
            if (null != params.getQueryRouter()) {
                DefaultRetrievalAugmentor retrievalAugmentor = DefaultRetrievalAugmentor.builder().queryRouter(params.getQueryRouter()).build();
                if (retrievalAugmentor != null) {
                    StringBuilder userQuestion = new StringBuilder();
                    List<Content> contents = new ArrayList(userMessage.contents());

                    for(int i = contents.size() - 1; i >= 0; --i) {
                        if (contents.get(i) instanceof TextContent) {
                            userQuestion.append(((TextContent)contents.remove(i)).text());
                            userQuestion.append("\n");
                        }
                    }

                    UserMessage textUserMessage = UserMessage.from(userQuestion.toString());
                    Metadata metadata = Metadata.from(textUserMessage, "default", chatMemory.messages());
                    AugmentationRequest augmentationRequest = new AugmentationRequest(textUserMessage, metadata);
                    augmentationResult = retrievalAugmentor.augment(augmentationRequest);
                    textUserMessage = (UserMessage)augmentationResult.chatMessage();
                    contents.add(TextContent.from(textUserMessage.singleText()));
                    userMessage = UserMessage.from(contents);
                }
            }

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
