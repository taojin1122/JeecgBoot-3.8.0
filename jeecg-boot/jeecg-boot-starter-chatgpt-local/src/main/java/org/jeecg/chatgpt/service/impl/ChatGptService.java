//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.chatgpt.service.impl;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.jeecg.ai.handler.AIParams;
import org.jeecg.ai.handler.LLMHandler;
import org.jeecg.chatgpt.dto.chat.MultiChatMessage;
import org.jeecg.chatgpt.dto.chat.MultiChatMessage.Role;
import org.jeecg.chatgpt.dto.image.ImageFormat;
import org.jeecg.chatgpt.dto.image.ImageSize;
import org.jeecg.chatgpt.service.AiChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatGptService implements AiChatService {
    private static final Logger log = LoggerFactory.getLogger(ChatGptService.class);
    LLMHandler llmHandler;

    public ChatGptService(LLMHandler llmHandler) {
        this.llmHandler = llmHandler;
    }

    public String completions(String message) {
        return StringUtils.isEmpty(message) ? "" : this.llmHandler.completions(message);
    }

    public String multiCompletions(List<MultiChatMessage> messages) {
        if (null != messages && !messages.isEmpty()) {
            List<ChatMessage> chatMessages = (List)messages.stream().map((m) -> {
                if (Role.SYSTEM.getName().equalsIgnoreCase(m.getRole())) {
                    return new SystemMessage(m.getContent());
                } else {
                    return (ChatMessage)(Role.ASSISTANT.getName().equalsIgnoreCase(m.getRole()) ? new AiMessage(m.getContent()) : new UserMessage(m.getContent()));
                }
            }).collect(Collectors.toList());
            return this.llmHandler.completions(chatMessages, (AIParams)null);
        } else {
            return "";
        }
    }

    public String genSchemaModules(String prompt) {
        if (StringUtils.isEmpty(prompt)) {
            return "";
        } else {
            String sysMsgContent = "根据业务需求设计一套表单；只回答json数据不要有其他描述。整体是一个json数组，每个表是一个json对象，属性包含：中文名（comment)，英文名(tableName)，字段列表(fields);字段列表是一个json数组，包含字段英文名(field)、字段中文名(comment)、字段数据库类型(fieldDbType)、字段组件(component)。可用的组件包含：input、textarea、number、money、radio、checkbox、select、switch、phone、email、file、date、time。参考json：[{\\\"tableName\\\":\\\"order\\\",\\\"comment\\\":\\\"订单表\\\",\\\"fields\\\":[{\\\"field\\\":\\\"name\\\",\\\"comment\\\":\\\"姓名\\\",\\\"fieldDbType\\\":\\\"varchar\\\",\\\"component\\\":\\\"input\\\"}]}]。";
            MultiChatMessage sysMsg = MultiChatMessage.builder().role(Role.USER).content(sysMsgContent).build();
            MultiChatMessage userMsg = MultiChatMessage.builder().role(Role.USER).content("业务需求如下:" + prompt).build();
            String gptResp = this.multiCompletions(Arrays.asList(sysMsg, userMsg));
            if (gptResp.contains("</think>")) {
                String[] thinkSplit = gptResp.split("</think>");
                gptResp = thinkSplit[thinkSplit.length - 1];
            }

            Pattern pattern = Pattern.compile("\\[.*?].*$", 32);
            Matcher matcher = pattern.matcher(gptResp);
            String returnData = "";
            if (matcher.find()) {
                returnData = matcher.group(0);
            }

            return returnData;
        }
    }

    public String genArticleWithMd(String prompt) {
        if (StringUtils.isEmpty(prompt)) {
            return "";
        } else {
            List<MultiChatMessage> messages = new ArrayList();
            messages.add(MultiChatMessage.builder().role(Role.SYSTEM).content("根据文章内容描述用MarkDown写一篇软文；只输出MarkDown,不要其他的描述。").build());
            messages.add(MultiChatMessage.builder().role(Role.USER).content("文章内容描述如下:" + prompt).build());
            String gptResp = this.multiCompletions(messages);
            if (gptResp.contains("</think>")) {
                String[] thinkSplit = gptResp.split("</think>");
                gptResp = thinkSplit[thinkSplit.length - 1];
            }

            return gptResp;
        }
    }

    public String imageGenerate(String prompt) {
        log.warn("暂不支持图像生成");
        return null;
    }

    public List<String> imageGenerate(String prompt, Integer n, ImageSize size, ImageFormat format) {
        log.warn("暂不支持图像生成");
        return null;
    }
}
