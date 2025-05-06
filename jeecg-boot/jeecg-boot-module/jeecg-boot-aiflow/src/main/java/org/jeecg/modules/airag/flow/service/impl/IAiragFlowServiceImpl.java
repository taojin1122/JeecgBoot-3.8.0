//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.FlowBus;
import com.yomahub.liteflow.flow.LiteflowResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.config.TenantContext;
import org.jeecg.common.exception.JeecgBootBizTipException;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.AssertUtils;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.UUIDGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.airag.common.utils.AiragLocalCache;
import org.jeecg.modules.airag.flow.context.FlowContext;
import org.jeecg.modules.airag.flow.component.code.CodeNode;
import org.jeecg.modules.airag.flow.entity.AiragFlow;
import org.jeecg.modules.airag.flow.mapper.AiragFlowMapper;
import org.jeecg.modules.airag.flow.service.IAiragFlowService;
import org.jeecg.modules.airag.flow.vo.api.FlowDebugParams;
import org.jeecg.modules.airag.flow.vo.api.FlowDesignParams;
import org.jeecg.modules.airag.flow.vo.api.FlowRunParams;
import org.jeecg.modules.airag.flow.vo.api.SubFlowResult;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowNode;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowNodeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service("airagFlowService")
public class IAiragFlowServiceImpl extends ServiceImpl<AiragFlowMapper, AiragFlow> implements IAiragFlowService {
    private static final Logger logger = LoggerFactory.getLogger(IAiragFlowServiceImpl.class);
    @Autowired
    FlowExecutor flowExecutor;

    @Autowired
    private ISysBaseAPI sysBaseAPI;

    @Override
    public Result<String> saveDesign(FlowDesignParams flowDesignParams) {
        // 验证流程存在性和必要参数
        AiragFlow flow = (AiragFlow)this.getById(flowDesignParams.getId());
        AssertUtils.assertNotEmpty("流程不存在", flow);
        AssertUtils.assertNotEmpty("流程规则不能为空", flowDesignParams.getChain());
        AssertUtils.assertNotEmpty("设计数据不能为空", flowDesignParams.getDesign());

        // 处理元数据
        String existingDesign = flow.getDesign();
        String existingMetadata = flow.getMetadata();
        JSONObject metadata = new JSONObject();
        if (oConvertUtils.isNotEmpty(existingMetadata)) {
            metadata = JSONObject.parseObject(existingMetadata);
        }

        // 处理现有设计
        if (StringUtils.isNotEmpty(existingDesign)) {
            JSONObject var6 = JSONObject.parseObject(existingDesign);
            JSONArray nodes = var6.getJSONArray("nodes");
            cleanupCodeNodes(nodes);
            updateFlowMetadata(nodes, metadata);
        } else {
            metadata.put("inputs", (Object)null);
            metadata.put("outputs", (Object)null);
        }

        // 移除现有链
        if (FlowBus.containChain(flow.getId())) {
            FlowBus.removeChain(flow.getId());
        }

        // 更新流程
        flow.setName(flowDesignParams.getName());
        flow.setChain(flowDesignParams.getChain());
        flow.setDesign(flowDesignParams.getDesign());
        flow.setMetadata(metadata.toJSONString());
        this.updateById(flow);
        return Result.OK("保存成功");
    }

    /**
     * 实际功能是更新流程元数据，代码中已有注释
     */
    private static void updateFlowMetadata(JSONArray nodes, JSONObject metadata) {
        HashMap var2 = new HashMap();
        Iterator var3 = nodes.iterator();

        while(var3.hasNext()) {
            Object var4 = var3.next();
            FlowNode var5 = (FlowNode)((JSONObject)var4).toJavaObject(FlowNode.class);
            FlowNodeConfig var6 = var5.getProperties();
            if ("start".equals(var5.getType())) {
                List var7 = var6.getInputParams();
                metadata.put("inputs", var7);
            } else if ("end".equals(var5.getType())) {
                boolean var9 = false;
                if (oConvertUtils.isObjectNotEmpty(var6.getOptions())) {
                    var9 = (Boolean)var6.getOptions().get("outputText");
                }

                if (var9) {
                    FlowNodeConfig.NodeParam var8 = new FlowNodeConfig.NodeParam();
                    var8.setField("outputText");
                    var8.setType("string");
                    var2.put(var8.getField(), var8);
                } else {
                    var6.getOutputParams().forEach((var1x) -> {
                        var2.put(var1x.getField(), var1x);
                    });
                }
            }
        }

        metadata.put("outputs", var2.values());
    }

    public Object runFlow(FlowRunParams flowRunParams) {
        AiragFlow flow = (AiragFlow)this.getById(flowRunParams.getFlowId());
        FlowContext flowContext = this.buildFlowContext(flow, flowRunParams);
        return this.executeFlowWithParams(flowRunParams, flowContext);
    }


    public Object debugFlow(FlowDebugParams flowDebugParams) {
        AiragFlow airagFlow = flowDebugParams.getFlow();
        String flowId = UUIDGenerator.generate();
        airagFlow.setId(flowId);
        String applicationName = "jeecg";
        airagFlow.setApplicationName(applicationName);
        airagFlow.setStatus("enable");
        flowDebugParams.setFlowId(flowId);
        flowDebugParams.setEventCallback((eventData) -> {
            String callBackflowId = eventData.getFlowId();
            if (FlowBus.containChain(callBackflowId)) {
                FlowBus.removeChain(callBackflowId);
            }

            if (oConvertUtils.isNotEmpty(airagFlow.getDesign())) {
                JSONObject design = JSONObject.parseObject(airagFlow.getDesign());
                JSONArray nodes = design.getJSONArray("nodes");
                cleanupCodeNodes(nodes);
            }

        });
        FlowContext flowContext = this.buildFlowContext((AiragFlow)airagFlow, (FlowRunParams)flowDebugParams);
        return this.executeFlowWithParams((FlowRunParams)flowDebugParams, (FlowContext)flowContext);
    }

    /**
     * 构建流程上下文 FlowContext
     * @param flow
     * @param params
     * @return
     */
    private FlowContext buildFlowContext(AiragFlow flow, FlowRunParams params) {
        AssertUtils.assertNotEmpty("流程不存在", flow);
        AssertUtils.assertNotEmpty("请先设计流程", flow.getChain());
        String designContent = flow.getDesign();
        HashMap flowNodes;
        if (StringUtils.isNotEmpty(designContent)) {
            JSONObject var5 = JSONObject.parseObject(designContent);
            JSONArray var6 = var5.getJSONArray("nodes");
            flowNodes = new HashMap();
            var6.forEach((var1x) -> {
                FlowNode var2 = (FlowNode)((JSONObject)var1x).toJavaObject(FlowNode.class);
                if (!oConvertUtils.isEmpty(var2)) {
                    CodeNode.a(var2);
                    flowNodes.put(var2.getId(), var2);
                }
            });
        } else {
            flowNodes = null;
        }

        FlowContext flowContext = new FlowContext();
        flowContext.setFlowNodes(flowNodes);
        flowContext.setRequestDatas(params.getInputParams());
        if (oConvertUtils.isNotEmpty(params.getRequestId())) {
            flowContext.setRequestId(params.getRequestId());
        } else {
            flowContext.setRequestId(UUIDGenerator.generate());
        }

        if (oConvertUtils.isNotEmpty(params.getConversationId())) {
            flowContext.setConversationId(params.getConversationId());
        }

        if (oConvertUtils.isNotEmpty(params.getTopicId())) {
            flowContext.setTopicId(params.getTopicId());
        }

        if (oConvertUtils.isObjectNotEmpty(params.getEventCallback())) {
            flowContext.setEventCallback(params.getEventCallback());
        }

        if (oConvertUtils.isObjectNotEmpty(params.getHttpRequest())) {
            flowContext.setHttpRequest(params.getHttpRequest());
        } else {
            try {
                HttpServletRequest var10 = SpringContextUtils.getHttpServletRequest();
                flowContext.setHttpRequest(var10);
            } catch (Exception var8) {
            }
        }

        LoginUser var11 = (LoginUser)SecurityUtils.getSubject().getPrincipal();
        if (null != var11) {
            flowContext.setCacheUser(this.sysBaseAPI.getCacheUser(var11.getUsername()));
        }

        String var7 = TenantContext.getTenant();
        if (oConvertUtils.isNotEmpty(var7)) {
            flowContext.setTenantId(var7);
        }

        logger.info("流程编排规则:{}", flow.getChain());
        if (!FlowBus.containChain(flow.getId())) {
            FlowBus.reloadChain(flow.getId(), flow.getChain());
        }

        return flowContext;
    }

    /**
     * 实际功能是清理代码节点，代码中已有注释
     */
    private static void cleanupCodeNodes(JSONArray nodes) {
        nodes.forEach((var0x) -> {
            FlowNode var1 = (FlowNode)((JSONObject)var0x).toJavaObject(FlowNode.class);
            if (!oConvertUtils.isEmpty(var1) && "code".equals(var1.getType())) {
                if (FlowBus.containNode(var1.getId())) {
                    FlowBus.removeNode(var1.getId());
                }
            }
        });
    }

    /**
     * 执行流程，支持阻塞或 SSE 响应模式
     * @param params
     * @param flowContext
     * @return
     */
    private Object executeFlowWithParams(FlowRunParams params, FlowContext flowContext) {
        if ("blocking".equalsIgnoreCase(params.getResponseMode())) {
            LiteflowResponse liteflowResponse = this.flowExecutor.execute2Resp(params.getFlowId(), (Object)null, new Object[]{flowContext});
            if (liteflowResponse.isSuccess()) {
                FlowContext flowContext1 = (FlowContext)liteflowResponse.getContextBean(FlowContext.class);
                return Result.OK(flowContext1.getResult());
            } else {
                if (null != liteflowResponse.getCause()) {
                    logger.error(liteflowResponse.getCause().getMessage(), liteflowResponse.getCause());
                }

                return Result.error(liteflowResponse.getMessage());
            }
        } else {
            SseEmitter sseEmitter;
            if (oConvertUtils.isNotEmpty(params.getRequestId())) {
                sseEmitter = (SseEmitter)AiragLocalCache.get("CHAT:TYPE:SSE", params.getRequestId());
            } else {
                sseEmitter = new SseEmitter(0L);
            }

            flowContext.setSseEmitter(sseEmitter);
            this.flowExecutor.execute2Future(params.getFlowId(), (Object)null, new Object[]{flowContext});
            return sseEmitter;
        }
    }

    public IPage<SubFlowResult> subflowPage(IPage<AiragFlow> page, String id, String keywords) {
        LambdaQueryWrapper<AiragFlow> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.orderByDesc(AiragFlow::getCreateTime);
        lambdaQueryWrapper.select(Arrays.asList(AiragFlow::getId, AiragFlow::getName,
                AiragFlow::getDescr, AiragFlow::getStatus, AiragFlow::getDesign));
        if (oConvertUtils.isNotEmpty(id)) {
            lambdaQueryWrapper.ne(AiragFlow::getId, id);
        }

        if (oConvertUtils.isNotEmpty(keywords)) {
            lambdaQueryWrapper.like(AiragFlow::getName, "%" + keywords + "%");
        }

        IPage var5 = ((AiragFlowMapper)this.baseMapper).selectPage(page, lambdaQueryWrapper);
        List var6 = var5.getRecords();
        Page var7 = new Page();
        var7.setPages(var5.getPages());
        var7.setTotal(var5.getTotal());
        var7.setCurrent(page.getCurrent());
        var7.setSize(page.getSize());
        var7.setRecords(new ArrayList());
        if (CollectionUtils.isEmpty(var6)) {
            return var7;
        } else {
            Iterator var8 = var6.iterator();

            while(var8.hasNext()) {
                AiragFlow var9 = (AiragFlow)var8.next();
                SubFlowResult var10 = new SubFlowResult(var9);
                if (var10.getInputParams() != null) {
                    var7.getRecords().add(var10);
                }
            }

            return var7;
        }
    }

    public SubFlowResult querySubflowById(String subflowId) {
        if (oConvertUtils.isEmpty(subflowId)) {
            throw new JeecgBootBizTipException("subflowId 不能为空");
        } else {
            AiragFlow var2 = (AiragFlow)this.getById(subflowId);
            if (var2 == null) {
                throw new JeecgBootBizTipException("流程不存在");
            } else {
                SubFlowResult var3 = new SubFlowResult(var2);
                if (var3.getInputParams() == null) {
                    throw new JeecgBootBizTipException("流程配置异常");
                } else {
                    return var3;
                }
            }
        }
    }
}
