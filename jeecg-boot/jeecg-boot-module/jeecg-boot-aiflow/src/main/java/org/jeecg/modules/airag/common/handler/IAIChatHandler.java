//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.common.handler;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.service.TokenStream;
import java.util.List;

public interface IAIChatHandler {
    String completionsByDefaultModel(List<ChatMessage> var1, AIChatParams var2);

    String completions(String var1, List<ChatMessage> var2);

    String completions(String var1, List<ChatMessage> var2, AIChatParams var3);

    TokenStream chat(String var1, List<ChatMessage> var2);

    TokenStream chat(String var1, List<ChatMessage> var2, AIChatParams var3);

    TokenStream chatByDefaultModel(List<ChatMessage> var1, AIChatParams var2);

    UserMessage buildUserMessage(String var1, List<String> var2);

    List<ImageContent> buildImageContents(List<String> var1);
}
