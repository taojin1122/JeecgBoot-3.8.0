//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.common.utils;

import freemarker.core.TemplateClassResolver;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.StringWriter;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FreemarkerTemplateUtil {
    private static final Logger logger = LoggerFactory.getLogger(FreemarkerTemplateUtil.class);
    private static Configuration templateConfig;

    public FreemarkerTemplateUtil() {
    }

    static {
        templateConfig = new Configuration(Configuration.VERSION_2_3_28);
        templateConfig.setNumberFormat("0.#####################");
        templateConfig.setClassForTemplateLoading(FreemarkerTemplateUtil.class, "/");
        templateConfig.setNewBuiltinClassResolver(TemplateClassResolver.SAFER_RESOLVER);
    }

    /**
     * 使用指定编码处理FreeMarker模板
     * @param templateName 模板名称
     * @param encoding 编码方式
     * @param dataModel 数据模型
     * @return 处理后的字符串
     */
    private static String processTemplate(String templateName, String encoding, Map<String, Object> dataModel) {
        try {
            StringWriter var3 = new StringWriter();
            Template var4 = null;
            var4 = templateConfig.getTemplate(templateName, encoding);
            var4.process(dataModel, var3);
            return var3.toString();
        } catch (Exception var5) {
            logger.error(var5.getMessage(), var5);
            return var5.toString();
        }
    }

    /**
     * 使用UTF-8编码处理FreeMarker模板
     * @param templateName 模板名称
     * @param dataModel 数据模型
     * @return 处理后的字符串
     */
    public static String processTemplate(String templateName, Map<String, Object> dataModel) {
        return processTemplate(templateName, "utf-8", dataModel);
    }


}
