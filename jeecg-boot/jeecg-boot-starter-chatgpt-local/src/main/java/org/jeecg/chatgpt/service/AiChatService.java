//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.chatgpt.service;

import java.util.List;
import org.jeecg.chatgpt.dto.chat.MultiChatMessage;
import org.jeecg.chatgpt.dto.image.ImageFormat;
import org.jeecg.chatgpt.dto.image.ImageSize;

public interface AiChatService {
    String completions(String message);

    String multiCompletions(List<MultiChatMessage> messages);

    String genSchemaModules(String prompt);

    String genArticleWithMd(String prompt);

    String imageGenerate(String prompt);

    List<String> imageGenerate(String prompt, Integer n, ImageSize size, ImageFormat format);
}
