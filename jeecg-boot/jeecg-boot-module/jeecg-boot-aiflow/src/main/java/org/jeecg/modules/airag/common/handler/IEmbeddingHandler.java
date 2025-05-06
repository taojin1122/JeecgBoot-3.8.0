//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.common.handler;

import java.util.List;
import org.jeecg.modules.airag.common.vo.knowledge.KnowledgeSearchResult;

public interface IEmbeddingHandler {
    KnowledgeSearchResult embeddingSearch(List<String> var1, String var2, Integer var3, Double var4);
}
