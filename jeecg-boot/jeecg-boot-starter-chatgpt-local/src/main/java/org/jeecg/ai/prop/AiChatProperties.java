//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.ai.prop;

import dev.langchain4j.model.openai.OpenAiChatModelName;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
    prefix = "jeecg.ai-chat"
)
public class AiChatProperties {
    public static final String PREFIX = "jeecg.ai-chat";
    private boolean enabled = false;
    private String provider = "OPENAI";
    private Credential credential = new Credential();
    /** @deprecated */
    private String apiKey = "";
    private String model;
    private String apiHost;
    private Metadata metadata;
    /** @deprecated */
    private int timeout;
    private Proxy proxy;

    public String getApiKey() {
        return this.credential.getApiKey();
    }

    public void setApiKey(String apiKey) {
        this.credential.setApiKey(apiKey);
    }

    public int getTimeout() {
        return this.metadata.getTimeout();
    }

    public void setTimeout(int timeout) {
        this.metadata.setTimeout(timeout);
    }

    public AiChatProperties() {
        this.model = OpenAiChatModelName.GPT_3_5_TURBO.toString();
        this.apiHost = "https://api.openai.com/v1/";
        this.metadata = new Metadata();
        this.timeout = this.metadata.getTimeout();
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String getProvider() {
        return this.provider;
    }

    public Credential getCredential() {
        return this.credential;
    }

    public String getModel() {
        return this.model;
    }

    public String getApiHost() {
        return this.apiHost;
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setProvider(final String provider) {
        this.provider = provider;
    }

    public void setCredential(final Credential credential) {
        this.credential = credential;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public void setApiHost(final String apiHost) {
        this.apiHost = apiHost;
    }

    public void setMetadata(final Metadata metadata) {
        this.metadata = metadata;
    }

    public void setProxy(final Proxy proxy) {
        this.proxy = proxy;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof AiChatProperties)) {
            return false;
        } else {
            AiChatProperties other = (AiChatProperties)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.isEnabled() != other.isEnabled()) {
                return false;
            } else if (this.getTimeout() != other.getTimeout()) {
                return false;
            } else {
                label100: {
                    Object this$provider = this.getProvider();
                    Object other$provider = other.getProvider();
                    if (this$provider == null) {
                        if (other$provider == null) {
                            break label100;
                        }
                    } else if (this$provider.equals(other$provider)) {
                        break label100;
                    }

                    return false;
                }

                Object this$credential = this.getCredential();
                Object other$credential = other.getCredential();
                if (this$credential == null) {
                    if (other$credential != null) {
                        return false;
                    }
                } else if (!this$credential.equals(other$credential)) {
                    return false;
                }

                label86: {
                    Object this$apiKey = this.getApiKey();
                    Object other$apiKey = other.getApiKey();
                    if (this$apiKey == null) {
                        if (other$apiKey == null) {
                            break label86;
                        }
                    } else if (this$apiKey.equals(other$apiKey)) {
                        break label86;
                    }

                    return false;
                }

                label79: {
                    Object this$model = this.getModel();
                    Object other$model = other.getModel();
                    if (this$model == null) {
                        if (other$model == null) {
                            break label79;
                        }
                    } else if (this$model.equals(other$model)) {
                        break label79;
                    }

                    return false;
                }

                label72: {
                    Object this$apiHost = this.getApiHost();
                    Object other$apiHost = other.getApiHost();
                    if (this$apiHost == null) {
                        if (other$apiHost == null) {
                            break label72;
                        }
                    } else if (this$apiHost.equals(other$apiHost)) {
                        break label72;
                    }

                    return false;
                }

                Object this$metadata = this.getMetadata();
                Object other$metadata = other.getMetadata();
                if (this$metadata == null) {
                    if (other$metadata != null) {
                        return false;
                    }
                } else if (!this$metadata.equals(other$metadata)) {
                    return false;
                }

                Object this$proxy = this.getProxy();
                Object other$proxy = other.getProxy();
                if (this$proxy == null) {
                    if (other$proxy != null) {
                        return false;
                    }
                } else if (!this$proxy.equals(other$proxy)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AiChatProperties;
    }

    public int hashCode() {
//        int PRIME = true;
        int result = 1;
        result = result * 59 + (this.isEnabled() ? 79 : 97);
        result = result * 59 + this.getTimeout();
        Object $provider = this.getProvider();
        result = result * 59 + ($provider == null ? 43 : $provider.hashCode());
        Object $credential = this.getCredential();
        result = result * 59 + ($credential == null ? 43 : $credential.hashCode());
        Object $apiKey = this.getApiKey();
        result = result * 59 + ($apiKey == null ? 43 : $apiKey.hashCode());
        Object $model = this.getModel();
        result = result * 59 + ($model == null ? 43 : $model.hashCode());
        Object $apiHost = this.getApiHost();
        result = result * 59 + ($apiHost == null ? 43 : $apiHost.hashCode());
        Object $metadata = this.getMetadata();
        result = result * 59 + ($metadata == null ? 43 : $metadata.hashCode());
        Object $proxy = this.getProxy();
        result = result * 59 + ($proxy == null ? 43 : $proxy.hashCode());
        return result;
    }

    public String toString() {
        return "AiChatProperties(enabled=" + this.isEnabled() + ", provider=" + this.getProvider() + ", credential=" + this.getCredential() + ", apiKey=" + this.getApiKey() + ", model=" + this.getModel() + ", apiHost=" + this.getApiHost() + ", metadata=" + this.getMetadata() + ", timeout=" + this.getTimeout() + ", proxy=" + this.getProxy() + ")";
    }

    public static class Metadata {
        private int timeout = 60;

        public Metadata() {
        }

        public int getTimeout() {
            return this.timeout;
        }

        public void setTimeout(final int timeout) {
            this.timeout = timeout;
        }

        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof Metadata)) {
                return false;
            } else {
                Metadata other = (Metadata)o;
                if (!other.canEqual(this)) {
                    return false;
                } else {
                    return this.getTimeout() == other.getTimeout();
                }
            }
        }

        protected boolean canEqual(final Object other) {
            return other instanceof Metadata;
        }

        public int hashCode() {
//            int PRIME = true;
            int result = 1;
            result = result * 59 + this.getTimeout();
            return result;
        }

        public String toString() {
            return "AiChatProperties.Metadata(timeout=" + this.getTimeout() + ")";
        }
    }

    public static class Credential {
        private String apiKey = "";
        private String secretKey;

        public Credential() {
        }

        public String getApiKey() {
            return this.apiKey;
        }

        public String getSecretKey() {
            return this.secretKey;
        }

        public void setApiKey(final String apiKey) {
            this.apiKey = apiKey;
        }

        public void setSecretKey(final String secretKey) {
            this.secretKey = secretKey;
        }

        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof Credential)) {
                return false;
            } else {
                Credential other = (Credential)o;
                if (!other.canEqual(this)) {
                    return false;
                } else {
                    Object this$apiKey = this.getApiKey();
                    Object other$apiKey = other.getApiKey();
                    if (this$apiKey == null) {
                        if (other$apiKey != null) {
                            return false;
                        }
                    } else if (!this$apiKey.equals(other$apiKey)) {
                        return false;
                    }

                    Object this$secretKey = this.getSecretKey();
                    Object other$secretKey = other.getSecretKey();
                    if (this$secretKey == null) {
                        if (other$secretKey != null) {
                            return false;
                        }
                    } else if (!this$secretKey.equals(other$secretKey)) {
                        return false;
                    }

                    return true;
                }
            }
        }

        protected boolean canEqual(final Object other) {
            return other instanceof Credential;
        }

        public int hashCode() {
//            int PRIME = true;
            int result = 1;
            Object $apiKey = this.getApiKey();
            result = result * 59 + ($apiKey == null ? 43 : $apiKey.hashCode());
            Object $secretKey = this.getSecretKey();
            result = result * 59 + ($secretKey == null ? 43 : $secretKey.hashCode());
            return result;
        }

        public String toString() {
            return "AiChatProperties.Credential(apiKey=" + this.getApiKey() + ", secretKey=" + this.getSecretKey() + ")";
        }
    }

    public static class Proxy {
        String host;
        Integer port;

        public Proxy() {
        }

        public String getHost() {
            return this.host;
        }

        public Integer getPort() {
            return this.port;
        }

        public void setHost(final String host) {
            this.host = host;
        }

        public void setPort(final Integer port) {
            this.port = port;
        }

        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof Proxy)) {
                return false;
            } else {
                Proxy other = (Proxy)o;
                if (!other.canEqual(this)) {
                    return false;
                } else {
                    Object this$port = this.getPort();
                    Object other$port = other.getPort();
                    if (this$port == null) {
                        if (other$port != null) {
                            return false;
                        }
                    } else if (!this$port.equals(other$port)) {
                        return false;
                    }

                    Object this$host = this.getHost();
                    Object other$host = other.getHost();
                    if (this$host == null) {
                        if (other$host != null) {
                            return false;
                        }
                    } else if (!this$host.equals(other$host)) {
                        return false;
                    }

                    return true;
                }
            }
        }

        protected boolean canEqual(final Object other) {
            return other instanceof Proxy;
        }

        public int hashCode() {
//            int PRIME = true;
            int result = 1;
            Object $port = this.getPort();
            result = result * 59 + ($port == null ? 43 : $port.hashCode());
            Object $host = this.getHost();
            result = result * 59 + ($host == null ? 43 : $host.hashCode());
            return result;
        }

        public String toString() {
            return "AiChatProperties.Proxy(host=" + this.getHost() + ", port=" + this.getPort() + ")";
        }
    }
}
