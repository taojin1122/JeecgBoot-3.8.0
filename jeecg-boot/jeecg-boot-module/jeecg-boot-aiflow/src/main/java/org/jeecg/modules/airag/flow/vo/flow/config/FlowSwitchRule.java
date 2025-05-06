//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.vo.flow.config;

import java.util.List;

public class FlowSwitchRule {
    private String logic;
    private List<FlowCondition> conditions;
    private String next;

    public FlowSwitchRule() {
    }

    public String getLogic() {
        return this.logic;
    }

    public List<FlowCondition> getConditions() {
        return this.conditions;
    }

    public String getNext() {
        return this.next;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public void setConditions(List<FlowCondition> conditions) {
        this.conditions = conditions;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public FlowSwitchRule(String logic, List<FlowCondition> conditions, String next) {
        this.logic = logic;
        this.conditions = conditions;
        this.next = next;
    }
}
