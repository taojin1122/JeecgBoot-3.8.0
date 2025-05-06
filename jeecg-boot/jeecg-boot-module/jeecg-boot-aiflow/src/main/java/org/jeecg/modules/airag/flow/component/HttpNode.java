//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import javax.servlet.http.HttpServletRequest;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.SysUserCacheInfo;
import org.jeecg.common.util.CommonUtils;
import org.jeecg.common.util.RestUtil;
import org.jeecg.common.util.TokenUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.airag.flow.context.FlowContext;
import org.jeecg.modules.airag.flow.component.base.FlowNodeHelper;
import org.jeecg.modules.airag.flow.consts.FlowConsts;
import org.jeecg.modules.airag.flow.exception.FlowNodeHelperException;
import org.jeecg.modules.airag.flow.vo.flow.config.FlowNodeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component("http")
public class HttpNode extends FlowNodeHelper {
    private static final Logger logger = LoggerFactory.getLogger(HttpNode.class);
    public static final String BODY_FIELD = "body";
    public static final String STATUS_CODE_FIELD = "statusCode";
    private static final String CONTENT_TYPE_NONE = "none";
    private static final String CONTENT_TYPE_BINARY = "binary";
    private static final String CONTENT_TYPE_RAW = "raw";
    private static final String CONTENT_TYPE_FORM_URLENCODED = "x-www-form-urlencoded";
    private static final String CONTENT_TYPE_FORM_DATA = "form-data";
    private static final String CONTENT_TYPE_JSON = "json";

    public HttpNode() {
    }

    @LiteflowMethod(
        value = LiteFlowMethodEnum.PROCESS,
        nodeType = NodeTypeEnum.COMMON
    )
    public void processHttpRequest(NodeComponent component) { // 原 IAiragFlowAiGenServiceImpl(NodeComponent)
        logger.info("节点开始-http");
        FlowContext flowContext = getFlowContext(component);
        FlowNodeConfig flowNodeConfig = getNodeConfig(component);
        List<FlowNodeConfig.NodeParam> inputParams = flowNodeConfig.getInputParams();
        if (oConvertUtils.isObjectEmpty(flowNodeConfig.getOptions())) {
            throw new FlowNodeHelperException(flowNodeConfig.getNodeId(), flowNodeConfig.getText(), "节点配置错误");
        } else {
            JSONObject var5 = JSONObject.parseObject(JSONObject.toJSONString(flowNodeConfig.getOptions()));
            HttpOptions httpOptions = (HttpOptions)var5.getJSONObject("http").toJavaObject(HttpOptions.class);
            if (!oConvertUtils.isEmpty(httpOptions.getMethod()) && !oConvertUtils.isEmpty(httpOptions.getUrl())) {
                RequestParam requestParam = this.buildRequestParam(httpOptions, flowNodeConfig, flowContext, inputParams);

                try {
                    logger.info("[FLOW_HTTP]发送http请求:url:[{}]", requestParam.getUrl());
                    ResponseEntity responseEntity = RestUtil.request(requestParam.getUrl(), requestParam.getHttpMethod(), requestParam.getHttpHeaders(),
                            requestParam.getRequestParams(), requestParam.getBody(), String.class);
                    flowContext.setNodeFieldValue(flowNodeConfig.getNodeId(), "statusCode", responseEntity.getStatusCodeValue());
                    String responseEntityBody = (String)responseEntity.getBody();
                    flowContext.setNodeFieldValue(flowNodeConfig.getNodeId(), "body", responseEntityBody);
                    if (oConvertUtils.isNotEmpty(responseEntityBody)) {
                        this.processResponseOutput(flowNodeConfig, responseEntityBody, flowContext);
                    }

                } catch (Exception var13) {
                    String var10 = var13.getMessage();
                    if (var13 instanceof HttpClientErrorException) {
                        HttpClientErrorException var12 = (HttpClientErrorException)var13;
                        var10 = var12.getStatusCode().toString();
                    }

                    logger.error("[FLOW_HTTP]发送http请求失败:" + var10, var13);
                    throw new FlowNodeHelperException(flowNodeConfig.getNodeId(), flowNodeConfig.getText(), "发送http请求失败:" + var10);
                }
            } else {
                throw new FlowNodeHelperException(flowNodeConfig.getNodeId(), flowNodeConfig.getText(), "缺少必要参数");
            }
        }
    }

    /**
     * // 原 IAiragFlowAiGenServiceImpl(IAiragFlowAiGenServiceImpl, FlowNodeConfig, FlowContext, List)
     * @param options
     * @param config
     * @param context
     * @param inputParams
     * @return
     */
    private RequestParam buildRequestParam(HttpOptions options, FlowNodeConfig config, FlowContext context, List<FlowNodeConfig.NodeParam> inputParams) {
        HttpMethod httpMethod = HttpMethod.resolve(options.getMethod().toUpperCase());
        if (httpMethod == null) {
            throw new FlowNodeHelperException(config.getNodeId(), config.getText(), "无效的HTTP方法");
        } else {
            HttpServletRequest httpServletRequest = context.getHttpRequest();
            SysUserCacheInfo cacheUser = context.getCacheUser();
            Map<String, Object> inputParametersMap = this.collectInputParameters(inputParams, context);
            String url = options.getUrl();
            if (url.contains("{") && url.contains("}")) {
                if (url.contains("{{domainURL}}")) {
                    String var10 = CommonUtils.getBaseUrl(httpServletRequest);
                    url = url.replaceFirst("\\{\\{domainURL}}", var10);
                }

                url = replaceVariables(url, inputParametersMap, cacheUser);
            }

            JSONObject var16 = options.getRequestBody();
            String type = var16.getString("type");
            String body = var16.getString("body");
            body = this.escapeHtml(body);
            body = replaceVariables(body, inputParametersMap, cacheUser);
            Object var13 = body;
            if (!type.equalsIgnoreCase("raw") && !type.equalsIgnoreCase("none")) {
                var13 = JSONObject.parseObject(body);
            }

            JSONObject requestParams = options.getRequestParams();
            if (null != requestParams) {
                requestParams.keySet().forEach((var4x) -> {
                    Object var5 = requestParams.get(var4x);
                    if (var5 instanceof String) {
                        String var6 = (String)var5;
                        var6 = this.escapeHtml(var6);
                        var6 = replaceVariables(var6, inputParametersMap, cacheUser);
                        requestParams.put(var4x, var6);
                    }

                });
            }

            HttpHeaders httpHeaders = buildHttpHeaders(type, context, options, inputParametersMap, cacheUser);
            return new RequestParam(httpMethod, url, var13, requestParams, httpHeaders);
        }
    }

    /**
     * // 原 IAiragFlowAiGenServiceImpl(String, Map, SysUserCacheInfo)
     * @param content
     * @param variables
     * @param userCache
     * @return
     */
    private static String replaceVariables(String content, Map<String, Object> variables, SysUserCacheInfo userCache) {
        Matcher var3 = FlowConsts.VAR_PLACEHOLDER_PATTERN.matcher(content);

        StringBuffer var4;
        Object var6;
        for(var4 = new StringBuffer(); var3.find(); var3.appendReplacement(var4, var6 != null ? Matcher.quoteReplacement(var6.toString()) : "")) {
            String var5 = var3.group(1);
            var6 = oConvertUtils.isObjectNotEmpty(variables) ? variables.get(var5) : null;
            if (null == var6) {
                var6 = JwtUtil.getUserSystemData("#{" + var5 + "}", userCache);
            }
        }

        var3.appendTail(var4);
        return var4.toString();
    }

    /**
     *  // 原 IAiragFlowAiGenServiceImpl(List, FlowContext)
     * @param params
     * @param context
     * @return
     */
    private Map<String, Object> collectInputParameters(List<FlowNodeConfig.NodeParam> params, FlowContext context) {
        HashMap var3 = new HashMap();
        Iterator var4 = params.iterator();

        while(var4.hasNext()) {
            FlowNodeConfig.NodeParam var5 = (FlowNodeConfig.NodeParam)var4.next();
            Object var6 = context.getNodeFieldValue(var5.getNodeId(), var5.getField());
            var3.put(var5.getName(), var6);
        }

        return var3;
    }

    /**
     * // 原 IAiragFlowAiGenServiceImpl(String, FlowContext, IAiragFlowAiGenServiceImpl, Map, SysUserCacheInfo)
     * @param contentType
     * @param context
     * @param options
     * @param variables
     * @param userCache
     * @return
     */
    private static HttpHeaders buildHttpHeaders(String contentType, FlowContext context, HttpOptions options,
                                                Map<String, Object> variables, SysUserCacheInfo userCache) {
        HttpHeaders var5;
        switch (contentType) {
            case "none":
                var5 = new HttpHeaders();
                break;
            case "json":
                var5 = RestUtil.getHeader("application/json;charset=UTF-8");
                break;
            case "form-data":
                var5 = RestUtil.getHeader("multipart/form-data");
                break;
            case "x-www-form-urlencoded":
                var5 = RestUtil.getHeader("application/x-www-form-urlencoded");
                break;
            case "raw":
                var5 = RestUtil.getHeader("text/plain");
                break;
            case "binary":
                var5 = RestUtil.getHeader("application/octet-stream");
                break;
            default:
                var5 = RestUtil.getHeaderApplicationJson();
        }

        HttpServletRequest var6 = context.getHttpRequest();
        String var13 = TokenUtils.getTokenByRequest(var6);
        if (oConvertUtils.isNotEmpty(var13)) {
            var5.set("X-Access-Token", var13);
        }

        String var8 = TokenUtils.getTenantIdByRequest(var6);
        if (oConvertUtils.isNotEmpty(var8)) {
            var5.set("X-Tenant-Id", var8);
        }

        JSONObject headers = options.getHeaders();
        Iterator iterator = headers.keySet().iterator();

        while(iterator.hasNext()) {
            String var11 = (String)iterator.next();
            String var12 = headers.getString(var11);
            var12 = replaceVariables(var12, variables, userCache);
            var5.add(var11, var12);
        }

        return var5;
    }

    /**
     *  // 原 IAiragFlowAiGenServiceImpl(String)
     * @param content
     * @return
     */
    private String escapeHtml(String content) {
        content = content.replaceAll("&", "&amp;");
        content = content.replaceAll("<", "&lt;");
        content = content.replaceAll(">", "&gt;");
        content = content.replaceAll("'", "&#x27;");
        content = content.replaceAll("/", "&#x2F;");
        content = content.replace(" ", " ");
        return content;
    }

    /**
     *
     * @param config
     * @param response
     * @param context
     */
    public void processResponseOutput(FlowNodeConfig config, String response, FlowContext context) {
        List var4 = config.getOutputParams();
        if (!oConvertUtils.isEmpty(response)) {
            JSONObject jsonObject = JSONObject.parseObject(response);
            Iterator var6 = var4.iterator();

            while(var6.hasNext()) {
                FlowNodeConfig.NodeParam var7 = (FlowNodeConfig.NodeParam)var6.next();
                String field = var7.getField();
                if (!oConvertUtils.isEmpty(field) && !"body".equalsIgnoreCase(field) && !"statusCode".equalsIgnoreCase(field)) {
                    Object object = this.getJsonValue(field, jsonObject);
                    if (object != null) {
                        try {
                            object = this.convertValueType(object, var7.getType());
                        } catch (Exception var11) {
                            logger.error("转换类型失败,field:{},value:{}", field, object);
                            throw new FlowNodeHelperException(config.getNodeId(), config.getText(), "转换类型失败");
                        }
                    }

                    context.setNodeFieldValue(config.getNodeId(), field, object != null ? object : "");
                }
            }

        }
    }

    /**
     *
     * @param field
     * @param json
     * @return
     */
    private Object getJsonValue(String field, JSONObject json) {
        if (field.startsWith("body.")) {
            field = field.replaceFirst("body.", "");
        }

        String[] var3 = field.split("\\.");
        Object var4 = json;
        String[] var5 = var3;
        int var6 = var3.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            String var8 = var5[var7];
            if (!(var4 instanceof JSONObject)) {
                return null;
            }

            if (var8.contains("[") && var8.contains("]")) {
                String var9 = var8.substring(0, var8.indexOf("["));
                int var10 = Integer.parseInt(var8.substring(var8.indexOf("[") + 1, var8.indexOf("]")));
                JSONArray var11 = ((JSONObject)var4).getJSONArray(var9);
                if (var11.size() > var10 + 1) {
                    var4 = var11.get(var10);
                } else {
                    var4 = null;
                }
            } else {
                var4 = ((JSONObject)var4).get(var8);
            }
        }

        return var4;
    }

    private Object convertValueType(Object value, String type) {
        if ("string".equals(type)) {
            return value.toString();
        } else if ("number".equals(type)) {
            return value instanceof Number ? value : Double.parseDouble(value.toString());
        } else if ("picture".equals(type)) {
            return value.toString();
        } else if ("object".equals(type)) {
            return value instanceof JSONObject ? value : JSONObject.parseObject(value.toString());
        } else if ("string[]".equals(type)) {
            return value instanceof List ? ((List)value).toArray(new Object[0]) : new String[]{value.toString()};
        } else if ("number[]".equals(type)) {
            return value instanceof List ? ((List)value).stream().mapToDouble((var0) -> {
                return Double.parseDouble(var0.toString());
            }).toArray() : new double[]{Double.parseDouble(value.toString())};
        } else if ("object[]".equals(type)) {
            return value instanceof List ? ((List)value).toArray(new Object[0]) : new JSONObject[]{JSONObject.parseObject(value.toString())};
        } else {
            return value;
        }
    }

    /**
     *  // 原 IAiragFlowAiGenServiceImpl 类
     */
    public static class HttpOptions{
        private String method;
        private String url;
        private JSONObject requestParams;
        private JSONObject requestBody;
        private JSONObject headers;
        private int timeout;

        public HttpOptions() {
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public JSONObject getRequestParams() {
            return requestParams;
        }

        public void setRequestParams(JSONObject requestParams) {
            this.requestParams = requestParams;
        }

        public JSONObject getRequestBody() {
            return requestBody;
        }

        public void setRequestBody(JSONObject requestBody) {
            this.requestBody = requestBody;
        }

        public JSONObject getHeaders() {
            return headers;
        }

        public void setHeaders(JSONObject headers) {
            this.headers = headers;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof HttpOptions)) {
                return false;
            } else {
                HttpOptions var2 = (HttpOptions)o;
                if (!var2.canEqual(this)) {
                    return false;
                } else if (this.getTimeout() != var2.getTimeout()) {
                    return false;
                } else {
                    label73: {
                        String var3 = this.getMethod();
                        String var4 = var2.getMethod();
                        if (var3 == null) {
                            if (var4 == null) {
                                break label73;
                            }
                        } else if (var3.equals(var4)) {
                            break label73;
                        }

                        return false;
                    }

                    String var5 = this.getUrl();
                    String var6 = var2.getUrl();
                    if (var5 == null) {
                        if (var6 != null) {
                            return false;
                        }
                    } else if (!var5.equals(var6)) {
                        return false;
                    }

                    label59: {
                        JSONObject var7 = this.getRequestParams();
                        JSONObject var8 = var2.getRequestParams();
                        if (var7 == null) {
                            if (var8 == null) {
                                break label59;
                            }
                        } else if (var7.equals(var8)) {
                            break label59;
                        }

                        return false;
                    }

                    JSONObject var9 = this.getRequestBody();
                    JSONObject var10 = var2.getRequestBody();
                    if (var9 == null) {
                        if (var10 != null) {
                            return false;
                        }
                    } else if (!var9.equals(var10)) {
                        return false;
                    }

                    JSONObject var11 = this.getHeaders();
                    JSONObject var12 = var2.getHeaders();
                    if (var11 == null) {
                        if (var12 != null) {
                            return false;
                        }
                    } else if (!var11.equals(var12)) {
                        return false;
                    }

                    return true;
                }
            }
        }

        /**
         * canEqual 是一个常用的命名约定，用于 equals() 方法中检查对象是否可以进行比较
         * @param var1
         * @return
         */
        protected boolean canEqual(Object var1) {
            return var1 instanceof HttpOptions;
        }

        public int hashCode() {
            boolean var1 = true;
            int var2 = 1;
            var2 = var2 * 59 + this.getTimeout();
            String var3 = this.getMethod();
            var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
            String var4 = this.getUrl();
            var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
            JSONObject var5 = this.getRequestParams();
            var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
            JSONObject var6 = this.getRequestBody();
            var2 = var2 * 59 + (var6 == null ? 43 : var6.hashCode());
            JSONObject var7 = this.getHeaders();
            var2 = var2 * 59 + (var7 == null ? 43 : var7.hashCode());
            return var2;
        }

        public String toString() {
            return "HttpNode.HttpOptions(method=" + this.getMethod() + ", url=" + this.getUrl() + ", requestParams=" + this.getRequestParams() + ", requestBody=" + this.getRequestBody() + ", headers=" + this.getHeaders() + ", timeout=" + this.getTimeout() + ")";
        }
    }

    /**
     * // 原 IAiragFlowServiceImpl 类
     */
    private static class RequestParam {
        private final HttpMethod httpMethod;
        private final String url;
        private final Object body;
        private final JSONObject requestParams;
        private final HttpHeaders httpHeaders;

        public RequestParam(HttpMethod httpMethod, String url, Object body, JSONObject requestParams, HttpHeaders httpHeaders) {
            this.httpMethod = httpMethod;
            this.url = url;
            this.body = body;
            this.requestParams = requestParams;
            this.httpHeaders = httpHeaders;
        }

        public HttpMethod getHttpMethod() {
            return httpMethod;
        }

        public String getUrl() {
            return url;
        }

        public Object getBody() {
            return body;
        }

        public JSONObject getRequestParams() {
            return requestParams;
        }

        public HttpHeaders getHttpHeaders() {
            return httpHeaders;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof RequestParam)) {
                return false;
            } else {
                RequestParam var2 = (RequestParam)o;
                if (!var2.canEqual(this)) {
                    return false;
                } else {
                    label71: {
                        HttpMethod var3 = this.getHttpMethod();
                        HttpMethod var4 = var2.getHttpMethod();
                        if (var3 == null) {
                            if (var4 == null) {
                                break label71;
                            }
                        } else if (var3.equals(var4)) {
                            break label71;
                        }

                        return false;
                    }

                    String var5 = this.getUrl();
                    String var6 = var2.getUrl();
                    if (var5 == null) {
                        if (var6 != null) {
                            return false;
                        }
                    } else if (!var5.equals(var6)) {
                        return false;
                    }

                    label57: {
                        Object var7 = this.getBody();
                        Object var8 = var2.getBody();
                        if (var7 == null) {
                            if (var8 == null) {
                                break label57;
                            }
                        } else if (var7.equals(var8)) {
                            break label57;
                        }

                        return false;
                    }

                    JSONObject var9 = this.getRequestParams();
                    JSONObject var10 = var2.getRequestParams();
                    if (var9 == null) {
                        if (var10 != null) {
                            return false;
                        }
                    } else if (!var9.equals(var10)) {
                        return false;
                    }

                    HttpHeaders var11 = this.getHttpHeaders();
                    HttpHeaders var12 = var2.getHttpHeaders();
                    if (var11 == null) {
                        if (var12 == null) {
                            return true;
                        }
                    } else if (var11.equals(var12)) {
                        return true;
                    }

                    return false;
                }
            }
        }

        /**
         * canEqual 是一个常用的命名约定，用于 equals() 方法中检查对象是否可以进行比较
         * @param var1
         * @return
         */
        protected boolean canEqual(Object var1) {
            return var1 instanceof RequestParam;
        }

        public int hashCode() {
            boolean var1 = true;
            int var2 = 1;
            HttpMethod var3 = this.getHttpMethod();
            var2 = var2 * 59 + (var3 == null ? 43 : var3.hashCode());
            String var4 = this.getUrl();
            var2 = var2 * 59 + (var4 == null ? 43 : var4.hashCode());
            Object var5 = this.getBody();
            var2 = var2 * 59 + (var5 == null ? 43 : var5.hashCode());
            JSONObject var6 = this.getRequestParams();
            var2 = var2 * 59 + (var6 == null ? 43 : var6.hashCode());
            HttpHeaders var7 = this.getHttpHeaders();
            var2 = var2 * 59 + (var7 == null ? 43 : var7.hashCode());
            return var2;
        }

        public String toString() {
            return "HttpNode.ReqParam(httpMethod=" + this.getHttpMethod() + ", url=" + this.getUrl() + ", body=" + this.getBody() + ", requestParams=" + this.getRequestParams() + ", httpHeaders=" + this.getHttpHeaders() + ")";
        }
    }
}
