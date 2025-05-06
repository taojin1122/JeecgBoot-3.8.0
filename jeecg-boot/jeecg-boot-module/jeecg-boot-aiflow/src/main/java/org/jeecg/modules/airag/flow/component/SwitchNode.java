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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.airag.flow.component.base.FlowNodeHelper;
import org.jeecg.modules.airag.flow.context.FlowContext;
import org.jeecg.modules.airag.flow.exception.FlowNodeHelperException;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowCondition;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowNodeConfig;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowSwitchRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 这是一个流程中的条件分支节点，用于处理流程的条件判断和路由，它继承自基础的 FlowNodeHelper 类。
 * 建议将类名从 i 改为 SwitchNode，更好地表达其作为流程分支节点的功能。
 */
@Component("switch")
public class SwitchNode extends FlowNodeHelper {
    private static final Logger logger = LoggerFactory.getLogger(SwitchNode.class);

    public SwitchNode() {
    }

    @LiteflowMethod(
            value = LiteFlowMethodEnum.PROCESS_SWITCH,
            nodeType = NodeTypeEnum.SWITCH
    )
    public String processSwitchNode(NodeComponent component) {  // 原 IAiragFlowAiGenServiceImpl(NodeComponent)
        logger.info("节点开始-switch");
        FlowContext context = getFlowContext(component);
        FlowNodeConfig nodeConfig = getNodeConfig(component);
        Map options = nodeConfig.getOptions();
        List ifConditions = (List)options.get("if");
        List<FlowSwitchRule> switchRules = new ArrayList();

        // 转换条件规则
        if (null != ifConditions && !ifConditions.isEmpty()) {
            switchRules = (List)ifConditions.stream().map((rule) -> {
                return (FlowSwitchRule)JSONObject.parseObject(JSONObject.toJSONString(rule), FlowSwitchRule.class);
            }).collect(Collectors.toList());
        }

        // 遍历处理每个规则
        for(int ruleIndex = 0; ruleIndex < switchRules.size(); ++ruleIndex) {
            FlowSwitchRule rule = (FlowSwitchRule)switchRules.get(ruleIndex);
            List conditions = rule.getConditions();
            String logic = rule.getLogic();
            boolean isMatch = false;
            Iterator conditionIterator = conditions.iterator();

            while(conditionIterator.hasNext()) {
                FlowCondition condition = (FlowCondition)conditionIterator.next();
                Object fieldValue = context.getNodeFieldValue(condition.getNodeId(), condition.getField());
                isMatch = this.evaluateCondition(fieldValue, condition.getOperator(), condition.getValue());
                if (isMatch && "OR".equals(logic) || !isMatch && "AND".equals(logic)) {
                    break;
                }
            }

            if (isMatch) {
                logger.info("[switch]跳转到{}", rule.getNext());
                context.setNodeFieldValue(nodeConfig.getNodeId(), "index", ruleIndex);
                return this.formatNextNodeTag(nodeConfig, rule.getNext());
            }
        }

        // 处理else分支
        Map elseBlock = (Map)options.get("else");
        if (null != elseBlock && !elseBlock.isEmpty()) {
            logger.info("[switch]跳转到{}", elseBlock.get("next"));
            context.setNodeFieldValue(nodeConfig.getNodeId(), "index", -1);
            return this.formatNextNodeTag(nodeConfig, (String)elseBlock.get("next"));
        } else {
            throw new FlowNodeHelperException(nodeConfig.getNodeId(), nodeConfig.getText(), "没有找到匹配的结果");
        }
    }

    private boolean evaluateCondition(Object value, String operator, String compareValue) {  // 原 IAiragFlowAiGenServiceImpl(Object, String, String)
        switch (operator) {
            case "CONTAINS":
                return value != null && value.toString().contains(compareValue);
            case "NOT_CONTAINS":
                return value == null || !value.toString().contains(compareValue);
            case "EQUALS":
                return value != null && value.toString().equals(compareValue);
            case "GTE":
                return value != null && this.compareNumbers(value, compareValue) >= 0;
            case "GT":
                return value != null && this.compareNumbers(value, compareValue) > 0;
            case "LTE":
                return value != null && this.compareNumbers(value, compareValue) <= 0;
            case "LT":
                return value != null && this.compareNumbers(value, compareValue) < 0;
            case "LEN_EQ":
                return value != null && value.toString().length() == Integer.parseInt(compareValue);
            case "LEN_GTE":
                return value != null && value.toString().length() >= Integer.parseInt(compareValue);
            case "LEN_GT":
                return value != null && value.toString().length() > Integer.parseInt(compareValue);
            case "LEN_LTE":
                return value != null && value.toString().length() <= Integer.parseInt(compareValue);
            case "LEN_LT":
                return value != null && value.toString().length() < Integer.parseInt(compareValue);
            case "EMPTY":
                return oConvertUtils.isObjectEmpty(value);
            case "NOT_EMPTY":
                return oConvertUtils.isObjectNotEmpty(value);
            default:
                return false;
        }
    }
    private int compareNumbers(Object value1, String value2) {  // 原 IAiragFlowAiGenServiceImpl(Object, String)
        try {
            double num1 = Double.parseDouble(value1.toString());
            double num2 = Double.parseDouble(value2);
            return Double.compare(num1, num2);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number format for comparison", e);
        }
    }
}
