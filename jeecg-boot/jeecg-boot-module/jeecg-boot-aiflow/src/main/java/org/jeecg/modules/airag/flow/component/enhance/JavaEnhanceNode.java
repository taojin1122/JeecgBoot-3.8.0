//
// Source code recreated from JavaEnhanceNode .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.component.enhance;

import com.alibaba.fastjson.JSONObject;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jeecg.common.util.MyClassLoader;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.airag.flow.context.FlowContext;
import org.jeecg.modules.airag.flow.component.base.FlowNodeHelper;
import org.jeecg.modules.airag.flow.exception.FlowNodeHelperException;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowNodeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *  根据代码内容，这是一个处理 Java 增强功能的节点组件
 *在流程引擎中，用于执行自定义 Java 代码逻辑的节点，通过动态加载类或 Bean 的方式来“增强”流程功能
 *
 *  这个命名合适因为：
 *      1、与组件注解名 enhanceJava 语义一致
 *      2、表明这是一个流程节点（Node）
 *      3、处理 Java 增强（Enhance）功能
 *      4、主要功能：
 *          处理 Java 类型的增强逻辑
 *          动态加载和执行增强类
 *          处理节点的输入输出参数
 *      这个命名更简洁且能清晰表达类的用途和类型
 */
@Component("enhanceJava")
public class JavaEnhanceNode extends FlowNodeHelper {
    private static final Logger logger = LoggerFactory.getLogger(JavaEnhanceNode.class);

    public JavaEnhanceNode() {
    }

    @LiteflowMethod(
            value = LiteFlowMethodEnum.PROCESS,
            nodeType = NodeTypeEnum.COMMON
    )
    public void processJavaEnhanceNode(NodeComponent nodeComponent) {
        logger.info("节点开始-enhanceJava");
        FlowContext flowContext = getFlowContext(nodeComponent);
        FlowNodeConfig var3 = getNodeConfig(nodeComponent);
        if (oConvertUtils.isObjectEmpty(var3.getOptions())) {
            throw new FlowNodeHelperException(var3.getNodeId(), var3.getText(), "节点配置错误");
        } else {
            JSONObject var4 = JSONObject.parseObject(JSONObject.toJSONString(var3.getOptions()));
            JSONObject var5 = var4.getJSONObject("enhance");
            JavaEnhanceProcessor var6 = this.getEnhanceProcessor(var3, var5);
            HashMap var7 = new HashMap();
            List var8 = var3.getInputParams();
            Iterator var9 = var8.iterator();

            while(var9.hasNext()) {
                FlowNodeConfig.NodeParam var10 = (FlowNodeConfig.NodeParam)var9.next();
                Object var11 = flowContext.getNodeFieldValue(var10.getNodeId(), var10.getField());
                var7.put(var10.getName(), var11);
            }

            Map<String, Object> var13;
            try {
                var13 = var6.process(var7);
            } catch (Throwable var12) {
                throw new FlowNodeHelperException(var3.getNodeId(), var3.getText(), "运行java增强失败:" + var12.getMessage());
            }

            var13.forEach((var2x, var3x) -> {
                flowContext.setNodeFieldValue(var3.getNodeId(), var2x, var3x);
            });
        }
    }

    /**
     * 获取流程上下文与配置： 解析节点配置，提取增强设置。
     *
     * 如果配置为 class，使用 MyClassLoader 加载类。
     * 如果配置为 spring，从 Spring 容器中获取 Bean。
     *
     * @param config
     * @param enhanceConfig
     * @return
     */
    private JavaEnhanceProcessor getEnhanceProcessor(FlowNodeConfig config, JSONObject enhanceConfig) {
        Object var3 = null;
        if (enhanceConfig != null) {
            String var4 = enhanceConfig.getString("type");
            String var5 = enhanceConfig.getString("path");
            if (oConvertUtils.isNotEmpty(var5)) {
                if ("class".equals(var4)) {
                    try {
                        var3 = MyClassLoader.getClassByScn(var5).newInstance();
                    } catch (IllegalAccessException | InstantiationException var7) {
                        logger.error(var7.getMessage(), var7);
                        throw new FlowNodeHelperException(config.getNodeId(), config.getText(), "获取增强对象失败:" + var7.getMessage());
                    }
                } else if ("spring".equals(var4)) {
                    var3 = SpringContextUtils.getBean(var5);
                }
            }
        }

        if (var3 instanceof JavaEnhanceProcessor) {
            return (JavaEnhanceProcessor)var3;
        } else {
            throw new FlowNodeHelperException(config.getNodeId(), config.getText(), "获取增强对象失败:类型不匹配");
        }
    }
}
