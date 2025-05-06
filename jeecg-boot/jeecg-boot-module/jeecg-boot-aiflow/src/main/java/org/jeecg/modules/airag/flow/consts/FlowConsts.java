//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.consts;

import java.util.regex.Pattern;

public class FlowConsts {
    public static final String FLOW_STATUS_ENABLE = "enable";
    public static final String FLOW_STATUS_DISABLE = "disable";
    public static final String FLOW_INPUT_PARAM_HISTORY = "history";
    public static final String FLOW_INPUT_PARAM_QUESTION = "content";
    public static final String FLOW_INPUT_PARAM_IMAGES = "images";
    public static final String FLOW_PARAM_TYPE_STRING = "string";
    public static final String FLOW_PARAM_TYPE_NUMBER = "number";
    public static final String FLOW_PARAM_TYPE_PICTURE = "picture";
    public static final String FLOW_PARAM_TYPE_OBJECT = "object";
    public static final String FLOW_PARAM_TYPE_STRING_ARRAY = "string[]";
    public static final String FLOW_PARAM_TYPE_NUMBER_ARRAY = "number[]";
    public static final String FLOW_PARAM_TYPE_OBJECT_ARRAY = "object[]";
    public static final String FLOW_RESPONSE_MODE_STREAMING = "streaming";
    public static final String FLOW_RESPONSE_MODE_BLOCKING = "blocking";
    public static final String FLOW_DESIGN_NODES = "nodes";
    public static final String FLOW_METADATA_INPUTS = "inputs";
    public static final String FLOW_METADATA_OUTPUTS = "outputs";
    public static final String FLOW_NODE_TYPE_START = "start";
    public static final String FLOW_NODE_TYPE_SWITCH = "switch";
    public static final String FLOW_NODE_TYPE_LLM = "llm";
    public static final String FLOW_NODE_TYPE_KNOWLEDGE = "knowledge";
    public static final String FLOW_NODE_TYPE_CLASSIFIER = "classifier";
    public static final String FLOW_NODE_TYPE_END = "end";
    public static final String FLOW_NODE_TYPE_CODE = "code";
    public static final String FLOW_NODE_TYPE_SUBFLOW = "subflow";
    public static final String FLOW_NODE_TYPE_ENHANCE_JAVA = "enhanceJava";
    public static final String FLOW_NODE_TYPE_HTTP = "http";
    public static final String FLOW_NODE_TYPE_REPLY = "reply";
    public static final String FLOW_NODE_OPTION_CODE_TYPE = "codeType";
    public static final String FLOW_NODE_OPTION_CODE = "code";
    public static final String FLOW_NODE_OPTION_IF = "if";
    public static final String FLOW_NODE_OPTION_ELSE = "else";
    public static final String FLOW_NODE_OPTION_NEXT = "next";
    public static final String FLOW_NODE_OPTION_TOP_NUMBER = "topNumber";
    public static final String FLOW_NODE_OPTION_SIMILARITY = "similarity";
    public static final String FLOW_NODE_OPTION_KNOW_IDS = "knowIds";
    public static final String FLOW_NODE_OPTION_HTTP = "http";
    public static final String FLOW_NODE_OPTION_MODEL = "model";
    public static final String FLOW_NODE_OPTION_MODEL_MODEID = "modeId";
    public static final String FLOW_NODE_OPTION_MODEL_PARAMS = "params";
    public static final String FLOW_NODE_OPTION_MODEL_TEMPERATURE = "temperature";
    public static final String FLOW_NODE_OPTION_MODEL_TOP = "topP";
    public static final String FLOW_NODE_OPTION_MODEL_PRESENCE_PENALTY = "presencePenalty";
    public static final String FLOW_NODE_OPTION_MODEL_FREQUENCY_PENALTY = "frequencyPenalty";
    public static final String FLOW_NODE_OPTION_MODEL_MAX_TOKENS = "maxTokens";
    public static final String FLOW_NODE_OPTION_MODEL_MESSAGES = "messages";
    public static final String FLOW_NODE_OPTION_HISTORY_NUM = "history";
    public static final String FLOW_NODE_OPTION_MODEL_CATEGORIES = "categories";
    public static final String FLOW_NODE_OPTION_MODEL_CATEGORY = "category";
    public static final String FLOW_NODE_OPTION_SUBFLOW_ID = "subflowId";
    public static final String FLOW_NODE_OPTION_ENHANCE = "enhance";
    public static final String FLOW_NODE_OPTION_OUTPUT_TEXT = "outputText";
    public static final String FLOW_NODE_OPTION_OUTPUT_CONTENT = "outputContent";
    public static final String FLOW_NODE_OPTION_LOGIC_AND = "AND";
    public static final String FLOW_NODE_OPTION_LOGIC_OR = "OR";
    public static final String FLOW_NODE_OPTION_OP_CONTAINS = "CONTAINS";
    public static final String FLOW_NODE_OPTION_OP_NOT_CONTAINS = "NOT_CONTAINS";
    public static final String FLOW_NODE_OPTION_OP_EQUALS = "EQUALS";
    public static final String FLOW_NODE_OPTION_OP_GTE = "GTE";
    public static final String FLOW_NODE_OPTION_OP_GT = "GT";
    public static final String FLOW_NODE_OPTION_OP_LTE = "LTE";
    public static final String FLOW_NODE_OPTION_OP_LT = "LT";
    public static final String FLOW_NODE_OPTION_OP_LEN_EQ = "LEN_EQ";
    public static final String FLOW_NODE_OPTION_OP_LEN_GTE = "LEN_GTE";
    public static final String FLOW_NODE_OPTION_OP_LEN_GT = "LEN_GT";
    public static final String FLOW_NODE_OPTION_OP_LEN_LTE = "LEN_LTE";
    public static final String FLOW_NODE_OPTION_OP_LEN_LT = "LEN_LT";
    public static final String FLOW_NODE_OPTION_OP_EMPTY = "EMPTY";
    public static final String FLOW_NODE_OPTION_OP_NOT_EMPTY = "NOT_EMPTY";
    public static final String FLOW_NODE_OPTION_CONTENT = "content";
    public static final String FLOW_CONTEXT_PARAM_SEPARATOR = ".";
    public static final String FLOW_OUTPUT_FIELD_OUTPUT_TEXT = "outputText";
    public static final String FLOW_OUTPUT_FIELD_INDEX = "index";
    public static final String FLOW_OUTPUT_FIELD_CONTENT = "content";
    public static final Pattern VAR_PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{(.*?)}}");

    public FlowConsts() {
    }
}
