package com.asideal.handler;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.process.listener.TaskListener;
import com.bstek.uflo.process.node.TaskNode;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AutoComplate implements TaskListener {

    @Override
    public boolean beforeTaskCreate(Context context, ProcessInstance processInstance, TaskNode node) {
        return false;
    }

    @Override
    public void onTaskCreate(Context context, Task task) {
        ProcessInstance instance = context.getProcessService()
                .getProcessInstanceById(task.getProcessInstanceId());
        //这里表示是走了分支的流程实例
        if(instance.getId()!=instance.getRootId()){
            List<ProcessInstance> instances = context.getProcessService()
                    .createProcessInstanceQuery()
                    .rootId(instance.getRootId())
                    .list().stream().filter(e->e.getId()!=instance.getId()
                     && e.getId()!=e.getRootId()
                    )
                    .collect(Collectors.toList());
            if(instances.size()==0){
                //表示其他分支的流程实例已经完成
                context.getTaskService().start(task.getId());
                context.getTaskService().complete(task.getId());
            }
        }

    }

    @Override
    public void onTaskComplete(Context context, Task task) {

    }

    @Override
    public String desc() {
        return "安环自动完成确认";
    }
}
