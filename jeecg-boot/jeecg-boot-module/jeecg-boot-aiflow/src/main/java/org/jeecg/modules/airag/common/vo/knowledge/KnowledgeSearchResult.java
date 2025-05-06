//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.common.vo.knowledge;

import java.util.List;
import java.util.Map;

public class KnowledgeSearchResult {
    String data;
    List<Map<String, Object>> documents;

    public KnowledgeSearchResult(String data, List<Map<String, Object>> documents) {
        this.data = data;
        this.documents = documents;
    }

    public String getData() {
        return this.data;
    }

    public List<Map<String, Object>> getDocuments() {
        return this.documents;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setDocuments(List<Map<String, Object>> documents) {
        this.documents = documents;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof KnowledgeSearchResult)) {
            return false;
        } else {
            KnowledgeSearchResult var2 = (KnowledgeSearchResult)o;
            if (!var2.canEqual(this)) {
                return false;
            } else {
                String var3 = this.getData();
                String var4 = var2.getData();
                if (var3 == null) {
                    if (var4 != null) {
                        return false;
                    }
                } else if (!var3.equals(var4)) {
                    return false;
                }

                List var5 = this.getDocuments();
                List var6 = var2.getDocuments();
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
        return other instanceof KnowledgeSearchResult;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = 1;
        String var3 = this.getData();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        List var4 = this.getDocuments();
        var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
        return var2;
    }

    public String toString() {
        return "KnowledgeSearchResult(data=" + this.getData() + ", documents=" + this.getDocuments() + ")";
    }

    public KnowledgeSearchResult() {
    }
}
