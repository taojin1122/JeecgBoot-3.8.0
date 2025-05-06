//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.vo.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.airag.flow.entity.AiragFlow;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowNode;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowNodeConfig;
import org.springframework.util.CollectionUtils;

public class SubFlowResult {
    private String id;
    private String name;
    private String descr;
    private String status;
    private List<FlowNodeConfig.NodeParam> inputParams;
    private List<FlowNodeConfig.NodeParam> outputParams;

    public SubFlowResult(AiragFlow flow) {
        this.id = flow.getId();
        this.name = flow.getName();
        this.descr = flow.getDescr();
        this.status = flow.getStatus();
        this.inputParams = new ArrayList();
        this.outputParams = new ArrayList();
        this.initParams(flow);
    }

    private void initParams(AiragFlow flow) {
        JSONObject var2;
        try {
            var2 = JSONObject.parseObject(flow.getDesign());
        } catch (Exception var11) {
            return;
        }

        if (null != var2 && var2.containsKey("nodes")) {
            JSONArray var3 = var2.getJSONArray("nodes");
            if (!CollectionUtils.isEmpty(var3)) {
                HashMap var4 = new HashMap();
                Iterator var5 = var3.iterator();

                while(var5.hasNext()) {
                    Object var6 = var5.next();
                    FlowNode var7 = (FlowNode)((JSONObject)var6).toJavaObject(FlowNode.class);
                    FlowNodeConfig var8 = var7.getProperties();
                    if ("start".equals(var7.getType())) {
                        this.inputParams = var8.getInputParams();
                    } else if ("end".equals(var7.getType())) {
                        boolean var9 = false;
                        if (oConvertUtils.isObjectNotEmpty(var8.getOptions())) {
                            var9 = (Boolean)var8.getOptions().get("outputText");
                        }

                        if (var9) {
                            FlowNodeConfig.NodeParam var10 = new FlowNodeConfig.NodeParam();
                            var10.setName("outputText");
                            var10.setField("outputText");
                            var4.put(var10.getField(), var10);
                        } else {
                            var8.getOutputParams().forEach((nodeParam) -> {
                                var4.put(nodeParam.getName(), nodeParam);
                            });
                        }
                    }
                }

                this.outputParams = new ArrayList(var4.values());
            }
        }
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescr() {
        return this.descr;
    }

    public String getStatus() {
        return this.status;
    }

    public List<FlowNodeConfig.NodeParam> getInputParams() {
        return this.inputParams;
    }

    public List<FlowNodeConfig.NodeParam> getOutputParams() {
        return this.outputParams;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setInputParams(List<FlowNodeConfig.NodeParam> inputParams) {
        this.inputParams = inputParams;
    }

    public void setOutputParams(List<FlowNodeConfig.NodeParam> outputParams) {
        this.outputParams = outputParams;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof SubFlowResult)) {
            return false;
        } else {
            SubFlowResult var2 = (SubFlowResult)o;
            if (!var2.canEqual(this)) {
                return false;
            } else {
                String var3 = this.getId();
                String var4 = var2.getId();
                if (var3 == null) {
                    if (var4 != null) {
                        return false;
                    }
                } else if (!var3.equals(var4)) {
                    return false;
                }

                String var5 = this.getName();
                String var6 = var2.getName();
                if (var5 == null) {
                    if (var6 != null) {
                        return false;
                    }
                } else if (!var5.equals(var6)) {
                    return false;
                }

                String var7 = this.getDescr();
                String var8 = var2.getDescr();
                if (var7 == null) {
                    if (var8 != null) {
                        return false;
                    }
                } else if (!var7.equals(var8)) {
                    return false;
                }

                label62: {
                    String var9 = this.getStatus();
                    String var10 = var2.getStatus();
                    if (var9 == null) {
                        if (var10 == null) {
                            break label62;
                        }
                    } else if (var9.equals(var10)) {
                        break label62;
                    }

                    return false;
                }

                label55: {
                    List var11 = this.getInputParams();
                    List var12 = var2.getInputParams();
                    if (var11 == null) {
                        if (var12 == null) {
                            break label55;
                        }
                    } else if (var11.equals(var12)) {
                        break label55;
                    }

                    return false;
                }

                List var13 = this.getOutputParams();
                List var14 = var2.getOutputParams();
                if (var13 == null) {
                    if (var14 != null) {
                        return false;
                    }
                } else if (!var13.equals(var14)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof SubFlowResult;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = 1;
        String var3 = this.getId();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        String var4 = this.getName();
        var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
        String var5 = this.getDescr();
        var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
        String var6 = this.getStatus();
        var2 = var2 * 59 + (var6 == null ? 43 : var6.hashCode());
        List var7 = this.getInputParams();
        var2 = var2 * 59 + (var7 == null ? 43 : var7.hashCode());
        List var8 = this.getOutputParams();
        var2 = var2 * 59 + (var8 == null ? 43 : var8.hashCode());
        return var2;
    }

    public String toString() {
        return "SubFlowResult(id=" + this.getId() + ", name=" + this.getName() + ", descr=" + this.getDescr() + ", status=" + this.getStatus() + ", inputParams=" + this.getInputParams() + ", outputParams=" + this.getOutputParams() + ")";
    }
}
