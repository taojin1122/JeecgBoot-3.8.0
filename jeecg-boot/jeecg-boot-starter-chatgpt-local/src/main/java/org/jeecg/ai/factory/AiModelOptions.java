//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.ai.factory;

public class AiModelOptions {
    String provider;
    String apiKey;
    String secretKey;
    String baseUrl;
    String modelName;
    Double temperature;
    Double topP;
    Double presencePenalty;
    Double frequencyPenalty;
    Integer maxTokens;
    Integer topNumber;
    Double similarity;
    Integer timeout;

    public String toString() {
        return "AiModelOptions{provider='" + this.provider + '\'' + ", apiKey='" + this.apiKey + '\'' + ", secretKey='" + this.secretKey + '\'' + ", baseUrl='" + this.baseUrl + '\'' + ", modelName='" + this.modelName + '\'' + ", temperature=" + this.temperature + ", topP=" + this.topP + ", presencePenalty=" + this.presencePenalty + ", frequencyPenalty=" + this.frequencyPenalty + ", maxTokens=" + this.maxTokens + ", topNumber=" + this.topNumber + ", similarity=" + this.similarity + ", timeout=" + this.timeout + '}';
    }

    public static AiModelOptionsBuilder builder() {
        return new AiModelOptionsBuilder();
    }

    public String getProvider() {
        return this.provider;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public String getModelName() {
        return this.modelName;
    }

    public Double getTemperature() {
        return this.temperature;
    }

    public Double getTopP() {
        return this.topP;
    }

    public Double getPresencePenalty() {
        return this.presencePenalty;
    }

    public Double getFrequencyPenalty() {
        return this.frequencyPenalty;
    }

    public Integer getMaxTokens() {
        return this.maxTokens;
    }

    public Integer getTopNumber() {
        return this.topNumber;
    }

    public Double getSimilarity() {
        return this.similarity;
    }

    public Integer getTimeout() {
        return this.timeout;
    }

    public void setProvider(final String provider) {
        this.provider = provider;
    }

    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }

    public void setSecretKey(final String secretKey) {
        this.secretKey = secretKey;
    }

    public void setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setModelName(final String modelName) {
        this.modelName = modelName;
    }

    public void setTemperature(final Double temperature) {
        this.temperature = temperature;
    }

    public void setTopP(final Double topP) {
        this.topP = topP;
    }

    public void setPresencePenalty(final Double presencePenalty) {
        this.presencePenalty = presencePenalty;
    }

    public void setFrequencyPenalty(final Double frequencyPenalty) {
        this.frequencyPenalty = frequencyPenalty;
    }

    public void setMaxTokens(final Integer maxTokens) {
        this.maxTokens = maxTokens;
    }

    public void setTopNumber(final Integer topNumber) {
        this.topNumber = topNumber;
    }

    public void setSimilarity(final Double similarity) {
        this.similarity = similarity;
    }

    public void setTimeout(final Integer timeout) {
        this.timeout = timeout;
    }

    public AiModelOptions(final String provider, final String apiKey, final String secretKey, final String baseUrl, final String modelName, final Double temperature, final Double topP, final Double presencePenalty, final Double frequencyPenalty, final Integer maxTokens, final Integer topNumber, final Double similarity, final Integer timeout) {
        this.provider = provider;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.baseUrl = baseUrl;
        this.modelName = modelName;
        this.temperature = temperature;
        this.topP = topP;
        this.presencePenalty = presencePenalty;
        this.frequencyPenalty = frequencyPenalty;
        this.maxTokens = maxTokens;
        this.topNumber = topNumber;
        this.similarity = similarity;
        this.timeout = timeout;
    }

    public static class AiModelOptionsBuilder {
        private String provider;
        private String apiKey;
        private String secretKey;
        private String baseUrl;
        private String modelName;
        private Double temperature;
        private Double topP;
        private Double presencePenalty;
        private Double frequencyPenalty;
        private Integer maxTokens;
        private Integer topNumber;
        private Double similarity;
        private Integer timeout;

        AiModelOptionsBuilder() {
        }

        public AiModelOptionsBuilder provider(final String provider) {
            this.provider = provider;
            return this;
        }

        public AiModelOptionsBuilder apiKey(final String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public AiModelOptionsBuilder secretKey(final String secretKey) {
            this.secretKey = secretKey;
            return this;
        }

        public AiModelOptionsBuilder baseUrl(final String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public AiModelOptionsBuilder modelName(final String modelName) {
            this.modelName = modelName;
            return this;
        }

        public AiModelOptionsBuilder temperature(final Double temperature) {
            this.temperature = temperature;
            return this;
        }

        public AiModelOptionsBuilder topP(final Double topP) {
            this.topP = topP;
            return this;
        }

        public AiModelOptionsBuilder presencePenalty(final Double presencePenalty) {
            this.presencePenalty = presencePenalty;
            return this;
        }

        public AiModelOptionsBuilder frequencyPenalty(final Double frequencyPenalty) {
            this.frequencyPenalty = frequencyPenalty;
            return this;
        }

        public AiModelOptionsBuilder maxTokens(final Integer maxTokens) {
            this.maxTokens = maxTokens;
            return this;
        }

        public AiModelOptionsBuilder topNumber(final Integer topNumber) {
            this.topNumber = topNumber;
            return this;
        }

        public AiModelOptionsBuilder similarity(final Double similarity) {
            this.similarity = similarity;
            return this;
        }

        public AiModelOptionsBuilder timeout(final Integer timeout) {
            this.timeout = timeout;
            return this;
        }

        public AiModelOptions build() {
            return new AiModelOptions(this.provider, this.apiKey, this.secretKey, this.baseUrl, this.modelName, this.temperature, this.topP, this.presencePenalty, this.frequencyPenalty, this.maxTokens, this.topNumber, this.similarity, this.timeout);
        }

        public String toString() {
            return "AiModelOptions.AiModelOptionsBuilder(provider=" + this.provider + ", apiKey=" + this.apiKey + ", secretKey=" + this.secretKey + ", baseUrl=" + this.baseUrl + ", modelName=" + this.modelName + ", temperature=" + this.temperature + ", topP=" + this.topP + ", presencePenalty=" + this.presencePenalty + ", frequencyPenalty=" + this.frequencyPenalty + ", maxTokens=" + this.maxTokens + ", topNumber=" + this.topNumber + ", similarity=" + this.similarity + ", timeout=" + this.timeout + ")";
        }
    }
}
