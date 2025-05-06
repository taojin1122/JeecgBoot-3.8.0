//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.airag.flow.entity.AiragFlow;
import org.jeecg.modules.airag.flow.vo.api.FlowDebugParams;
import org.jeecg.modules.airag.flow.vo.api.FlowDesignParams;
import org.jeecg.modules.airag.flow.vo.api.FlowRunParams;
import org.jeecg.modules.airag.flow.vo.api.SubFlowResult;

public interface IAiragFlowService extends IService<AiragFlow> {
    Object runFlow(FlowRunParams var1);

    Result<String> saveDesign(FlowDesignParams var1);

    Object debugFlow(FlowDebugParams var1);

    IPage<SubFlowResult> subflowPage(IPage<AiragFlow> var1, String var2, String var3);

    SubFlowResult querySubflowById(String var1);
}
