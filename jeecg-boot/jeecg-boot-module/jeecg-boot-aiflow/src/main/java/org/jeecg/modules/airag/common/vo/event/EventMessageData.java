//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.common.vo.event;

import java.io.Serializable;

public class EventMessageData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fromNodeId;
    private String message;

    EventMessageData(String fromNodeId, String message) {
        this.fromNodeId = fromNodeId;
        this.message = message;
    }

    public static EventMessageDataBuilder builder() {
        return new EventMessageDataBuilder();
    }

    public String getFromNodeId() {
        return this.fromNodeId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setFromNodeId(String fromNodeId) {
        this.fromNodeId = fromNodeId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof EventMessageData)) {
            return false;
        } else {
            EventMessageData var2 = (EventMessageData)o;
            if (!var2.canEqual(this)) {
                return false;
            } else {
                String var3 = this.getFromNodeId();
                String var4 = var2.getFromNodeId();
                if (var3 == null) {
                    if (var4 != null) {
                        return false;
                    }
                } else if (!var3.equals(var4)) {
                    return false;
                }

                String var5 = this.getMessage();
                String var6 = var2.getMessage();
                if (var5 == null) {
                    if (var6 != null) {
                        return false;
                    }
                } else if (!var5.equals(var6)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof EventMessageData;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = 1;
        String var3 = this.getFromNodeId();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        String var4 = this.getMessage();
        var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
        return var2;
    }

    public String toString() {
        return "EventMessageData(fromNodeId=" + this.getFromNodeId() + ", message=" + this.getMessage() + ")";
    }

    public static class EventMessageDataBuilder {
        private String fromNodeId;
        private String message;

        EventMessageDataBuilder() {
        }

        public EventMessageDataBuilder fromNodeId(String fromNodeId) {
            this.fromNodeId = fromNodeId;
            return this;
        }

        public EventMessageDataBuilder message(String message) {
            this.message = message;
            return this;
        }

        public EventMessageData build() {
            return new EventMessageData(this.fromNodeId, this.message);
        }

        public String toString() {
            return "EventMessageData.EventMessageDataBuilder(fromNodeId=" + this.fromNodeId + ", message=" + this.message + ")";
        }
    }
}
