package com.asideal.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Assert;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.variable.Variable;
import com.bstek.uflo.process.handler.ForeachHandler;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @author zhangjie
 * @date 2021-04-08
 */
@Component
public class AreasForeachHandler implements ForeachHandler {
    @Override
    public Collection<Object> handle(ProcessInstance processInstance, Context context) {
        // String deptVar = String.valueOf(context.getProcessService().getProcessVariable("dept", processInstance.getRootId()));
        List<Variable> variables = context.getProcessService().createProcessVariableQuery().processInstanceId(processInstance.getRootId()).key("area").list();

        Assert.isTrue(CollectionUtil.isNotEmpty(variables),"辖区变量为空");

        String deptStr = variables.get(0).getValue().toString();

        return ListUtil.toList(deptStr.split(","));
    }

    @Override
    public String desc() {
        return "获取辖区";
    }
}
