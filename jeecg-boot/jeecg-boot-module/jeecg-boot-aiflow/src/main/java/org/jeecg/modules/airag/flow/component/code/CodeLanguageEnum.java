//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.component.code;

/**
 * 这是一个定义代码类型的枚举类
 *
 */
public enum CodeLanguageEnum {
    JAVASCRIPT("javascript", "js", "org/jeecg/modules/airag/flow/component/code/code_node_javascript.ftl"),
    PYTHON("python", "python", "org/jeecg/modules/airag/flow/component/code/code_node_python.ftl"),
    GROOVY("groovy", "groovy", "org/jeecg/modules/airag/flow/component/code/code_node_groovy.ftl"),
    KOTLIN("kotlin", "kotlin", "org/jeecg/modules/airag/flow/component/code/code_node_kotlin.ftl"),
    AVIATOR("aviator", "aviator", "org/jeecg/modules/airag/flow/component/code/code_node_aviator.ftl");

    private String name;
    private String scriptType;
    private String templatePath;

    public static CodeLanguageEnum[] valuesCopy() {
        return values().clone();
    }

    public static CodeLanguageEnum fromName(String name) {
        return CodeLanguageEnum.valueOf(name);
    }

    private CodeLanguageEnum(String name, String scriptType, String templatePath) {
        this.name = name;
        this.scriptType = scriptType;
        this.templatePath = templatePath;
    }

    public String getName() {
        return this.name;
    }

    public String getScriptType() {
        return this.scriptType;
    }

    public String getTemplatePath() {
        return this.templatePath;
    }
}
