package com.bstek.uflo.command.impl;

import com.bstek.uflo.command.Command;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;

import java.util.Map;

public class SaveTaskNextAssigneeCommand implements Command<Object> {
    private String nextAssignee;
    private Long taskId;
    public SaveTaskNextAssigneeCommand(String nextAssignee,Long taskId){
        this.nextAssignee=nextAssignee;
        this.taskId=taskId;
    }

    @Override
    public Object execute(Context context) {
        Task task = context.getTaskService().getTask(taskId);
        task.setNextAssignee(nextAssignee);
        context.getSession().saveOrUpdate(task);
        return null;
    }
}
