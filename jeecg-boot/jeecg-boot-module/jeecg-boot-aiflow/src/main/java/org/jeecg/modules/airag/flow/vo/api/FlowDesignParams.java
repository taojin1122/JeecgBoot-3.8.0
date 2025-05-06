//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.vo.api;

import java.io.Serializable;

public class FlowDesignParams implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String chain;
    private String design;
    private String name;

    public FlowDesignParams() {
    }

    public String getId() {
        return this.id;
    }

    public String getChain() {
        return this.chain;
    }

    public String getDesign() {
        return this.design;
    }

    public String getName() {
        return this.name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof FlowDesignParams)) {
            return false;
        } else {
            FlowDesignParams var2 = (FlowDesignParams)o;
            if (!var2.canEqual(this)) {
                return false;
            } else {
                label59: {
                    String var3 = this.getId();
                    String var4 = var2.getId();
                    if (var3 == null) {
                        if (var4 == null) {
                            break label59;
                        }
                    } else if (var3.equals(var4)) {
                        break label59;
                    }

                    return false;
                }

                String var5 = this.getChain();
                String var6 = var2.getChain();
                if (var5 == null) {
                    if (var6 != null) {
                        return false;
                    }
                } else if (!var5.equals(var6)) {
                    return false;
                }

                String var7 = this.getDesign();
                String var8 = var2.getDesign();
                if (var7 == null) {
                    if (var8 != null) {
                        return false;
                    }
                } else if (!var7.equals(var8)) {
                    return false;
                }

                String var9 = this.getName();
                String var10 = var2.getName();
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
        return other instanceof FlowDesignParams;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = 1;
        String var3 = this.getId();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        String var4 = this.getChain();
        var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
        String var5 = this.getDesign();
        var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
        String var6 = this.getName();
        var2 = var2 * 59 + (var6 == null ? 43 : var6.hashCode());
        return var2;
    }

    public String toString() {
        return "FlowDesignParams(id=" + this.getId() + ", chain=" + this.getChain() + ", design=" + this.getDesign() + ", name=" + this.getName() + ")";
    }
}
