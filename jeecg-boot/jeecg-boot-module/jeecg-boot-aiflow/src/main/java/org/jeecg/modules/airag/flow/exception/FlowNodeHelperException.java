//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.exception;

import org.jeecg.common.exception.JeecgBootException;

public class FlowNodeHelperException extends JeecgBootException {
    private static final long c = -9008987328133246231L;
    String nodeId;
    String nodeName;

    public FlowNodeHelperException(String message) {
        super(message);
    }

    public FlowNodeHelperException(String nodeId, String nodeName, String message) {
        super(message);
        this.nodeName = nodeName;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public String getNodeName() {
        return this.nodeName;
    }
}
