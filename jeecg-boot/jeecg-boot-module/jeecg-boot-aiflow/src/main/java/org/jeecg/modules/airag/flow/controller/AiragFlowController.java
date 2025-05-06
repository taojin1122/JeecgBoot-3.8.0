//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.FlowBus;
import com.yomahub.liteflow.springboot.LiteflowProperty;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.AssertUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.airag.flow.entity.AiragFlow;
import org.jeecg.modules.airag.flow.service.IAiragFlowAiGenService;
import org.jeecg.modules.airag.flow.service.IAiragFlowService;
import org.jeecg.modules.airag.flow.vo.api.AigcTestDataParams;
import org.jeecg.modules.airag.flow.vo.api.FlowDebugParams;
import org.jeecg.modules.airag.flow.vo.api.FlowDesignParams;
import org.jeecg.modules.airag.flow.vo.api.FlowRunParams;
import org.jeecg.modules.airag.flow.vo.api.SubFlowResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 根据代码内容，这是一个流程控制器类，处理流程相关的 REST 接口，
 *
 * 这个命名合适因为：
 * 1、遵循了 Controller 命名规范（以Controller结尾）
 * 2、处理 AiragFlow 相关的所有 REST 接口
 * 3、包含了：
     * 流程的 CRUD 操作
     * 流程设计保存
     * 流程调试和运行
     * 子流程管理
     * 导入导出功能
 * 4、与 RequestMapping 路径（/airag/flow）保持一致
 * 5、与注入的 Bean 名称（airagFlowController）保持一致
 */
@Tag(
    name = "AiRag流程"
)
@RestController("airagFlowController")
@RequestMapping({"/airag/flow"})
public class AiragFlowController extends JeecgController<AiragFlow, IAiragFlowService> {
    private static final Logger a = LoggerFactory.getLogger(AiragFlowController.class);
    @Autowired
    private IAiragFlowService airagFlowService;
    @Autowired
    private IAiragFlowAiGenService airagFlowAiGenService;
    @Autowired
    LiteflowProperty liteflowProperty;
    @Autowired
    FlowExecutor flowExecutor;

    public AiragFlowController() {
    }

    @GetMapping({"/list"})
    public Result<IPage<AiragFlow>> a(AiragFlow var1, @RequestParam(name = "pageNo",defaultValue = "1") Integer var2, @RequestParam(name = "pageSize",defaultValue = "10") Integer var3, HttpServletRequest var4) {
        QueryWrapper var5 = QueryGenerator.initQueryWrapper(var1, var4.getParameterMap());
        Page var6 = new Page((long)var2, (long)var3);
        IPage var7 = this.airagFlowService.page(var6, var5);
        return Result.OK(var7);
    }

    @PostMapping({"/add"})
    public Result<String> a(@RequestBody AiragFlow var1) {
        Map var2 = this.liteflowProperty.getRuleSourceExtDataMap();
        String var3 = "jeecg";
        if (null != var2) {
            var3 = oConvertUtils.getString((String)var2.get("applicationName"), "jeecg");
        }

        var1.setApplicationName(var3);
        var1.setStatus("enable");
        this.airagFlowService.save(var1);
        return Result.OK("添加成功！", var1.getId());
    }

    @RequestMapping(
        value = {"/edit"},
        method = {RequestMethod.PUT, RequestMethod.POST}
    )
    public Result<String> b(@RequestBody AiragFlow var1) {
        AiragFlow var2 = (AiragFlow)this.airagFlowService.getById(var1.getId());
        AssertUtils.assertNotEmpty("流程不存在", var2);
        var1.setChain((String)null);
        var1.setDesign((String)null);
        var1.setApplicationName((String)null);
        this.airagFlowService.updateById(var1);
        return Result.OK("编辑成功!");
    }

    @RequestMapping(
        value = {"/design/save"},
        method = {RequestMethod.PUT, RequestMethod.POST}
    )
    public Result<String> a(@RequestBody FlowDesignParams var1) {
        return this.airagFlowService.saveDesign(var1);
    }

    @DeleteMapping({"/delete"})
    public Result<String> a(@RequestParam(name = "id",required = true) String var1) {
        FlowBus.removeChain(var1);
        this.airagFlowService.removeById(var1);
        return Result.OK("删除成功!");
    }

    @DeleteMapping({"/deleteBatch"})
    public Result<String> b(@RequestParam(name = "ids",required = true) String var1) {
        List var2 = Arrays.asList(var1.split(","));
        FlowBus.removeChain((String[])var2.toArray(new String[0]));
        this.airagFlowService.removeByIds(var2);
        return Result.OK("批量删除成功!");
    }

    @GetMapping({"/queryById"})
    public Result<AiragFlow> queryById(@RequestParam(name = "id",required = true) String var1) {
        AiragFlow var2 = (AiragFlow)this.airagFlowService.getById(var1);
        return var2 == null ? Result.error("未找到对应数据") : Result.OK(var2);
    }

    @GetMapping({"/subflowList"})
    public Result<IPage<SubFlowResult>> subflowList(@RequestParam(name = "id",required = false) String var1, @RequestParam(name = "keywords",required = false) String var2, @RequestParam(name = "pageNo",defaultValue = "1") Integer var3, @RequestParam(name = "pageSize",defaultValue = "10") Integer var4) {
        Page var5 = new Page((long)var3, (long)var4);
        IPage var6 = this.airagFlowService.subflowPage(var5, var1, var2);
        return Result.OK(var6);
    }

    @GetMapping({"/querySubflowById"})
    public Result<SubFlowResult> querySubflowById(@RequestParam(name = "subflowId") String var1) {
        SubFlowResult var2 = this.airagFlowService.querySubflowById(var1);
        return Result.OK(var2);
    }

    @RequestMapping({"/exportXls"})
    public ModelAndView exportXls(HttpServletRequest var1, AiragFlow var2) {
        return super.exportXls(var1, var2, AiragFlow.class, "AiRag流程");
    }

    @RequestMapping(
        value = {"/importExcel"},
        method = {RequestMethod.POST}
    )
    public Result<?> importExcel(HttpServletRequest var1, HttpServletResponse var2) {
        return super.importExcel(var1, var2, AiragFlow.class);
    }

    @PostMapping({"/debug"})
    public Object debugFlow(@RequestBody FlowDebugParams flowDebugParams) {
        return this.airagFlowService.debugFlow(flowDebugParams);
    }

    @PostMapping({"/run"})
    public Object runFlow(@RequestBody FlowRunParams var1) {
        return this.airagFlowService.runFlow(var1);
    }

    @PostMapping({"/aigc/test-data"})
    public Result<?> genTestData(@RequestBody AigcTestDataParams var1) {
        return this.airagFlowAiGenService.genTestData(var1);
    }
}
