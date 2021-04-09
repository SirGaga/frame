package com.asideal.handler;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.model.task.TaskState;
import com.bstek.uflo.process.handler.CountersignHandler;
import com.bstek.uflo.query.TaskQuery;
import com.asideal.common.HandleResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultCountersignHandler implements CountersignHandler {
    @Override
    //@Transactional(rollbackFor = Exception.class)
    public boolean handle(Context context, ProcessInstance processInstance) {
        //查出当前流程中的变量
        String agree = String.valueOf(context.getProcessService().getProcessVariable("agree",processInstance));
        if(HandleResult.Agree.getCode().equals(agree)) {
            TaskQuery query = context.getTaskService().createTaskQuery();
            query.addTaskState(TaskState.Created).addTaskState(TaskState.Ready).addTaskState(TaskState.Suspended).addTaskState(TaskState.InProgress).addTaskState(TaskState.Reserved);
            query.processInstanceId(processInstance.getId());
            //是否还有未完成的任务
            List<Task> countersignTasks = query.list();
            return countersignTasks.size() == 0;
        }else{
            return true;
        }
    }

    @Override
    public String desc() {
        return "默认会签完成";
    }
}
