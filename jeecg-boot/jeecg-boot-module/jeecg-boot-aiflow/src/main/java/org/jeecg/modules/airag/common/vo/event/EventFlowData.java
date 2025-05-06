//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.common.vo.event;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EventFlowData implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean success;
    private String message;
    private String executeSteps;
    private Map<String, Object> inputs = new HashMap();
    private Object outputs;

    EventFlowData(boolean success, String message, String executeSteps, Map<String, Object> inputs, Object outputs) {
        this.success = success;
        this.message = message;
        this.executeSteps = executeSteps;
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public static EventFlowDataBuilder builder() {
        return new EventFlowDataBuilder();
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }

    public String getExecuteSteps() {
        return this.executeSteps;
    }

    public Map<String, Object> getInputs() {
        return this.inputs;
    }

    public Object getOutputs() {
        return this.outputs;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setExecuteSteps(String executeSteps) {
        this.executeSteps = executeSteps;
    }

    public void setInputs(Map<String, Object> inputs) {
        this.inputs = inputs;
    }

    public void setOutputs(Object outputs) {
        this.outputs = outputs;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof EventFlowData)) {
            return false;
        } else {
            EventFlowData var2 = (EventFlowData)o;
            if (!var2.canEqual(this)) {
                return false;
            } else if (this.isSuccess() != var2.isSuccess()) {
                return false;
            } else {
                label61: {
                    String var3 = this.getMessage();
                    String var4 = var2.getMessage();
                    if (var3 == null) {
                        if (var4 == null) {
                            break label61;
                        }
                    } else if (var3.equals(var4)) {
                        break label61;
                    }

                    return false;
                }

                label54: {
                    String var5 = this.getExecuteSteps();
                    String var6 = var2.getExecuteSteps();
                    if (var5 == null) {
                        if (var6 == null) {
                            break label54;
                        }
                    } else if (var5.equals(var6)) {
                        break label54;
                    }

                    return false;
                }

                Map var7 = this.getInputs();
                Map var8 = var2.getInputs();
                if (var7 == null) {
                    if (var8 != null) {
                        return false;
                    }
                } else if (!var7.equals(var8)) {
                    return false;
                }

                Object var9 = this.getOutputs();
                Object var10 = var2.getOutputs();
                if (var9 == null) {
                    if (var10 != null) {
                        return false;
                    }
                } else if (!var9.equals(var10)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof EventFlowData;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = 1;
        var2 = var2 * 59 + (this.isSuccess() ? 79 : 97);
        String var3 = this.getMessage();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        String var4 = this.getExecuteSteps();
        var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
        Map var5 = this.getInputs();
        var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
        Object var6 = this.getOutputs();
        var2 = var2 * 59 + (var6 == null ? 43 : var6.hashCode());
        return var2;
    }

    public String toString() {
        return "EventFlowData(success=" + this.isSuccess() + ", message=" + this.getMessage() + ", executeSteps=" + this.getExecuteSteps() + ", inputs=" + this.getInputs() + ", outputs=" + this.getOutputs() + ")";
    }

    public static class EventFlowDataBuilder {
        private boolean success;
        private String message;
        private String executeSteps;
        private Map<String, Object> inputs;
        private Object outputs;

        EventFlowDataBuilder() {
        }

        public EventFlowDataBuilder success(boolean success) {
            this.success = success;
            return this;
        }

        public EventFlowDataBuilder message(String message) {
            this.message = message;
            return this;
        }

        public EventFlowDataBuilder executeSteps(String executeSteps) {
            this.executeSteps = executeSteps;
            return this;
        }

        public EventFlowDataBuilder inputs(Map<String, Object> inputs) {
            this.inputs = inputs;
            return this;
        }

        public EventFlowDataBuilder outputs(Object outputs) {
            this.outputs = outputs;
            return this;
        }

        public EventFlowData build() {
            return new EventFlowData(this.success, this.message, this.executeSteps, this.inputs, this.outputs);
        }

        public String toString() {
            return "EventFlowData.EventFlowDataBuilder(success=" + this.success + ", message=" + this.message + ", executeSteps=" + this.executeSteps + ", inputs=" + this.inputs + ", outputs=" + this.outputs + ")";
        }
    }
}
