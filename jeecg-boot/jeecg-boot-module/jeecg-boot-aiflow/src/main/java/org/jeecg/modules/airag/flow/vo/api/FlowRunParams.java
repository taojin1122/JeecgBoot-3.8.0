//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.vo.api;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Consumer;
import javax.servlet.http.HttpServletRequest;
import org.jeecg.modules.airag.common.vo.event.EventData;

public class FlowRunParams implements Serializable {
    private static final long serialVersionUID = 1L;
    private String flowId;
    private Map<String, Object> inputParams;
    private String responseMode;
    private String requestId;
    private Consumer<EventData> eventCallback;
    private String conversationId;
    private String topicId;
    private HttpServletRequest httpRequest;

    public FlowRunParams() {
    }

    public String getFlowId() {
        return this.flowId;
    }

    public Map<String, Object> getInputParams() {
        return this.inputParams;
    }

    public String getResponseMode() {
        return this.responseMode;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public Consumer<EventData> getEventCallback() {
        return this.eventCallback;
    }

    public String getConversationId() {
        return this.conversationId;
    }

    public String getTopicId() {
        return this.topicId;
    }

    public HttpServletRequest getHttpRequest() {
        return this.httpRequest;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public void setInputParams(Map<String, Object> inputParams) {
        this.inputParams = inputParams;
    }

    public void setResponseMode(String responseMode) {
        this.responseMode = responseMode;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setEventCallback(Consumer<EventData> eventCallback) {
        this.eventCallback = eventCallback;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public void setHttpRequest(HttpServletRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof FlowRunParams)) {
            return false;
        } else {
            FlowRunParams var2 = (FlowRunParams)o;
            if (!var2.canEqual(this)) {
                return false;
            } else {
                label107: {
                    String var3 = this.getFlowId();
                    String var4 = var2.getFlowId();
                    if (var3 == null) {
                        if (var4 == null) {
                            break label107;
                        }
                    } else if (var3.equals(var4)) {
                        break label107;
                    }

                    return false;
                }

                Map var5 = this.getInputParams();
                Map var6 = var2.getInputParams();
                if (var5 == null) {
                    if (var6 != null) {
                        return false;
                    }
                } else if (!var5.equals(var6)) {
                    return false;
                }

                String var7 = this.getResponseMode();
                String var8 = var2.getResponseMode();
                if (var7 == null) {
                    if (var8 != null) {
                        return false;
                    }
                } else if (!var7.equals(var8)) {
                    return false;
                }

                label86: {
                    String var9 = this.getRequestId();
                    String var10 = var2.getRequestId();
                    if (var9 == null) {
                        if (var10 == null) {
                            break label86;
                        }
                    } else if (var9.equals(var10)) {
                        break label86;
                    }

                    return false;
                }

                label79: {
                    Consumer var11 = this.getEventCallback();
                    Consumer var12 = var2.getEventCallback();
                    if (var11 == null) {
                        if (var12 == null) {
                            break label79;
                        }
                    } else if (var11.equals(var12)) {
                        break label79;
                    }

                    return false;
                }

                label72: {
                    String var13 = this.getConversationId();
                    String var14 = var2.getConversationId();
                    if (var13 == null) {
                        if (var14 == null) {
                            break label72;
                        }
                    } else if (var13.equals(var14)) {
                        break label72;
                    }

                    return false;
                }

                String var15 = this.getTopicId();
                String var16 = var2.getTopicId();
                if (var15 == null) {
                    if (var16 != null) {
                        return false;
                    }
                } else if (!var15.equals(var16)) {
                    return false;
                }

                HttpServletRequest var17 = this.getHttpRequest();
                HttpServletRequest var18 = var2.getHttpRequest();
                if (var17 == null) {
                    if (var18 != null) {
                        return false;
                    }
                } else if (!var17.equals(var18)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof FlowRunParams;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = 1;
        String var3 = this.getFlowId();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        Map var4 = this.getInputParams();
        var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
        String var5 = this.getResponseMode();
        var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
        String var6 = this.getRequestId();
        var2 = var2 * 59 + (var6 == null ? 43 : var6.hashCode());
        Consumer var7 = this.getEventCallback();
        var2 = var2 * 59 + (var7 == null ? 43 : var7.hashCode());
        String var8 = this.getConversationId();
        var2 = var2 * 59 + (var8 == null ? 43 : var8.hashCode());
        String var9 = this.getTopicId();
        var2 = var2 * 59 + (var9 == null ? 43 : var9.hashCode());
        HttpServletRequest var10 = this.getHttpRequest();
        var2 = var2 * 59 + (var10 == null ? 43 : var10.hashCode());
        return var2;
    }

    public String toString() {
        return "FlowRunParams(flowId=" + this.getFlowId() + ", inputParams=" + this.getInputParams() + ", responseMode=" + this.getResponseMode() + ", requestId=" + this.getRequestId() + ", eventCallback=" + this.getEventCallback() + ", conversationId=" + this.getConversationId() + ", topicId=" + this.getTopicId() + ", httpRequest=" + this.getHttpRequest() + ")";
    }
}
