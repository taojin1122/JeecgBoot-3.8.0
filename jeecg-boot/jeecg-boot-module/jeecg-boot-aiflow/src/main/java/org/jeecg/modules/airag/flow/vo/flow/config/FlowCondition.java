//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.vo.flow.config;

public class FlowCondition {
    private String nodeId;
    private String field;
    private String operator;
    private String value;

    public FlowCondition() {
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public String getField() {
        return this.field;
    }

    public String getOperator() {
        return this.operator;
    }

    public String getValue() {
        return this.value;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof FlowCondition)) {
            return false;
        } else {
            FlowCondition var2 = (FlowCondition)o;
            if (!var2.canEqual(this)) {
                return false;
            } else {
                label59: {
                    String var3 = this.getNodeId();
                    String var4 = var2.getNodeId();
                    if (var3 == null) {
                        if (var4 == null) {
                            break label59;
                        }
                    } else if (var3.equals(var4)) {
                        break label59;
                    }

                    return false;
                }

                String var5 = this.getField();
                String var6 = var2.getField();
                if (var5 == null) {
                    if (var6 != null) {
                        return false;
                    }
                } else if (!var5.equals(var6)) {
                    return false;
                }

                String var7 = this.getOperator();
                String var8 = var2.getOperator();
                if (var7 == null) {
                    if (var8 != null) {
                        return false;
                    }
                } else if (!var7.equals(var8)) {
                    return false;
                }

                String var9 = this.getValue();
                String var10 = var2.getValue();
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
        return other instanceof FlowCondition;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = 1;
        String var3 = this.getNodeId();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        String var4 = this.getField();
        var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
        String var5 = this.getOperator();
        var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
        String var6 = this.getValue();
        var2 = var2 * 59 + (var6 == null ? 43 : var6.hashCode());
        return var2;
    }

    public String toString() {
        return "FlowCondition(nodeId=" + this.getNodeId() + ", field=" + this.getField() + ", operator=" + this.getOperator() + ", value=" + this.getValue() + ")";
    }
}
