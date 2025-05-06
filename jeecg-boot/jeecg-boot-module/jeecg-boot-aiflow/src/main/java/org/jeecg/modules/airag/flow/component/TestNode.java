package org.jeecg.modules.airag.flow.component;

import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import org.jeecg.modules.airag.flow.component.base.FlowNodeHelper;
import org.springframework.stereotype.Component;

/**
 * @author Admin
 * @create 2025/4/30 15:00
 */
@Component("testNode")
public class TestNode extends FlowNodeHelper {

    @LiteflowMethod(
            value = LiteFlowMethodEnum.PROCESS,
            nodeType = NodeTypeEnum.COMMON
    )
    public void processSubflow(NodeComponent component) {

    }
}
