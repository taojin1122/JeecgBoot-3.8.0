//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.chatgpt.dto.chat;

public class MultiChatMessage {
    private String role;
    private String content;
    private String name;

    public MultiChatMessage() {
    }

    public MultiChatMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public MultiChatMessage(Builder builder) {
        this.setContent(builder.content);
        this.setRole(builder.role);
        this.setName(builder.name);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getRole() {
        return this.role;
    }

    public String getContent() {
        return this.content;
    }

    public String getName() {
        return this.name;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof MultiChatMessage)) {
            return false;
        } else {
            MultiChatMessage other = (MultiChatMessage)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$role = this.getRole();
                    Object other$role = other.getRole();
                    if (this$role == null) {
                        if (other$role == null) {
                            break label47;
                        }
                    } else if (this$role.equals(other$role)) {
                        break label47;
                    }

                    return false;
                }

                Object this$content = this.getContent();
                Object other$content = other.getContent();
                if (this$content == null) {
                    if (other$content != null) {
                        return false;
                    }
                } else if (!this$content.equals(other$content)) {
                    return false;
                }

                Object this$name = this.getName();
                Object other$name = other.getName();
                if (this$name == null) {
                    if (other$name != null) {
                        return false;
                    }
                } else if (!this$name.equals(other$name)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MultiChatMessage;
    }

    public int hashCode() {
//        int PRIME = true;
        int result = 1;
        Object $role = this.getRole();
        result = result * 59 + ($role == null ? 43 : $role.hashCode());
        Object $content = this.getContent();
        result = result * 59 + ($content == null ? 43 : $content.hashCode());
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public String toString() {
        return "MultiChatMessage(role=" + this.getRole() + ", content=" + this.getContent() + ", name=" + this.getName() + ")";
    }

    public static enum Role {
        SYSTEM("system"),
        USER("user"),
        ASSISTANT("assistant"),
        FUNCTION("function"),
        TOOL("tool");

        private final String name;

        private Role(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    public static final class Builder {
        private String role;
        private String content;
        private String name;

        public Builder() {
        }

        public Builder role(Role role) {
            this.role = role.getName();
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public MultiChatMessage build() {
            return new MultiChatMessage(this);
        }
    }
}
