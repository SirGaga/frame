package com.feityz.listener;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.process.listener.TaskListener;
import com.bstek.uflo.process.node.TaskNode;
import com.feityz.system.entity.User;
import com.feityz.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailTaskListener implements TaskListener {

    @Autowired
    private IUserService userService;

    @Override
    public boolean beforeTaskCreate(Context context, ProcessInstance processInstance, TaskNode node) {
        return false;
    }

    @Override
    public void onTaskCreate(Context context, Task task) {
        Long userId = Long.valueOf(task.getAssignee());
        User user = userService.getById(userId);
        //假设这里有个email
        String emailAddress = "2301881221@qq.com";
    }

    @Override
    public void onTaskComplete(Context context, Task task) {

    }

    @Override
    public String desc() {
        return "给处理人发送邮件";
    }
}
