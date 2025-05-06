//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.vo.api;

import java.util.List;

public class AigcTestDataParams {
    private List<FieldItem> fields;

    public AigcTestDataParams() {
    }

    public List<FieldItem> getFields() {
        return this.fields;
    }

    public void setFields(List<FieldItem> fields) {
        this.fields = fields;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof AigcTestDataParams)) {
            return false;
        } else {
            AigcTestDataParams var2 = (AigcTestDataParams)o;
            if (!var2.canEqual(this)) {
                return false;
            } else {
                List var3 = this.getFields();
                List var4 = var2.getFields();
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
        return other instanceof AigcTestDataParams;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = 1;
        List var3 = this.getFields();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        return var2;
    }

    public String toString() {
        return "AigcTestDataParams(fields=" + this.getFields() + ")";
    }

    public static class FieldItem {
        private String field;
        private String name;
        private String type;
        private boolean required;

        public FieldItem() {
        }

        public String getField() {
            return this.field;
        }

        public String getName() {
            return this.name;
        }

        public String getType() {
            return this.type;
        }

        public boolean isRequired() {
            return this.required;
        }

        public void setField(String field) {
            this.field = field;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setRequired(boolean required) {
            this.required = required;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof FieldItem)) {
                return false;
            } else {
                FieldItem var2 = (FieldItem)o;
                if (!var2.canEqual(this)) {
                    return false;
                } else if (this.isRequired() != var2.isRequired()) {
                    return false;
                } else {
                    label49: {
                        String var3 = this.getField();
                        String var4 = var2.getField();
                        if (var3 == null) {
                            if (var4 == null) {
                                break label49;
                            }
                        } else if (var3.equals(var4)) {
                            break label49;
                        }

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

                    String var7 = this.getType();
                    String var8 = var2.getType();
                    if (var7 == null) {
                        if (var8 != null) {
                            return false;
                        }
                    } else if (!var7.equals(var8)) {
                        return false;
                    }

                    return true;
                }
            }
        }

        protected boolean canEqual(Object other) {
            return other instanceof FieldItem;
        }

        public int hashCode() {
            boolean var1 = true;
            int var2 = 1;
            var2 = var2 * 59 + (this.isRequired() ? 79 : 97);
            String var3 = this.getField();
            var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
            String var4 = this.getName();
            var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
            String var5 = this.getType();
            var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
            return var2;
        }

        public String toString() {
            return "AigcTestDataParams.FieldItem(field=" + this.getField() + ", name=" + this.getName() + ", type=" + this.getType() + ", required=" + this.isRequired() + ")";
        }
    }
}
