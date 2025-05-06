

let params = seq.map();
<#list inputParams as inputParam>
    seq.put(params, "${inputParam.name}",getContextData(jeecgFlowContext, "${inputParam.nodeId}", "${inputParam.field}"));
</#list>
println(params);
${scriptContent}
println(resp);
<#list outputParams as outputParam>
    setContextData(jeecgFlowContext, "${flowNodeObjId}", "${outputParam.field}", seq.get(resp,"${outputParam.field}"));
</#list>
