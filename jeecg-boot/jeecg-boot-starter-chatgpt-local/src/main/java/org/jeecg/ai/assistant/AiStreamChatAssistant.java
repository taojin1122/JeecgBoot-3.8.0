//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.ai.assistant;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface AiStreamChatAssistant {
    TokenStream chat(@UserMessage String prompt);

    @SystemMessage({"{{systemMessage}}"})
    TokenStream chat(@V("systemMessage") String systemMessage, @UserMessage String prompt);
}
