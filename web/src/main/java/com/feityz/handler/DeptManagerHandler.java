package com.feityz.handler;

import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.AssignmentHandler;
import com.bstek.uflo.process.node.TaskNode;
import com.feityz.system.entity.User;
import com.feityz.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门经理
 */
@Component("deptManager")
public class DeptManagerHandler implements AssignmentHandler {
    @Autowired
    private IUserService userService;
    @Override
    public Collection<String> handle(TaskNode taskNode, ProcessInstance processInstance, Context context) {

        User user = userService.getById(processInstance.getPromoter());

        Long deptId = user.getDept();

        List<User> users = userService.lambdaQuery().eq(User::getDept,deptId).inSql(
                User::getId,"select user_id from re_user_role where role_id = (" +
                            "select id from sys_role where role_num='JL'" +
                            ")"
                ).list();

        List<String> result = users.stream().map(e->e.getId().toString()).collect(Collectors.toList());

        return result;
    }

    @Override
    public String desc() {
        return "获取部门经理";
    }
}
