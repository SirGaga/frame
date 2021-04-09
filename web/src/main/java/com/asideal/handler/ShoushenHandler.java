package com.asideal.handler;

import cn.hutool.core.collection.ListUtil;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.AssignmentHandler;
import com.bstek.uflo.process.node.TaskNode;
import com.bstek.uflo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collection;

@Component
public class ShoushenHandler implements AssignmentHandler {

    @Autowired
    private TaskService taskService;
    @Override
    public Collection<String> handle(TaskNode taskNode, ProcessInstance processInstance, Context context) {
        //从流程变量中取受审人员
        Assert.isTrue(context.getProcessService().getProcessVariable("users",processInstance)!=null,"流程为传入受审人员");
        String[] assignees = context.getProcessService().getProcessVariable("users",processInstance).toString().split(",");
        return ListUtil.toList(assignees);
    }

    @Override
    public String desc() {
        return "获取受审部门节点处理人";
    }
}
