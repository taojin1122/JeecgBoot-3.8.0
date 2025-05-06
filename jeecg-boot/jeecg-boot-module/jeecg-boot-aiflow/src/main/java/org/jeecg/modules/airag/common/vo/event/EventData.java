//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.common.vo.event;

import com.alibaba.fastjson.JSONObject;
import java.io.Serializable;

public class EventData implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String EVENT_NODE_STARTED = "NODE_STARTED";
    public static final String EVENT_NODE_FINISHED = "NODE_FINISHED";
    public static final String EVENT_MESSAGE = "MESSAGE";
    public static final String EVENT_MESSAGE_END = "MESSAGE_END";
    public static final String EVENT_FLOW_STARTED = "FLOW_STARTED";
    public static final String EVENT_FLOW_FINISHED = "FLOW_FINISHED";
    public static final String EVENT_FLOW_ERROR = "ERROR";
    private String event;
    private String flowId;
    private String requestId;
    private String conversationId;
    private String topicId;
    private Object data;

    public EventData(String requestId, String flowId, String event) {
        this.requestId = requestId;
        this.flowId = flowId;
        this.event = event;
    }

    public EventData(String requestId, String flowId, String event, String conversationId, String topicId) {
        this.event = event;
        this.flowId = flowId;
        this.requestId = requestId;
        this.conversationId = conversationId;
        this.topicId = topicId;
    }

    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public String getEvent() {
        return this.event;
    }

    public String getFlowId() {
        return this.flowId;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public String getConversationId() {
        return this.conversationId;
    }

    public String getTopicId() {
        return this.topicId;
    }

    public Object getData() {
        return this.data;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof EventData)) {
            return false;
        } else {
            EventData var2 = (EventData)o;
            if (!var2.canEqual(this)) {
                return false;
            } else {
                String var3 = this.getEvent();
                String var4 = var2.getEvent();
                if (var3 == null) {
                    if (var4 != null) {
                        return false;
                    }
                } else if (!var3.equals(var4)) {
                    return false;
                }

                String var5 = this.getFlowId();
                String var6 = var2.getFlowId();
                if (var5 == null) {
                    if (var6 != null) {
                        return false;
                    }
                } else if (!var5.equals(var6)) {
                    return false;
                }

                String var7 = this.getRequestId();
                String var8 = var2.getRequestId();
                if (var7 == null) {
                    if (var8 != null) {
                        return false;
                    }
                } else if (!var7.equals(var8)) {
                    return false;
                }

                label62: {
                    String var9 = this.getConversationId();
                    String var10 = var2.getConversationId();
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
                    String var11 = this.getTopicId();
                    String var12 = var2.getTopicId();
                    if (var11 == null) {
                        if (var12 == null) {
                            break label55;
                        }
                    } else if (var11.equals(var12)) {
                        break label55;
                    }

                    return false;
                }

                Object var13 = this.getData();
                Object var14 = var2.getData();
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
        return other instanceof EventData;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = 1;
        String var3 = this.getEvent();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        String var4 = this.getFlowId();
        var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
        String var5 = this.getRequestId();
        var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
        String var6 = this.getConversationId();
        var2 = var2 * 59 + (var6 == null ? 43 : var6.hashCode());
        String var7 = this.getTopicId();
        var2 = var2 * 59 + (var7 == null ? 43 : var7.hashCode());
        Object var8 = this.getData();
        var2 = var2 * 59 + (var8 == null ? 43 : var8.hashCode());
        return var2;
    }

    public EventData() {
    }
}
