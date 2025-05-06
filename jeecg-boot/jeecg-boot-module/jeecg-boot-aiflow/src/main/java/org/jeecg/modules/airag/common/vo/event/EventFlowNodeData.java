//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.common.vo.event;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EventFlowNodeData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String type;
    private String text;
    private Map<String, Object> inputs = new HashMap();
    private Object outputs;
    private boolean success;
    private String message;

    EventFlowNodeData(String id, String type, String text, Map<String, Object> inputs, Object outputs, boolean success, String message) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.inputs = inputs;
        this.outputs = outputs;
        this.success = success;
        this.message = message;
    }

    public static EventFlowNodeDataBuilder builder() {
        return new EventFlowNodeDataBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public String getText() {
        return this.text;
    }

    public Map<String, Object> getInputs() {
        return this.inputs;
    }

    public Object getOutputs() {
        return this.outputs;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setInputs(Map<String, Object> inputs) {
        this.inputs = inputs;
    }

    public void setOutputs(Object outputs) {
        this.outputs = outputs;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof EventFlowNodeData)) {
            return false;
        } else {
            EventFlowNodeData var2 = (EventFlowNodeData)o;
            if (!var2.canEqual(this)) {
                return false;
            } else if (this.isSuccess() != var2.isSuccess()) {
                return false;
            } else {
                String var3 = this.getId();
                String var4 = var2.getId();
                if (var3 == null) {
                    if (var4 != null) {
                        return false;
                    }
                } else if (!var3.equals(var4)) {
                    return false;
                }

                String var5 = this.getType();
                String var6 = var2.getType();
                if (var5 == null) {
                    if (var6 != null) {
                        return false;
                    }
                } else if (!var5.equals(var6)) {
                    return false;
                }

                label71: {
                    String var7 = this.getText();
                    String var8 = var2.getText();
                    if (var7 == null) {
                        if (var8 == null) {
                            break label71;
                        }
                    } else if (var7.equals(var8)) {
                        break label71;
                    }

                    return false;
                }

                label64: {
                    Map var9 = this.getInputs();
                    Map var10 = var2.getInputs();
                    if (var9 == null) {
                        if (var10 == null) {
                            break label64;
                        }
                    } else if (var9.equals(var10)) {
                        break label64;
                    }

                    return false;
                }

                Object var11 = this.getOutputs();
                Object var12 = var2.getOutputs();
                if (var11 == null) {
                    if (var12 != null) {
                        return false;
                    }
                } else if (!var11.equals(var12)) {
                    return false;
                }

                String var13 = this.getMessage();
                String var14 = var2.getMessage();
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
        return other instanceof EventFlowNodeData;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = 1;
        var2 = var2 * 59 + (this.isSuccess() ? 79 : 97);
        String var3 = this.getId();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        String var4 = this.getType();
        var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
        String var5 = this.getText();
        var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
        Map var6 = this.getInputs();
        var2 = var2 * 59 + (var6 == null ? 43 : var6.hashCode());
        Object var7 = this.getOutputs();
        var2 = var2 * 59 + (var7 == null ? 43 : var7.hashCode());
        String var8 = this.getMessage();
        var2 = var2 * 59 + (var8 == null ? 43 : var8.hashCode());
        return var2;
    }

    public String toString() {
        return "EventFlowNodeData(id=" + this.getId() + ", type=" + this.getType() + ", text=" + this.getText() + ", inputs=" + this.getInputs() + ", outputs=" + this.getOutputs() + ", success=" + this.isSuccess() + ", message=" + this.getMessage() + ")";
    }

    public static class EventFlowNodeDataBuilder {
        private String id;
        private String type;
        private String text;
        private Map<String, Object> inputs;
        private Object outputs;
        private boolean success;
        private String message;

        EventFlowNodeDataBuilder() {
        }

        public EventFlowNodeDataBuilder id(String id) {
            this.id = id;
            return this;
        }

        public EventFlowNodeDataBuilder type(String type) {
            this.type = type;
            return this;
        }

        public EventFlowNodeDataBuilder text(String text) {
            this.text = text;
            return this;
        }

        public EventFlowNodeDataBuilder inputs(Map<String, Object> inputs) {
            this.inputs = inputs;
            return this;
        }

        public EventFlowNodeDataBuilder outputs(Object outputs) {
            this.outputs = outputs;
            return this;
        }

        public EventFlowNodeDataBuilder success(boolean success) {
            this.success = success;
            return this;
        }

        public EventFlowNodeDataBuilder message(String message) {
            this.message = message;
            return this;
        }

        public EventFlowNodeData build() {
            return new EventFlowNodeData(this.id, this.type, this.text, this.inputs, this.outputs, this.success, this.message);
        }

        public String toString() {
            return "EventFlowNodeData.EventFlowNodeDataBuilder(id=" + this.id + ", type=" + this.type + ", text=" + this.text + ", inputs=" + this.inputs + ", outputs=" + this.outputs + ", success=" + this.success + ", message=" + this.message + ")";
        }
    }
}
