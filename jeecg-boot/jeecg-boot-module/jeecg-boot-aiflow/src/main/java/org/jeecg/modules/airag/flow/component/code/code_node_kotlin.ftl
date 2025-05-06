import org.jeecg.modules.airag.flow.context.JeecgFlowContext

val jeecgFlowContext = bindings["jeecgFlowContext"] as JeecgFlowContext

val params = mutableMapOf<String, Any?>()
<#list inputParams as inputParam>
    params["${inputParam.name}"] = jeecgFlowContext.getContextData("${inputParam.nodeId}", "${inputParam.field}")
</#list>

${scriptContent}

val resp = main(params)

<#list outputParams as outputParam>
    jeecgFlowContext.setContextData("${flowNodeObjId}", "${outputParam.field}", resp["${outputParam.field}"])
</#list>
