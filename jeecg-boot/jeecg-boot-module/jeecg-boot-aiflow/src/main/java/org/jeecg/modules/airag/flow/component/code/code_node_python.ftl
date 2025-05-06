params = {}
<#list inputParams as inputParam>
    params["${inputParam.name}"] = jeecgFlowContext.getContextData("${inputParam.nodeId}", "${inputParam.field}")
</#list>

${scriptContent}

<#list outputParams as outputParam>
    jeecgFlowContext.setContextData("${flowNodeObjId}", "${outputParam.field}", resp.get("${outputParam.field}"))
</#list>
