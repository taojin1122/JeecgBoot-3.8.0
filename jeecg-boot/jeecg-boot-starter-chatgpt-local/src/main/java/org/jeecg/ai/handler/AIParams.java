//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.ai.handler;

import dev.langchain4j.rag.query.router.QueryRouter;
import org.jeecg.ai.factory.AiModelOptions;

public class AIParams {
    String provider;
    String modelName;
    String baseUrl;
    String apiKey;
    String secretKey;
    String knowledgeTxt;
    QueryRouter queryRouter;
    Integer topNumber = 5;
    Double similarity = 0.75;
    Integer maxMsgNumber = 4;
    Double temperature;
    Double topP;
    Double presencePenalty;
    Double frequencyPenalty;
    Integer maxTokens;
    Integer timeout;

    public AiModelOptions toModelOptions() {
        return AiModelOptions.builder().provider(this.getProvider()).modelName(this.getModelName()).baseUrl(this.getBaseUrl()).apiKey(this.getApiKey()).secretKey(this.getSecretKey()).temperature(this.getTemperature()).topP(this.getTopP()).presencePenalty(this.getPresencePenalty()).frequencyPenalty(this.getFrequencyPenalty()).maxTokens(this.getMaxTokens()).topNumber(this.getTopNumber()).similarity(this.getSimilarity()).timeout(this.getTimeout()).build();
    }

    public String getProvider() {
        return this.provider;
    }

    public String getModelName() {
        return this.modelName;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public String getKnowledgeTxt() {
        return this.knowledgeTxt;
    }

    public QueryRouter getQueryRouter() {
        return this.queryRouter;
    }

    public Integer getTopNumber() {
        return this.topNumber;
    }

    public Double getSimilarity() {
        return this.similarity;
    }

    public Integer getMaxMsgNumber() {
        return this.maxMsgNumber;
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

    public Integer getTimeout() {
        return this.timeout;
    }

    public void setProvider(final String provider) {
        this.provider = provider;
    }

    public void setModelName(final String modelName) {
        this.modelName = modelName;
    }

    public void setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }

    public void setSecretKey(final String secretKey) {
        this.secretKey = secretKey;
    }

    public void setKnowledgeTxt(final String knowledgeTxt) {
        this.knowledgeTxt = knowledgeTxt;
    }

    public void setQueryRouter(final QueryRouter queryRouter) {
        this.queryRouter = queryRouter;
    }

    public void setTopNumber(final Integer topNumber) {
        this.topNumber = topNumber;
    }

    public void setSimilarity(final Double similarity) {
        this.similarity = similarity;
    }

    public void setMaxMsgNumber(final Integer maxMsgNumber) {
        this.maxMsgNumber = maxMsgNumber;
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

    public void setTimeout(final Integer timeout) {
        this.timeout = timeout;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof AIParams)) {
            return false;
        } else {
            AIParams other = (AIParams)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label203: {
                    Object this$topNumber = this.getTopNumber();
                    Object other$topNumber = other.getTopNumber();
                    if (this$topNumber == null) {
                        if (other$topNumber == null) {
                            break label203;
                        }
                    } else if (this$topNumber.equals(other$topNumber)) {
                        break label203;
                    }

                    return false;
                }

                Object this$similarity = this.getSimilarity();
                Object other$similarity = other.getSimilarity();
                if (this$similarity == null) {
                    if (other$similarity != null) {
                        return false;
                    }
                } else if (!this$similarity.equals(other$similarity)) {
                    return false;
                }

                Object this$maxMsgNumber = this.getMaxMsgNumber();
                Object other$maxMsgNumber = other.getMaxMsgNumber();
                if (this$maxMsgNumber == null) {
                    if (other$maxMsgNumber != null) {
                        return false;
                    }
                } else if (!this$maxMsgNumber.equals(other$maxMsgNumber)) {
                    return false;
                }

                label182: {
                    Object this$temperature = this.getTemperature();
                    Object other$temperature = other.getTemperature();
                    if (this$temperature == null) {
                        if (other$temperature == null) {
                            break label182;
                        }
                    } else if (this$temperature.equals(other$temperature)) {
                        break label182;
                    }

                    return false;
                }

                label175: {
                    Object this$topP = this.getTopP();
                    Object other$topP = other.getTopP();
                    if (this$topP == null) {
                        if (other$topP == null) {
                            break label175;
                        }
                    } else if (this$topP.equals(other$topP)) {
                        break label175;
                    }

                    return false;
                }

                label168: {
                    Object this$presencePenalty = this.getPresencePenalty();
                    Object other$presencePenalty = other.getPresencePenalty();
                    if (this$presencePenalty == null) {
                        if (other$presencePenalty == null) {
                            break label168;
                        }
                    } else if (this$presencePenalty.equals(other$presencePenalty)) {
                        break label168;
                    }

                    return false;
                }

                Object this$frequencyPenalty = this.getFrequencyPenalty();
                Object other$frequencyPenalty = other.getFrequencyPenalty();
                if (this$frequencyPenalty == null) {
                    if (other$frequencyPenalty != null) {
                        return false;
                    }
                } else if (!this$frequencyPenalty.equals(other$frequencyPenalty)) {
                    return false;
                }

                label154: {
                    Object this$maxTokens = this.getMaxTokens();
                    Object other$maxTokens = other.getMaxTokens();
                    if (this$maxTokens == null) {
                        if (other$maxTokens == null) {
                            break label154;
                        }
                    } else if (this$maxTokens.equals(other$maxTokens)) {
                        break label154;
                    }

                    return false;
                }

                Object this$timeout = this.getTimeout();
                Object other$timeout = other.getTimeout();
                if (this$timeout == null) {
                    if (other$timeout != null) {
                        return false;
                    }
                } else if (!this$timeout.equals(other$timeout)) {
                    return false;
                }

                label140: {
                    Object this$provider = this.getProvider();
                    Object other$provider = other.getProvider();
                    if (this$provider == null) {
                        if (other$provider == null) {
                            break label140;
                        }
                    } else if (this$provider.equals(other$provider)) {
                        break label140;
                    }

                    return false;
                }

                Object this$modelName = this.getModelName();
                Object other$modelName = other.getModelName();
                if (this$modelName == null) {
                    if (other$modelName != null) {
                        return false;
                    }
                } else if (!this$modelName.equals(other$modelName)) {
                    return false;
                }

                Object this$baseUrl = this.getBaseUrl();
                Object other$baseUrl = other.getBaseUrl();
                if (this$baseUrl == null) {
                    if (other$baseUrl != null) {
                        return false;
                    }
                } else if (!this$baseUrl.equals(other$baseUrl)) {
                    return false;
                }

                label119: {
                    Object this$apiKey = this.getApiKey();
                    Object other$apiKey = other.getApiKey();
                    if (this$apiKey == null) {
                        if (other$apiKey == null) {
                            break label119;
                        }
                    } else if (this$apiKey.equals(other$apiKey)) {
                        break label119;
                    }

                    return false;
                }

                label112: {
                    Object this$secretKey = this.getSecretKey();
                    Object other$secretKey = other.getSecretKey();
                    if (this$secretKey == null) {
                        if (other$secretKey == null) {
                            break label112;
                        }
                    } else if (this$secretKey.equals(other$secretKey)) {
                        break label112;
                    }

                    return false;
                }

                Object this$knowledgeTxt = this.getKnowledgeTxt();
                Object other$knowledgeTxt = other.getKnowledgeTxt();
                if (this$knowledgeTxt == null) {
                    if (other$knowledgeTxt != null) {
                        return false;
                    }
                } else if (!this$knowledgeTxt.equals(other$knowledgeTxt)) {
                    return false;
                }

                Object this$queryRouter = this.getQueryRouter();
                Object other$queryRouter = other.getQueryRouter();
                if (this$queryRouter == null) {
                    if (other$queryRouter != null) {
                        return false;
                    }
                } else if (!this$queryRouter.equals(other$queryRouter)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AIParams;
    }

    public int hashCode() {
//        int PRIME = true;
        int result = 1;
        Object $topNumber = this.getTopNumber();
        result = result * 59 + ($topNumber == null ? 43 : $topNumber.hashCode());
        Object $similarity = this.getSimilarity();
        result = result * 59 + ($similarity == null ? 43 : $similarity.hashCode());
        Object $maxMsgNumber = this.getMaxMsgNumber();
        result = result * 59 + ($maxMsgNumber == null ? 43 : $maxMsgNumber.hashCode());
        Object $temperature = this.getTemperature();
        result = result * 59 + ($temperature == null ? 43 : $temperature.hashCode());
        Object $topP = this.getTopP();
        result = result * 59 + ($topP == null ? 43 : $topP.hashCode());
        Object $presencePenalty = this.getPresencePenalty();
        result = result * 59 + ($presencePenalty == null ? 43 : $presencePenalty.hashCode());
        Object $frequencyPenalty = this.getFrequencyPenalty();
        result = result * 59 + ($frequencyPenalty == null ? 43 : $frequencyPenalty.hashCode());
        Object $maxTokens = this.getMaxTokens();
        result = result * 59 + ($maxTokens == null ? 43 : $maxTokens.hashCode());
        Object $timeout = this.getTimeout();
        result = result * 59 + ($timeout == null ? 43 : $timeout.hashCode());
        Object $provider = this.getProvider();
        result = result * 59 + ($provider == null ? 43 : $provider.hashCode());
        Object $modelName = this.getModelName();
        result = result * 59 + ($modelName == null ? 43 : $modelName.hashCode());
        Object $baseUrl = this.getBaseUrl();
        result = result * 59 + ($baseUrl == null ? 43 : $baseUrl.hashCode());
        Object $apiKey = this.getApiKey();
        result = result * 59 + ($apiKey == null ? 43 : $apiKey.hashCode());
        Object $secretKey = this.getSecretKey();
        result = result * 59 + ($secretKey == null ? 43 : $secretKey.hashCode());
        Object $knowledgeTxt = this.getKnowledgeTxt();
        result = result * 59 + ($knowledgeTxt == null ? 43 : $knowledgeTxt.hashCode());
        Object $queryRouter = this.getQueryRouter();
        result = result * 59 + ($queryRouter == null ? 43 : $queryRouter.hashCode());
        return result;
    }

    public String toString() {
        return "AIParams(provider=" + this.getProvider() + ", modelName=" + this.getModelName() + ", baseUrl=" + this.getBaseUrl() + ", apiKey=" + this.getApiKey() + ", secretKey=" + this.getSecretKey() + ", knowledgeTxt=" + this.getKnowledgeTxt() + ", queryRouter=" + this.getQueryRouter() + ", topNumber=" + this.getTopNumber() + ", similarity=" + this.getSimilarity() + ", maxMsgNumber=" + this.getMaxMsgNumber() + ", temperature=" + this.getTemperature() + ", topP=" + this.getTopP() + ", presencePenalty=" + this.getPresencePenalty() + ", frequencyPenalty=" + this.getFrequencyPenalty() + ", maxTokens=" + this.getMaxTokens() + ", timeout=" + this.getTimeout() + ")";
    }

    public AIParams(final String provider, final String modelName, final String baseUrl, final String apiKey, final String secretKey, final String knowledgeTxt, final QueryRouter queryRouter, final Integer topNumber, final Double similarity, final Integer maxMsgNumber, final Double temperature, final Double topP, final Double presencePenalty, final Double frequencyPenalty, final Integer maxTokens, final Integer timeout) {
        this.provider = provider;
        this.modelName = modelName;
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.knowledgeTxt = knowledgeTxt;
        this.queryRouter = queryRouter;
        this.topNumber = topNumber;
        this.similarity = similarity;
        this.maxMsgNumber = maxMsgNumber;
        this.temperature = temperature;
        this.topP = topP;
        this.presencePenalty = presencePenalty;
        this.frequencyPenalty = frequencyPenalty;
        this.maxTokens = maxTokens;
        this.timeout = timeout;
    }

    public AIParams() {
    }
}
