package com.asideal.handler;

import cn.hutool.extra.mail.MailUtil;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.process.handler.ReminderHandler;
import org.springframework.stereotype.Component;

@Component
public class EmailReminderHandler implements ReminderHandler {
    @Override
    public void execute(ProcessInstance processInstance, Task task) {
        MailUtil.send("261661487@qq.com","您有新的待办事项",
                "您的待办事项即将过期,请及时处理",false,null);
    }

    @Override
    public String desc() {
        return "向处理人发送邮件提醒";
    }
}
