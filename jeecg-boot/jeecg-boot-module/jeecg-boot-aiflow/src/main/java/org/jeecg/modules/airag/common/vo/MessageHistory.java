//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.common.vo;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

public class MessageHistory implements Serializable {
    private static final long serialVersionUID = 3238429500037511283L;
    String conversationId;
    String topicId;
    String role;
    String content;
    List<ImageHistory> images;
    String datetime;

    public static MessageHistoryBuilder builder() {
        return new MessageHistoryBuilder();
    }

    public String getConversationId() {
        return this.conversationId;
    }

    public String getTopicId() {
        return this.topicId;
    }

    public String getRole() {
        return this.role;
    }

    public String getContent() {
        return this.content;
    }

    public List<ImageHistory> getImages() {
        return this.images;
    }

    public String getDatetime() {
        return this.datetime;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImages(List<ImageHistory> images) {
        this.images = images;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof MessageHistory)) {
            return false;
        } else {
            MessageHistory var2 = (MessageHistory)o;
            if (!var2.canEqual(this)) {
                return false;
            } else {
                String var3 = this.getConversationId();
                String var4 = var2.getConversationId();
                if (var3 == null) {
                    if (var4 != null) {
                        return false;
                    }
                } else if (!var3.equals(var4)) {
                    return false;
                }

                String var5 = this.getTopicId();
                String var6 = var2.getTopicId();
                if (var5 == null) {
                    if (var6 != null) {
                        return false;
                    }
                } else if (!var5.equals(var6)) {
                    return false;
                }

                String var7 = this.getRole();
                String var8 = var2.getRole();
                if (var7 == null) {
                    if (var8 != null) {
                        return false;
                    }
                } else if (!var7.equals(var8)) {
                    return false;
                }

                label62: {
                    String var9 = this.getContent();
                    String var10 = var2.getContent();
                    if (var9 == null) {
                        if (var10 == null) {
                            break label62;
                        }
                    } else if (var9.equals(var10)) {
                        break label62;
                    }

                    return false;
                }

                label55: {
                    List var11 = this.getImages();
                    List var12 = var2.getImages();
                    if (var11 == null) {
                        if (var12 == null) {
                            break label55;
                        }
                    } else if (var11.equals(var12)) {
                        break label55;
                    }

                    return false;
                }

                String var13 = this.getDatetime();
                String var14 = var2.getDatetime();
                if (var13 == null) {
                    if (var14 != null) {
                        return false;
                    }
                } else if (!var13.equals(var14)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof MessageHistory;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = 1;
        String var3 = this.getConversationId();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        String var4 = this.getTopicId();
        var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
        String var5 = this.getRole();
        var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
        String var6 = this.getContent();
        var2 = var2 * 59 + (var6 == null ? 43 : var6.hashCode());
        List var7 = this.getImages();
        var2 = var2 * 59 + (var7 == null ? 43 : var7.hashCode());
        String var8 = this.getDatetime();
        var2 = var2 * 59 + (var8 == null ? 43 : var8.hashCode());
        return var2;
    }

    public String toString() {
        return "MessageHistory(conversationId=" + this.getConversationId() + ", topicId=" + this.getTopicId() + ", role=" + this.getRole() + ", content=" + this.getContent() + ", images=" + this.getImages() + ", datetime=" + this.getDatetime() + ")";
    }

    public MessageHistory() {
    }

    public MessageHistory(String conversationId, String topicId, String role, String content, List<ImageHistory> images, String datetime) {
        this.conversationId = conversationId;
        this.topicId = topicId;
        this.role = role;
        this.content = content;
        this.images = images;
        this.datetime = datetime;
    }

    public static class MessageHistoryBuilder {
        private String conversationId;
        private String topicId;
        private String role;
        private String content;
        private List<ImageHistory> images;
        private String datetime;

        MessageHistoryBuilder() {
        }

        public MessageHistoryBuilder conversationId(String conversationId) {
            this.conversationId = conversationId;
            return this;
        }

        public MessageHistoryBuilder topicId(String topicId) {
            this.topicId = topicId;
            return this;
        }

        public MessageHistoryBuilder role(String role) {
            this.role = role;
            return this;
        }

        public MessageHistoryBuilder content(String content) {
            this.content = content;
            return this;
        }

        public MessageHistoryBuilder images(List<ImageHistory> images) {
            this.images = images;
            return this;
        }

        public MessageHistoryBuilder datetime(String datetime) {
            this.datetime = datetime;
            return this;
        }

        public MessageHistory build() {
            return new MessageHistory(this.conversationId, this.topicId, this.role, this.content, this.images, this.datetime);
        }

        public String toString() {
            return "MessageHistory.MessageHistoryBuilder(conversationId=" + this.conversationId + ", topicId=" + this.topicId + ", role=" + this.role + ", content=" + this.content + ", images=" + this.images + ", datetime=" + this.datetime + ")";
        }
    }

    public static class ImageHistory {
        private URI url;
        private String base64Data;
        private String mimeType;

        public ImageHistory() {
        }

        public ImageHistory(URI url, String base64Data, String mimeType) {
            this.url = url;
            this.base64Data = base64Data;
            this.mimeType = mimeType;
        }

        public static ImageHistory from(URI url, String base64Data, String mimeType) {
            return new ImageHistory(url, base64Data, mimeType);
        }

        public URI getUrl() {
            return this.url;
        }

        public String getBase64Data() {
            return this.base64Data;
        }

        public String getMimeType() {
            return this.mimeType;
        }

        public void setUrl(URI url) {
            this.url = url;
        }

        public void setBase64Data(String base64Data) {
            this.base64Data = base64Data;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof ImageHistory)) {
                return false;
            } else {
                ImageHistory var2 = (ImageHistory)o;
                if (!var2.canEqual(this)) {
                    return false;
                } else {
                    label47: {
                        URI var3 = this.getUrl();
                        URI var4 = var2.getUrl();
                        if (var3 == null) {
                            if (var4 == null) {
                                break label47;
                            }
                        } else if (var3.equals(var4)) {
                            break label47;
                        }

                        return false;
                    }

                    String var5 = this.getBase64Data();
                    String var6 = var2.getBase64Data();
                    if (var5 == null) {
                        if (var6 != null) {
                            return false;
                        }
                    } else if (!var5.equals(var6)) {
                        return false;
                    }

                    String var7 = this.getMimeType();
                    String var8 = var2.getMimeType();
                    if (var7 == null) {
                        if (var8 != null) {
                            return false;
                        }
                    } else if (!var7.equals(var8)) {
                        return false;
                    }

                    return true;
                }
            }
        }

        protected boolean canEqual(Object other) {
            return other instanceof ImageHistory;
        }

        public int hashCode() {
            boolean var1 = true;
            int var2 = 1;
            URI var3 = this.getUrl();
            var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
            String var4 = this.getBase64Data();
            var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
            String var5 = this.getMimeType();
            var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
            return var2;
        }

        public String toString() {
            return "MessageHistory.ImageHistory(url=" + this.getUrl() + ", base64Data=" + this.getBase64Data() + ", mimeType=" + this.getMimeType() + ")";
        }
    }
}
