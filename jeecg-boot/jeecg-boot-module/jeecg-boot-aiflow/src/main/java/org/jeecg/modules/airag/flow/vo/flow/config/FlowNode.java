//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.vo.flow.config;

public class FlowNode {
    private String id;
    private String type;
    private FlowNodeConfig properties;

    public FlowNode() {
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public FlowNodeConfig getProperties() {
        return this.properties;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProperties(FlowNodeConfig properties) {
        this.properties = properties;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof FlowNode)) {
            return false;
        } else {
            FlowNode var2 = (FlowNode)o;
            if (!var2.canEqual(this)) {
                return false;
            } else {
                label47: {
                    String var3 = this.getId();
                    String var4 = var2.getId();
                    if (var3 == null) {
                        if (var4 == null) {
                            break label47;
                        }
                    } else if (var3.equals(var4)) {
                        break label47;
                    }

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

                FlowNodeConfig var7 = this.getProperties();
                FlowNodeConfig var8 = var2.getProperties();
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
        return other instanceof FlowNode;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = 1;
        String var3 = this.getId();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        String var4 = this.getType();
        var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
        FlowNodeConfig var5 = this.getProperties();
        var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
        return var2;
    }

    public String toString() {
        return "FlowNode(id=" + this.getId() + ", type=" + this.getType() + ", properties=" + this.getProperties() + ")";
    }

    public FlowNode(String id, String type, FlowNodeConfig properties) {
        this.id = id;
        this.type = type;
        this.properties = properties;
    }
}
