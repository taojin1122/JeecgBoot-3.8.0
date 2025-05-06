//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.component.code;

import com.yomahub.liteflow.builder.LiteFlowNodeBuilder;
import com.yomahub.liteflow.enums.ScriptTypeEnum;
import com.yomahub.liteflow.flow.FlowBus;
import com.yomahub.liteflow.script.validator.ScriptValidator;
import java.util.HashMap;
import java.util.Map;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.jeecg.common.util.AssertUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.airag.common.utils.FreemarkerTemplateUtil;
import org.jeecg.modules.airag.flow.exception.FlowNodeHelperException;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowNode;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowNodeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeNode {
    private static final Logger logger = LoggerFactory.getLogger(CodeNode.class);

    public CodeNode() {
    }

    public static void a(FlowNode flowNode) {
        FlowNodeConfig flowNodeConfig = flowNode.getProperties();
        if ("code".equals(flowNode.getType()) && !oConvertUtils.isEmpty(flowNodeConfig)) {
            Map options = flowNodeConfig.getOptions();
            if (!oConvertUtils.isEmpty(options) && options.containsKey("code")) {
                String var3 = flowNode.getId();
                if (!FlowBus.containNode(var3)) {
                    String codeType = (String)options.get("codeType");
                    AssertUtils.assertNotEmpty("请选择代码类型", codeType);
                    CodeLanguageEnum codeLanguageEnum = CodeLanguageEnum.fromName(codeType.toUpperCase());
                    String var6 = (String)options.get("code");
                    AssertUtils.assertNotEmpty("请输入代码", var6);
                    HashMap var7 = new HashMap();
                    var7.put("inputParams", flowNodeConfig.getInputParams());
                    var7.put("outputParams", flowNodeConfig.getOutputParams());
                    var7.put("flowNodeObjId", var3);
                    var7.put("scriptContent", var6);
                    var6 = FreemarkerTemplateUtil.processTemplate(codeLanguageEnum.getTemplatePath(), var7);
                    logger.info("类型:{},脚本:{}", codeLanguageEnum.getScriptType(), var6);
                    if (!ScriptValidator.validate(var6, ScriptTypeEnum.getEnumByDisplayName(codeLanguageEnum.getScriptType()))) {
                        logger.error("脚本校验失败,请检查脚本语法");
                        throw new FlowNodeHelperException(flowNodeConfig.getNodeId(), flowNodeConfig.getText(),
                                "节点[" + flowNodeConfig.getText() + "]：脚本校验失败，请检查脚本语法");
                    }

                    LiteFlowNodeBuilder.createScriptNode().setId(var3).setScript(var6).setLanguage(codeLanguageEnum.getScriptType()).build();
                }

            }
        }
    }

    public static void main(String[] args) {
        ScriptEngineManager var1 = new ScriptEngineManager();
        ScriptEngine var2 = var1.getEngineByName("kotlin");
        if (var2 != null) {
            System.out.println("Kotlin ScriptEngine loaded: " + var2);
        } else {
            System.err.println("Kotlin ScriptEngine not found!");
        }

    }
}
