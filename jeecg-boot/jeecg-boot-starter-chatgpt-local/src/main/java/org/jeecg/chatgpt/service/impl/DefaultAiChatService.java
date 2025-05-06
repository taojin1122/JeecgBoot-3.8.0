//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.chatgpt.service.impl;

import java.util.List;
import org.jeecg.chatgpt.dto.chat.MultiChatMessage;
import org.jeecg.chatgpt.dto.image.ImageFormat;
import org.jeecg.chatgpt.dto.image.ImageSize;
import org.jeecg.chatgpt.service.AiChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultAiChatService implements AiChatService {
    private static final Logger log = LoggerFactory.getLogger(DefaultAiChatService.class);
    private static final String NO_PROP_WARN_MSG = "If you want to use AI to chat, set up the response configuration first!";

    public DefaultAiChatService() {
    }

    public String completions(String message) {
        return (String)this.generalReturn();
    }

    public String multiCompletions(List<MultiChatMessage> messages) {
        return (String)this.generalReturn();
    }

    public String genSchemaModules(String prompt) {
        return (String)this.generalReturn();
    }

    public String genArticleWithMd(String prompt) {
        return (String)this.generalReturn();
    }

    public String imageGenerate(String prompt) {
        return (String)this.generalReturn();
    }

    public List<String> imageGenerate(String prompt, Integer n, ImageSize size, ImageFormat format) {
        return (List)this.generalReturn();
    }

    private <T> T generalReturn() {
        log.warn("If you want to use AI to chat, set up the response configuration first!");
        return null;
    }
}
