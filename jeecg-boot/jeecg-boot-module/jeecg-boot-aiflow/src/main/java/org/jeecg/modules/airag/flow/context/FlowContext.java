//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.context;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import javax.servlet.http.HttpServletRequest;
import org.jeecg.common.system.vo.SysUserCacheInfo;
import org.jeecg.modules.airag.common.vo.event.EventData;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowNode;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * 根据代码内容，这个类是一个流程上下文的数据容器，管理着流程执行过程中的各种数据和状态
 *
 * 这个命名合适因为：
 * 1、存储流程执行的上下文信息（Context）
 * 2、包含了：
     * 请求数据（RequestDatas）
     * 流程节点（FlowNodes）
     * 事件回调（EventCallback）
     * 用户信息（CacheUser）
     * 会话ID（ConversationId）
     * 结果数据（Result）
     * SSE发射器（Emitter）
 * 3、作为流程执行的数据容器和状态管理器
 * 这个名字能够清晰地表达类的用途：作为流程执行的上下文容器。
 */
public class FlowContext implements Serializable {
    private static final long f = -3630250155343804583L;
    // 基本信息字段
    private String requestId = "";
    private String conversationId;
    private String topicId;
    private Object result = null;
    // 数据存储
    private final ConcurrentHashMap<String, Object> contextData = new ConcurrentHashMap<>();
    Map<String, Object> requestData = new ConcurrentHashMap<>();
    Map<String, FlowNode> flowNodes = new ConcurrentHashMap<>();
    // 事件和请求相关
    private SseEmitter sseEmitter;
    private Consumer<EventData> eventCallback;
    HttpServletRequest httpRequest;
    SysUserCacheInfo cacheUser;
    String tenantId;

    public FlowContext() {
    }

    // 上下文数据操作方法
    public Map<String, Object> getContextDatas() {
        return this.contextData;
    }

    public <T> T getContextValue(String key) {  // 原 IAiragFlowAiGenServiceImpl(String)
        return this.contextData.containsKey(key) ? (T) this.contextData.get(key) : null;
    }

    public <T> T getNodeFieldValue(String nodeId, String field) {  // 原 IAiragFlowAiGenServiceImpl(String, String)
        String key = nodeId + "." + field;
        return this.getContextValue(key);
    }

    public <T> void setContextValue(String key, T value) {  // 原 IAiragFlowAiGenServiceImpl(String, T)
        if (null != value) {
            this.contextData.put(key, value);
        }
    }

    public <T> void setNodeFieldValue(String nodeId, String field, T value) {  // 原 IAiragFlowAiGenServiceImpl(String, String, T)
        String key = nodeId + "." + field;
        this.setContextValue(key, value);
    }

    // 请求数据操作方法
    public Map<String, Object> getRequestDatas() {
        return this.requestData;
    }


    public void setRequestDatas(Map<String, Object> requestData) {
        this.requestData = requestData;
    }

    public <T> T getRequestValue(String key) {  // 原 IAiragFlowServiceImpl(String)
        return this.requestData.containsKey(key) ? (T) this.requestData.get(key) : null;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public ConcurrentHashMap<String, Object> getContextData() {
        return contextData;
    }

    public Map<String, Object> getRequestData() {
        return requestData;
    }

    public void setRequestData(Map<String, Object> requestData) {
        this.requestData = requestData;
    }

    public Map<String, FlowNode> getFlowNodes() {
        return flowNodes;
    }

    public void setFlowNodes(Map<String, FlowNode> flowNodes) {
        this.flowNodes = flowNodes;
    }

    public SseEmitter getSseEmitter() {
        return sseEmitter;
    }

    public void setSseEmitter(SseEmitter sseEmitter) {
        this.sseEmitter = sseEmitter;
    }

    public Consumer<EventData> getEventCallback() {
        return eventCallback;
    }

    public void setEventCallback(Consumer<EventData> eventCallback) {
        this.eventCallback = eventCallback;
    }

    public HttpServletRequest getHttpRequest() {
        return httpRequest;
    }

    public void setHttpRequest(HttpServletRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public SysUserCacheInfo getCacheUser() {
        return cacheUser;
    }

    public void setCacheUser(SysUserCacheInfo cacheUser) {
        this.cacheUser = cacheUser;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
