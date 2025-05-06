//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

@TableName("airag_flow")
@Schema(
    description = "AiRag流程"
)
public class AiragFlow implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(
        type = IdType.ASSIGN_ID
    )
    @Schema(
        description = "主键"
    )
    private String id;
    @Schema(
        description = "创建人"
    )
    @Dict(
        dictTable = "sys_user",
        dicCode = "username",
        dicText = "realname"
    )
    private String createBy;
    @JsonFormat(
        timezone = "GMT+8",
        pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @DateTimeFormat(
        pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @Schema(
        description = "创建日期"
    )
    private Date createTime;
    @Schema(
        description = "更新人"
    )
    private String updateBy;
    @JsonFormat(
        timezone = "GMT+8",
        pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @DateTimeFormat(
        pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @Schema(
        description = "更新日期"
    )
    private Date updateTime;
    @Schema(
        description = "所属部门"
    )
    private String sysOrgCode;
    @Excel(
        name = "租户id",
        width = 15.0
    )
    @Schema(
        description = "租户id"
    )
    private String tenantId;
    @Excel(
        name = "应用名称",
        width = 15.0
    )
    @Schema(
        description = "应用名称"
    )
    private String applicationName;
    @Excel(
        name = "名称",
        width = 15.0
    )
    @Schema(
        description = "名称"
    )
    private String name;
    @Excel(
        name = "描述",
        width = 15.0
    )
    @Schema(
        description = "描述"
    )
    private String descr;
    @Excel(
        name = "应用图标",
        width = 15.0
    )
    @Schema(
        description = "应用图标"
    )
    private String icon;
    @Excel(
        name = "编排规则",
        width = 15.0
    )
    @Schema(
        description = "编排规则"
    )
    private String chain;
    @Excel(
        name = "编排设计",
        width = 15.0
    )
    @Schema(
        description = "编排设计"
    )
    private String design;
    @Excel(
        name = "状态",
        width = 15.0
    )
    @Schema(
        description = "状态"
    )
    private String status;
    @Excel(
        name = "元数据",
        width = 15.0
    )
    @Schema(
        description = "元数据"
    )
    private String metadata;

    public AiragFlow() {
    }

    public String getId() {
        return this.id;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public String getSysOrgCode() {
        return this.sysOrgCode;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public String getApplicationName() {
        return this.applicationName;
    }

    public String getName() {
        return this.name;
    }

    public String getDescr() {
        return this.descr;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getChain() {
        return this.chain;
    }

    public String getDesign() {
        return this.design;
    }

    public String getStatus() {
        return this.status;
    }

    public String getMetadata() {
        return this.metadata;
    }

    public AiragFlow setId(String id) {
        this.id = id;
        return this;
    }

    public AiragFlow setCreateBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    @JsonFormat(
        timezone = "GMT+8",
        pattern = "yyyy-MM-dd HH:mm:ss"
    )
    public AiragFlow setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public AiragFlow setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    @JsonFormat(
        timezone = "GMT+8",
        pattern = "yyyy-MM-dd HH:mm:ss"
    )
    public AiragFlow setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public AiragFlow setSysOrgCode(String sysOrgCode) {
        this.sysOrgCode = sysOrgCode;
        return this;
    }

    public AiragFlow setTenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public AiragFlow setApplicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public AiragFlow setName(String name) {
        this.name = name;
        return this;
    }

    public AiragFlow setDescr(String descr) {
        this.descr = descr;
        return this;
    }

    public AiragFlow setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public AiragFlow setChain(String chain) {
        this.chain = chain;
        return this;
    }

    public AiragFlow setDesign(String design) {
        this.design = design;
        return this;
    }

    public AiragFlow setStatus(String status) {
        this.status = status;
        return this;
    }

    public AiragFlow setMetadata(String metadata) {
        this.metadata = metadata;
        return this;
    }

    public String toString() {
        return "AiragFlow(id=" + this.getId() + ", createBy=" + this.getCreateBy() + ", createTime=" + this.getCreateTime() + ", updateBy=" + this.getUpdateBy() + ", updateTime=" + this.getUpdateTime() + ", sysOrgCode=" + this.getSysOrgCode() + ", tenantId=" + this.getTenantId() + ", applicationName=" + this.getApplicationName() + ", name=" + this.getName() + ", descr=" + this.getDescr() + ", icon=" + this.getIcon() + ", chain=" + this.getChain() + ", design=" + this.getDesign() + ", status=" + this.getStatus() + ", metadata=" + this.getMetadata() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof AiragFlow)) {
            return false;
        } else {
            AiragFlow var2 = (AiragFlow)o;
            if (!var2.canEqual(this)) {
                return false;
            } else {
                label191: {
                    String var3 = this.getId();
                    String var4 = var2.getId();
                    if (var3 == null) {
                        if (var4 == null) {
                            break label191;
                        }
                    } else if (var3.equals(var4)) {
                        break label191;
                    }

                    return false;
                }

                String var5 = this.getCreateBy();
                String var6 = var2.getCreateBy();
                if (var5 == null) {
                    if (var6 != null) {
                        return false;
                    }
                } else if (!var5.equals(var6)) {
                    return false;
                }

                Date var7 = this.getCreateTime();
                Date var8 = var2.getCreateTime();
                if (var7 == null) {
                    if (var8 != null) {
                        return false;
                    }
                } else if (!var7.equals(var8)) {
                    return false;
                }

                label170: {
                    String var9 = this.getUpdateBy();
                    String var10 = var2.getUpdateBy();
                    if (var9 == null) {
                        if (var10 == null) {
                            break label170;
                        }
                    } else if (var9.equals(var10)) {
                        break label170;
                    }

                    return false;
                }

                label163: {
                    Date var11 = this.getUpdateTime();
                    Date var12 = var2.getUpdateTime();
                    if (var11 == null) {
                        if (var12 == null) {
                            break label163;
                        }
                    } else if (var11.equals(var12)) {
                        break label163;
                    }

                    return false;
                }

                String var13 = this.getSysOrgCode();
                String var14 = var2.getSysOrgCode();
                if (var13 == null) {
                    if (var14 != null) {
                        return false;
                    }
                } else if (!var13.equals(var14)) {
                    return false;
                }

                String var15 = this.getTenantId();
                String var16 = var2.getTenantId();
                if (var15 == null) {
                    if (var16 != null) {
                        return false;
                    }
                } else if (!var15.equals(var16)) {
                    return false;
                }

                label142: {
                    String var17 = this.getApplicationName();
                    String var18 = var2.getApplicationName();
                    if (var17 == null) {
                        if (var18 == null) {
                            break label142;
                        }
                    } else if (var17.equals(var18)) {
                        break label142;
                    }

                    return false;
                }

                label135: {
                    String var19 = this.getName();
                    String var20 = var2.getName();
                    if (var19 == null) {
                        if (var20 == null) {
                            break label135;
                        }
                    } else if (var19.equals(var20)) {
                        break label135;
                    }

                    return false;
                }

                String var21 = this.getDescr();
                String var22 = var2.getDescr();
                if (var21 == null) {
                    if (var22 != null) {
                        return false;
                    }
                } else if (!var21.equals(var22)) {
                    return false;
                }

                label121: {
                    String var23 = this.getIcon();
                    String var24 = var2.getIcon();
                    if (var23 == null) {
                        if (var24 == null) {
                            break label121;
                        }
                    } else if (var23.equals(var24)) {
                        break label121;
                    }

                    return false;
                }

                String var25 = this.getChain();
                String var26 = var2.getChain();
                if (var25 == null) {
                    if (var26 != null) {
                        return false;
                    }
                } else if (!var25.equals(var26)) {
                    return false;
                }

                label107: {
                    String var27 = this.getDesign();
                    String var28 = var2.getDesign();
                    if (var27 == null) {
                        if (var28 == null) {
                            break label107;
                        }
                    } else if (var27.equals(var28)) {
                        break label107;
                    }

                    return false;
                }

                String var29 = this.getStatus();
                String var30 = var2.getStatus();
                if (var29 == null) {
                    if (var30 != null) {
                        return false;
                    }
                } else if (!var29.equals(var30)) {
                    return false;
                }

                String var31 = this.getMetadata();
                String var32 = var2.getMetadata();
                if (var31 == null) {
                    if (var32 != null) {
                        return false;
                    }
                } else if (!var31.equals(var32)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof AiragFlow;
    }

    public int hashCode() {
        boolean var1 = true;
        int var2 = 1;
        String var3 = this.getId();
        var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
        String var4 = this.getCreateBy();
        var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
        Date var5 = this.getCreateTime();
        var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
        String var6 = this.getUpdateBy();
        var2 = var2 * 59 + (var6 == null ? 43 : var6.hashCode());
        Date var7 = this.getUpdateTime();
        var2 = var2 * 59 + (var7 == null ? 43 : var7.hashCode());
        String var8 = this.getSysOrgCode();
        var2 = var2 * 59 + (var8 == null ? 43 : var8.hashCode());
        String var9 = this.getTenantId();
        var2 = var2 * 59 + (var9 == null ? 43 : var9.hashCode());
        String var10 = this.getApplicationName();
        var2 = var2 * 59 + (var10 == null ? 43 : var10.hashCode());
        String var11 = this.getName();
        var2 = var2 * 59 + (var11 == null ? 43 : var11.hashCode());
        String var12 = this.getDescr();
        var2 = var2 * 59 + (var12 == null ? 43 : var12.hashCode());
        String var13 = this.getIcon();
        var2 = var2 * 59 + (var13 == null ? 43 : var13.hashCode());
        String var14 = this.getChain();
        var2 = var2 * 59 + (var14 == null ? 43 : var14.hashCode());
        String var15 = this.getDesign();
        var2 = var2 * 59 + (var15 == null ? 43 : var15.hashCode());
        String var16 = this.getStatus();
        var2 = var2 * 59 + (var16 == null ? 43 : var16.hashCode());
        String var17 = this.getMetadata();
        var2 = var2 * 59 + (var17 == null ? 43 : var17.hashCode());
        return var2;
    }
}
