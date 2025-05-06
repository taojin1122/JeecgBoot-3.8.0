//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.ai.config;

import org.jeecg.ai.handler.LLMHandler;
import org.jeecg.ai.prop.AiChatProperties;
import org.jeecg.chatgpt.service.AiChatService;
import org.jeecg.chatgpt.service.impl.ChatGptService;
import org.jeecg.chatgpt.service.impl.DefaultAiChatService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AiChatProperties.class})
public class AiChatAutoConfiguration {
    public AiChatAutoConfiguration() {
    }

    @Bean
    @ConditionalOnProperty(
        prefix = "jeecg.ai-chat",
        name = {"enabled"},
        havingValue = "true"
    )
    public LLMHandler llmHandler(AiChatProperties aiChatProperties) {
        return new LLMHandler(aiChatProperties);
    }

    @Bean
    public LLMHandler defaultLlmHandler() {
        return new LLMHandler();
    }

    @Bean
    @ConditionalOnProperty(
        prefix = "jeecg.ai-chat",
        name = {"enabled"},
        havingValue = "true"
    )
    public AiChatService chatGptAiChatService(LLMHandler llmHandler) {
        return new ChatGptService(llmHandler);
    }

    @Bean
    @ConditionalOnMissingBean({AiChatService.class})
    public AiChatService defaultAiChatService() {
        return new DefaultAiChatService();
    }
}
