//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.vo.api;

import org.jeecg.modules.airag.flow.entity.AiragFlow;

public class FlowDebugParams extends FlowRunParams {
    private static final long serialVersionUID = 1L;
    private AiragFlow flow;

    public FlowDebugParams() {
    }

    public AiragFlow getFlow() {
        return this.flow;
    }

    public void setFlow(AiragFlow flow) {
        this.flow = flow;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof FlowDebugParams)) {
            return false;
        } else {
            FlowDebugParams var2 = (FlowDebugParams)o;
            if (!var2.canEqual(this)) {
                return false;
            } else {
                AiragFlow var3 = this.getFlow();
                AiragFlow var4 = var2.getFlow();
                if (var3 == null) {
                    if (var4 != null) {
                        return false;
                    }
                } else if (!var3.equals(var4)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof FlowDebugParams;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = 1;
        AiragFlow var3 = this.getFlow();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        return var2;
    }

    public String toString() {
        return "FlowDebugParams(flow=" + this.getFlow() + ")";
    }
}
