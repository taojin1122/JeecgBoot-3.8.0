//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.ai.factory;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.dashscope.QwenChatModel;
import dev.langchain4j.model.dashscope.QwenEmbeddingModel;
import dev.langchain4j.model.dashscope.QwenStreamingChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.model.openai.*;
import dev.langchain4j.model.qianfan.*;
import dev.langchain4j.model.zhipu.ZhipuAiChatModel;
import dev.langchain4j.model.zhipu.ZhipuAiEmbeddingModel;
import dev.langchain4j.model.zhipu.ZhipuAiStreamingChatModel;
import dev.langchain4j.model.zhipu.chat.ChatCompletionModel;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AiModelFactory {
    public static final String AIMODEL_TYPE_OPENAI = "OPENAI";
    public static final String AIMODEL_TYPE_ZHIPU = "ZHIPU";
    public static final String AIMODEL_TYPE_QIANFAN = "QIANFAN";
    public static final String AIMODEL_TYPE_QWEN = "QWEN";
    public static final String AIMODEL_TYPE_DEEPSEEK = "DEEPSEEK";
    public static final String AIMODEL_TYPE_OLLAMA = "OLLAMA";
    private static final ConcurrentHashMap<String, Object> chatModelCache = new ConcurrentHashMap();

    public AiModelFactory() {
    }

    private static Object getCache(String key) {
        return chatModelCache.get(key);
    }

    private static void setCache(String key, Object model) {
        chatModelCache.put(key, model);
    }

    public static ChatLanguageModel createChatModel(AiModelOptions options) {
        assertNotEmpty("请设置模型参数", options);
        assertNotEmpty("请选择AI模型供应商", options.getProvider());
        String cacheKey = options.toString();
        Object cachedModel = getCache(cacheKey);
        if (cachedModel != null) {
            return (ChatLanguageModel)cachedModel;
        } else {
            String apiKey = options.getApiKey();
            String secretKey = options.getSecretKey();
            String baseUrl = options.getBaseUrl();
            String modelName = options.getModelName();
            double temperature = getDouble(options.getTemperature(), 0.7);
            double topP = getDouble(options.getTopP(), 1.0);
            double presencePenalty = getDouble(options.getPresencePenalty(), 0.0);
            double frequencyPenalty = getDouble(options.getFrequencyPenalty(), 0.0);
            double repetitionPenalty = 1.0 + (presencePenalty + frequencyPenalty) / 2.0;
            int timeout = getInteger(options.getTimeout(), 120);
            Integer maxTokens = options.getMaxTokens();
            ChatLanguageModel chatModel = null;
            switch (options.getProvider().toUpperCase()) {
                case "OPENAI":
                    assertNotEmpty("apiKey不能为空", apiKey);
                    baseUrl = ensureOpenAiUrlEnd(baseUrl);
                    modelName = getString(modelName, OpenAiChatModelName.GPT_3_5_TURBO.toString());
                    OpenAiChatModel.OpenAiChatModelBuilder openAIBuilder = OpenAiChatModel.builder().apiKey(apiKey).baseUrl(baseUrl).modelName(modelName).temperature(temperature).topP(topP).presencePenalty(presencePenalty).frequencyPenalty(frequencyPenalty).timeout(Duration.ofSeconds((long)timeout)).maxRetries(0).logRequests(true).logResponses(true);
                    if (null != maxTokens) {
                        openAIBuilder.maxTokens(maxTokens);
                    }

                    chatModel = openAIBuilder.build();
                    break;
                case "ZHIPU":
                    assertNotEmpty("apiKey不能为空", apiKey);
                    modelName = getString(modelName, ChatCompletionModel.GLM_4_FLASH.toString());
                    ZhipuAiChatModel.ZhipuAiChatModelBuilder zhipuBuilder = ZhipuAiChatModel.builder().apiKey(apiKey).baseUrl(baseUrl).model(modelName).temperature(temperature).topP(topP).maxRetries(0).callTimeout(Duration.ofSeconds((long)timeout)).readTimeout(Duration.ofSeconds((long)timeout)).connectTimeout(Duration.ofSeconds((long)timeout)).writeTimeout(Duration.ofSeconds((long)timeout));
                    if (null != maxTokens) {
                        zhipuBuilder.maxToken(maxTokens);
                    }

                    chatModel = zhipuBuilder.build();
                    break;
                case "QIANFAN":
                    assertNotEmpty("apiKey不能为空", apiKey);
                    assertNotEmpty("secretKey不能为空", secretKey);
                    modelName = getString(modelName, QianfanChatModelNameEnum.YI_34B_CHAT.getModelName());
                    QianfanChatModel.QianfanChatModelBuilder qianfanBuilder = QianfanChatModel.builder().apiKey(apiKey).baseUrl(baseUrl).secretKey(secretKey).modelName(modelName).temperature(temperature).topP(topP).penaltyScore(repetitionPenalty).maxRetries(0);
                    if (null != maxTokens) {
                        qianfanBuilder.maxOutputTokens(maxTokens);
                    }

                    chatModel = qianfanBuilder.build();
                    break;
                case "QWEN":
                    assertNotEmpty("apiKey不能为空", apiKey);
                    modelName = getString(modelName, "qwen-plus");
                    QwenChatModel.QwenChatModelBuilder qwenBuilder = QwenChatModel.builder().apiKey(apiKey).baseUrl(baseUrl).modelName(modelName).temperature((float)temperature).topP(topP).repetitionPenalty((float)repetitionPenalty);
                    if (null != maxTokens) {
                        qwenBuilder.maxTokens(maxTokens);
                    }

                    chatModel = qwenBuilder.build();
                    break;
                case "OLLAMA":
                    assertNotEmpty("baseUrl不能为空", baseUrl);
                    assertNotEmpty("请选择模型", modelName);
                    chatModel = OllamaChatModel.builder().baseUrl(baseUrl).modelName(modelName).temperature(temperature).topP(topP).repeatPenalty(repetitionPenalty).timeout(Duration.ofSeconds((long)timeout)).maxRetries(3).build();
                    break;
                case "DEEPSEEK":
                    assertNotEmpty("apiKey不能为空", apiKey);
                    baseUrl = getString(baseUrl, "https://api.deepseek.com/v1");
                    baseUrl = ensureOpenAiUrlEnd(baseUrl);
                    modelName = getString(modelName, "deepseek-chat");
                    OpenAiChatModel.OpenAiChatModelBuilder dsBuilder = OpenAiChatModel.builder().apiKey(apiKey).baseUrl(baseUrl).modelName(modelName).temperature(temperature).topP(topP).presencePenalty(presencePenalty).frequencyPenalty(frequencyPenalty).timeout(Duration.ofSeconds((long)timeout));
                    if (null != maxTokens) {
                        dsBuilder.maxTokens(maxTokens);
                    }

                    chatModel = dsBuilder.build();
            }

            setCache(cacheKey, chatModel);
            return (ChatLanguageModel)chatModel;
        }
    }

    public static StreamingChatLanguageModel createStreamingChatModel(AiModelOptions options) {
        assertNotEmpty("请设置模型参数", options);
        assertNotEmpty("请选择AI模型供应商", options.getProvider());
        String cacheKey = "STEAM_" + options.toString();
        Object cachedModel = getCache(cacheKey);
        if (cachedModel != null) {
            return (StreamingChatLanguageModel)cachedModel;
        } else {
            String apiKey = options.getApiKey();
            String secretKey = options.getSecretKey();
            String baseUrl = options.getBaseUrl();
            String modelName = options.getModelName();
            double temperature = getDouble(options.getTemperature(), 0.7);
            double topP = getDouble(options.getTopP(), 1.0);
            double presencePenalty = getDouble(options.getPresencePenalty(), 0.0);
            double frequencyPenalty = getDouble(options.getFrequencyPenalty(), 0.0);
            double repetitionPenalty = 1.0 + (presencePenalty + frequencyPenalty) / 2.0;
            int timeout = getInteger(options.getTimeout(), 120);
            Integer maxTokens = options.getMaxTokens();
            StreamingChatLanguageModel chatModel = null;
            switch (options.getProvider().toUpperCase()) {
                case "OPENAI":
                    assertNotEmpty("apiKey不能为空", apiKey);
                    baseUrl = ensureOpenAiUrlEnd(baseUrl);
                    modelName = getString(modelName, OpenAiChatModelName.GPT_3_5_TURBO.toString());
                    OpenAiStreamingChatModel.OpenAiStreamingChatModelBuilder openAIBuilder = OpenAiStreamingChatModel.builder().apiKey(apiKey).baseUrl(baseUrl).modelName(modelName).temperature(temperature).topP(topP).presencePenalty(presencePenalty).frequencyPenalty(frequencyPenalty).timeout(Duration.ofSeconds((long)timeout)).logRequests(true).logResponses(true);
                    if (null != maxTokens) {
                        openAIBuilder.maxTokens(maxTokens);
                    }

                    chatModel = openAIBuilder.build();
                    break;
                case "ZHIPU":
                    assertNotEmpty("apiKey不能为空", apiKey);
                    modelName = getString(modelName, ChatCompletionModel.GLM_4_FLASH.toString());
                    ZhipuAiStreamingChatModel.ZhipuAiStreamingChatModelBuilder zhipuBuilder = ZhipuAiStreamingChatModel.builder().apiKey(apiKey).baseUrl(baseUrl).model(modelName).temperature(temperature).topP(topP).callTimeout(Duration.ofSeconds((long)timeout)).readTimeout(Duration.ofSeconds((long)timeout)).connectTimeout(Duration.ofSeconds((long)timeout)).writeTimeout(Duration.ofSeconds((long)timeout));
                    if (null != maxTokens) {
                        zhipuBuilder.maxToken(maxTokens);
                    }

                    chatModel = zhipuBuilder.build();
                    break;
                case "QIANFAN":
                    assertNotEmpty("apiKey不能为空", apiKey);
                    assertNotEmpty("secretKey不能为空", secretKey);
                    modelName = getString(modelName, QianfanChatModelNameEnum.YI_34B_CHAT.getModelName());
                    chatModel = QianfanStreamingChatModel.builder().apiKey(apiKey).baseUrl(baseUrl).secretKey(secretKey).modelName(modelName).temperature(temperature).topP(topP).penaltyScore(repetitionPenalty).build();
                    break;
                case "QWEN":
                    assertNotEmpty("apiKey不能为空", apiKey);
                    modelName = getString(modelName, "qwen-plus");
                    QwenStreamingChatModel.QwenStreamingChatModelBuilder qwenBuilder = QwenStreamingChatModel.builder().apiKey(apiKey).baseUrl(baseUrl).modelName(modelName).temperature((float)temperature).topP(topP).repetitionPenalty((float)repetitionPenalty);
                    if (null != maxTokens) {
                        qwenBuilder.maxTokens(maxTokens);
                    }

                    chatModel = qwenBuilder.build();
                    break;
                case "OLLAMA":
                    assertNotEmpty("baseUrl不能为空", baseUrl);
                    assertNotEmpty("请选择模型", modelName);
                    chatModel = OllamaStreamingChatModel.builder().baseUrl(baseUrl).modelName(modelName).temperature(temperature).topP(topP).repeatPenalty(repetitionPenalty).timeout(Duration.ofSeconds((long)timeout)).build();
                    break;
                case "DEEPSEEK":
                    assertNotEmpty("apiKey不能为空", apiKey);
                    baseUrl = getString(baseUrl, "https://api.deepseek.com/v1");
                    baseUrl = ensureOpenAiUrlEnd(baseUrl);
                    modelName = getString(modelName, "deepseek-chat");
                    OpenAiStreamingChatModel.OpenAiStreamingChatModelBuilder dsBuilder = OpenAiStreamingChatModel.builder().apiKey(apiKey).baseUrl(baseUrl).modelName(modelName).temperature(temperature).topP(topP).presencePenalty(presencePenalty).frequencyPenalty(frequencyPenalty).timeout(Duration.ofSeconds((long)timeout));
                    if (null != maxTokens) {
                        dsBuilder.maxTokens(maxTokens);
                    }

                    chatModel = dsBuilder.build();
            }

            setCache(cacheKey, chatModel);
            return (StreamingChatLanguageModel)chatModel;
        }
    }

    public static EmbeddingModel createEmbeddingModel(AiModelOptions options) {
        assertNotEmpty("请设置模型参数", options);
        assertNotEmpty("请选择AI模型供应商", options.getProvider());
        String cacheKey = options.toString();
        Object cachedModel = getCache(cacheKey);
        if (cachedModel != null) {
            return (EmbeddingModel)cachedModel;
        } else {
            String apiKey = options.getApiKey();
            String secretKey = options.getSecretKey();
            String baseUrl = options.getBaseUrl();
            String modelName = options.getModelName();
            int timeout = getInteger(options.getTimeout(), 120);
            Object embeddingModel;
            switch (options.getProvider().toUpperCase()) {
                case "OPENAI":
                    assertNotEmpty("apiKey不能为空", apiKey);
                    baseUrl = ensureOpenAiUrlEnd(baseUrl);
                    modelName = getString(modelName, OpenAiEmbeddingModelName.TEXT_EMBEDDING_ADA_002.toString());
                    embeddingModel = OpenAiEmbeddingModel.builder().apiKey(apiKey).baseUrl(baseUrl).modelName(modelName).timeout(Duration.ofSeconds((long)timeout)).maxRetries(0).logRequests(true).logResponses(true).build();
                    break;
                case "ZHIPU":
                    assertNotEmpty("apiKey不能为空", apiKey);
                    modelName = getString(modelName, dev.langchain4j.model.zhipu.embedding.EmbeddingModel.EMBEDDING_2.toString());
                    embeddingModel = ZhipuAiEmbeddingModel.builder().apiKey(apiKey).baseUrl(baseUrl).model(modelName).callTimeout(Duration.ofSeconds((long)timeout)).readTimeout(Duration.ofSeconds((long)timeout)).connectTimeout(Duration.ofSeconds((long)timeout)).writeTimeout(Duration.ofSeconds((long)timeout)).dimensions(1536).maxRetries(0).build();
                    break;
                case "QIANFAN":
                    assertNotEmpty("apiKey不能为空", apiKey);
                    assertNotEmpty("secretKey不能为空", secretKey);
                    modelName = getString(modelName, QianfanEmbeddingModelNameEnum.EMBEDDING_V1.getModelName());
                    embeddingModel = QianfanEmbeddingModel.builder().apiKey(apiKey).baseUrl(baseUrl).secretKey(secretKey).modelName(modelName).maxRetries(0).build();
                    break;
                case "QWEN":
                    assertNotEmpty("apiKey不能为空", apiKey);
                    modelName = getString(modelName, "text-embedding-v2");
                    embeddingModel = QwenEmbeddingModel.builder().apiKey(apiKey).baseUrl(baseUrl).modelName(modelName).build();
                    break;
                case "OLLAMA":
                    assertNotEmpty("baseUrl不能为空", baseUrl);
                    assertNotEmpty("请选择模型", modelName);
                    embeddingModel = OllamaEmbeddingModel.builder().baseUrl(baseUrl).modelName(modelName).maxRetries(3).timeout(Duration.ofSeconds((long)timeout)).maxRetries(0).build();
                    break;
                default:
                    throw new RuntimeException("不支持的模型");
            }

            setCache(cacheKey, embeddingModel);
            return (EmbeddingModel)embeddingModel;
        }
    }

    public static void assertNotEmpty(String msg, Object obj) {
        if (isObjectEmpty(obj)) {
            throw new RuntimeException(msg);
        }
    }

    public static boolean isObjectEmpty(Object obj) {
        if (null == obj) {
            return true;
        } else if (obj instanceof CharSequence) {
            return isEmpty(obj);
        } else if (obj instanceof Map) {
            return ((Map)obj).isEmpty();
        } else if (obj instanceof Iterable) {
            return isObjectEmpty(((Iterable)obj).iterator());
        } else if (obj instanceof Iterator) {
            return !((Iterator)obj).hasNext();
        } else if (isArray(obj)) {
            return 0 == Array.getLength(obj);
        } else {
            return false;
        }
    }

    @Nullable
    private static String ensureOpenAiUrlEnd(String baseUrl) {
        if (StringUtils.isNotEmpty(baseUrl)) {
            if (baseUrl.endsWith("/")) {
                baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
            }

            if (!baseUrl.endsWith("v1")) {
                baseUrl = baseUrl + (baseUrl.endsWith("/") ? "" : "/") + "v1";
            }
        }

        return baseUrl;
    }

    public static boolean isArray(Object obj) {
        return obj == null ? false : obj.getClass().isArray();
    }

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if ("".equals(object)) {
            return true;
        } else {
            return "null".equals(object);
        }
    }

    public static String getString(CharSequence str, String defaultStr) {
        return isEmpty(str) ? defaultStr : str.toString();
    }

    public static Integer getInteger(Integer object, Integer defval) {
        if (object == null) {
            return defval;
        } else {
            try {
                return Integer.parseInt(object.toString());
            } catch (NumberFormatException var3) {
                return defval;
            }
        }
    }

    public static Double getDouble(Double object, Double defval) {
        if (object == null) {
            return defval;
        } else {
            try {
                return Double.parseDouble(object.toString());
            } catch (NumberFormatException var3) {
                return defval;
            }
        }
    }
}
