//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.common.handler;

import java.util.List;
import org.jeecg.ai.handler.AIParams;

public class AIChatParams extends AIParams {
    List<String> knowIds;
    Boolean noThinking;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof AIChatParams)) {
            return false;
        } else {
            AIChatParams var2 = (AIChatParams)o;
            if (!var2.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                Boolean var3 = this.getNoThinking();
                Boolean var4 = var2.getNoThinking();
                if (var3 == null) {
                    if (var4 != null) {
                        return false;
                    }
                } else if (!var3.equals(var4)) {
                    return false;
                }

                List var5 = this.getKnowIds();
                List var6 = var2.getKnowIds();
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
        return other instanceof AIChatParams;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = super.hashCode();
        Boolean var3 = this.getNoThinking();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        List var4 = this.getKnowIds();
        var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
        return var2;
    }

    public List<String> getKnowIds() {
        return this.knowIds;
    }

    public Boolean getNoThinking() {
        return this.noThinking;
    }

    public void setKnowIds(List<String> knowIds) {
        this.knowIds = knowIds;
    }

    public void setNoThinking(Boolean noThinking) {
        this.noThinking = noThinking;
    }

    public String toString() {
        return "AIChatParams(knowIds=" + this.getKnowIds() + ", noThinking=" + this.getNoThinking() + ")";
    }

    public AIChatParams() {
    }
}
