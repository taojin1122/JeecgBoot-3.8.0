//
// Source code recreated from FlowNodeHelper .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.jeecg.modules.airag.flow.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.jeecg.chatgpt.dto.chat.MultiChatMessage;
import org.jeecg.chatgpt.dto.chat.MultiChatMessage.Role;
import org.jeecg.chatgpt.service.AiChatService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootBizTipException;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.airag.flow.service.IAiragFlowAiGenService;
import org.jeecg.modules.airag.flow.vo.api.AigcTestDataParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service("airagFlowAiGenServiceImpl")
public class IAiragFlowAiGenServiceImpl implements IAiragFlowAiGenService {
    private static final Logger a = LoggerFactory.getLogger(IAiragFlowAiGenServiceImpl.class);
    @Autowired
    AiChatService aiChatService;



    public Result<?> genTestData(AigcTestDataParams params) {
        if (params != null && !CollectionUtils.isEmpty(params.getFields())) {
            JeecgBootBizTipException var2 = new JeecgBootBizTipException("Ai开小差了，请稍后再试");
            List var3 = params.getFields();
            StringBuilder var4 = new StringBuilder();
            Iterator var5 = var3.iterator();

            while(var5.hasNext()) {
                AigcTestDataParams.FieldItem var6 = (AigcTestDataParams.FieldItem)var5.next();
                if (!oConvertUtils.isEmpty(var6.getField())) {
                    var4.append(var6.getField());
                    if (oConvertUtils.isNotEmpty(var6.getName())) {
                        var4.append("（").append(var6.getName()).append("）");
                    }

                    var4.append("：").append(var6.getType());
                    if (var6.isRequired()) {
                        var4.append("：必填");
                    }

                    var4.append("\n");
                }
            }

            String var11 = var4.toString();
            if (oConvertUtils.isEmpty(var11)) {
                return Result.error("没有待生成的字段");
            } else {
                String var12 = this.a(var11);
                if (oConvertUtils.isEmpty(var12)) {
                    throw var2;
                } else {
                    JSONObject var7;
                    try {
                        var7 = JSONArray.parseObject(var12);
                    } catch (JSONException var9) {
                        throw var2;
                    } catch (Exception var10) {
                        a.error(var10.getMessage(), var10);
                        throw var2;
                    }

                    JSONObject var8 = new JSONObject();
                    var8.put("data", var7);
                    return Result.OK("生成成功", var8);
                }
            }
        } else {
            return Result.error("没有待生成的字段");
        }
    }

    private String a(String var1) {
        String var2 = "{\"name\":\"张三\",\"age\":23}";
        String var3 = "请根据字段列表帮我生成一个测试数据JSON，测试数据参考JSON如下：" + var2 + "\n在该JSON数组中，key为字段的field，value为你生成的数据，要严格按照字段类型，如果是文本需要保持在10个字左右";
        LinkedList var4 = new LinkedList();
        var4.add(MultiChatMessage.builder().role(Role.SYSTEM).content("请严格按照参考json数组的格式输出，不要有其他任何描述，输出内容应以{开头，以}结尾").build());
        var4.add(MultiChatMessage.builder().role(Role.USER).content(var3).build());
        var4.add(MultiChatMessage.builder().role(Role.USER).content("\n\n我的字段如下：\n> " + var1 + "\n").build());
        String var5 = this.aiChatService.multiCompletions(var4);
        if (StringUtils.isEmpty(var5)) {
            throw new JeecgBootBizTipException("如果您想使用AI助手，请先设置相应配置!");
        } else {
            if (var5.contains("</think>")) {
                String[] var6 = var5.split("</think>");
                var5 = var6[var6.length - 1];
            }

            a.warn("Ai返回结果：" + var5);
            Pattern var9 = Pattern.compile("\\{.*}", 32);
            Matcher var7 = var9.matcher(var5);
            String var8 = "";
            if (var7.find()) {
                var8 = var7.group(0);
            }

            return var8;
        }
    }
}
