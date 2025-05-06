${scriptContent}

var params = {};
<#list inputParams as inputParam>
    params["${inputParam.name}"] = jeecgFlowContext.getContextData("${inputParam.nodeId}", "${inputParam.field}");
</#list>

var resp = main(params);

<#list outputParams as outputParam>
    jeecgFlowContext.setContextData("${flowNodeObjId}","${outputParam.field}", resp["${outputParam.field}"]);
</#list>
