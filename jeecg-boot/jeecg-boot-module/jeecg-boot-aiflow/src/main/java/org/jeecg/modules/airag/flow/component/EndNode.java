//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.component;

import com.alibaba.fastjson.JSONObject;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.airag.flow.component.base.FlowNodeHelper;
import org.jeecg.modules.airag.flow.context.FlowContext;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowNodeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 这个类是一个结束节点组件，继承自 ClassifierNode
 */
@Component("end")
public class EndNode extends FlowNodeHelper {
    private static final Logger logger = LoggerFactory.getLogger(EndNode.class);
    /**
     * 表明这是用于模板替换的正则表达式
     */
    public static final Pattern TEMPLATE_PATTERN = Pattern.compile("\\{\\{(.*?)}}");

    public EndNode() {
    }

    /**
     *  // 原 IAiragFlowAiGenServiceImpl()
     * @param component
     */
    @LiteflowMethod(
        value = LiteFlowMethodEnum.PROCESS,
        nodeType = NodeTypeEnum.COMMON
    )
    public void processEndNode(NodeComponent component) {
        logger.info("节点开始-end");
        FlowContext flowContext = getFlowContext(component);
        FlowNodeConfig var3 = getNodeConfig(component);
        List var4 = var3.getOutputParams();
        HashMap var5 = new HashMap();
        Iterator var6 = var4.iterator();

        while(var6.hasNext()) {
            FlowNodeConfig.NodeParam var7 = (FlowNodeConfig.NodeParam)var6.next();
            Object var8 = flowContext.getNodeFieldValue(var7.getNodeId(), var7.getField());
            var5.put(var7.getName(), var8);
        }

        boolean var13 = false;
        JSONObject var14 = null;
        if (oConvertUtils.isObjectNotEmpty(var3.getOptions())) {
            var14 = JSONObject.parseObject(JSONObject.toJSONString(var3.getOptions()));
            var13 = var14.getBooleanValue("outputText");
        }

        if (var13) {
            String var15 = var14.getString("outputContent");
            if (oConvertUtils.isNotEmpty(var15)) {
                Matcher var9 = TEMPLATE_PATTERN.matcher(var15);
                StringBuffer var10 = new StringBuffer();

                while(var9.find()) {
                    String var11 = var9.group(1);
                    Object var12 = oConvertUtils.isObjectNotEmpty(var5) ? var5.get(var11) : null;
                    var9.appendReplacement(var10, var12 != null ? Matcher.quoteReplacement(var12.toString()) : "");
                }

                var9.appendTail(var10);
                flowContext.setResult(var10.toString());
            }
        } else {
            flowContext.setResult(var5);
        }

        component.setIsEnd(true);
    }
}
