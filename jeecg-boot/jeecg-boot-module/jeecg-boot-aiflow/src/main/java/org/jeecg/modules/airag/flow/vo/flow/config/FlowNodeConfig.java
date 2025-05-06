//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.vo.flow.config;

import java.util.List;
import java.util.Map;

public class FlowNodeConfig {
    private String nodeId;
    private String nodeType;
    private List<NodeParam> inputParams;
    private List<NodeParam> outputParams;
    private Map<String, Object> options;
    private String text;

    public FlowNodeConfig() {
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public String getNodeType() {
        return this.nodeType;
    }

    public List<NodeParam> getInputParams() {
        return this.inputParams;
    }

    public List<NodeParam> getOutputParams() {
        return this.outputParams;
    }

    public Map<String, Object> getOptions() {
        return this.options;
    }

    public String getText() {
        return this.text;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public void setInputParams(List<NodeParam> inputParams) {
        this.inputParams = inputParams;
    }

    public void setOutputParams(List<NodeParam> outputParams) {
        this.outputParams = outputParams;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof FlowNodeConfig)) {
            return false;
        } else {
            FlowNodeConfig var2 = (FlowNodeConfig)o;
            if (!var2.canEqual(this)) {
                return false;
            } else {
                String var3 = this.getNodeId();
                String var4 = var2.getNodeId();
                if (var3 == null) {
                    if (var4 != null) {
                        return false;
                    }
                } else if (!var3.equals(var4)) {
                    return false;
                }

                String var5 = this.getNodeType();
                String var6 = var2.getNodeType();
                if (var5 == null) {
                    if (var6 != null) {
                        return false;
                    }
                } else if (!var5.equals(var6)) {
                    return false;
                }

                List var7 = this.getInputParams();
                List var8 = var2.getInputParams();
                if (var7 == null) {
                    if (var8 != null) {
                        return false;
                    }
                } else if (!var7.equals(var8)) {
                    return false;
                }

                label62: {
                    List var9 = this.getOutputParams();
                    List var10 = var2.getOutputParams();
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
                    Map var11 = this.getOptions();
                    Map var12 = var2.getOptions();
                    if (var11 == null) {
                        if (var12 == null) {
                            break label55;
                        }
                    } else if (var11.equals(var12)) {
                        break label55;
                    }

                    return false;
                }

                String var13 = this.getText();
                String var14 = var2.getText();
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
        return other instanceof FlowNodeConfig;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = 1;
        String var3 = this.getNodeId();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        String var4 = this.getNodeType();
        var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
        List var5 = this.getInputParams();
        var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
        List var6 = this.getOutputParams();
        var2 = var2 * 59 + (var6 == null ? 43 : var6.hashCode());
        Map var7 = this.getOptions();
        var2 = var2 * 59 + (var7 == null ? 43 : var7.hashCode());
        String var8 = this.getText();
        var2 = var2 * 59 + (var8 == null ? 43 : var8.hashCode());
        return var2;
    }

    public String toString() {
        return "FlowNodeConfig(nodeId=" + this.getNodeId() + ", nodeType=" + this.getNodeType() + ", inputParams=" + this.getInputParams() + ", outputParams=" + this.getOutputParams() + ", options=" + this.getOptions() + ", text=" + this.getText() + ")";
    }

    public FlowNodeConfig(String nodeId, String nodeType, List<NodeParam> inputParams, List<NodeParam> outputParams, Map<String, Object> options, String text) {
        this.nodeId = nodeId;
        this.nodeType = nodeType;
        this.inputParams = inputParams;
        this.outputParams = outputParams;
        this.options = options;
        this.text = text;
    }

    public static class NodeParam {
        private String nodeId;
        private String name;
        private String field;
        private String type;

        public NodeParam() {
        }

        public String getNodeId() {
            return this.nodeId;
        }

        public String getName() {
            return this.name;
        }

        public String getField() {
            return this.field;
        }

        public String getType() {
            return this.type;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setField(String field) {
            this.field = field;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
