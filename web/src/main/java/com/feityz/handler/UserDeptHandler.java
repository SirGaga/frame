package com.feityz.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.variable.Variable;
import com.bstek.uflo.process.handler.AssignmentHandler;
import com.bstek.uflo.process.node.TaskNode;
import com.bstek.uflo.service.ProcessService;
import com.feityz.system.entity.Dept;
import com.feityz.system.entity.User;
import com.feityz.system.service.IDeptService;
import com.feityz.system.service.IParameterService;
import com.feityz.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 人员所在部门的所有人员
 */
@Component("userDept")
public class UserDeptHandler implements AssignmentHandler {
    @Autowired
    private IUserService userService;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IParameterService parameterService;

    @Override
    public Collection<String> handle(TaskNode taskNode, ProcessInstance processInstance, Context context) {

        String shefRectifyRole = parameterService.getByCode("SHEF_RECTIFY_ROLE").getValue();

        List result = new ArrayList();

        List<Variable> variables = context.getProcessService().createProcessVariableQuery().processInstanceId(processInstance.getId()).key("dept").list();

        Assert.isTrue(CollectionUtil.isNotEmpty(variables),"部门变量为空");

        String deptsStr = variables.get(0).getValue().toString();

        //获取流程中的部门,部门可能是个数组
        String[] depts = deptsStr.split(",");
        List<Dept> deptLst = new ArrayList<>();
        for (String deptNum:depts) {
            Dept dept = deptService.lambdaQuery().eq(Dept::getDeptNum,deptNum).one();
            Assert.notNull(dept,"流程引擎找不到编号为"+deptNum+"的部门");
            deptLst.add(dept);
        }

        List<User> users = userService.lambdaQuery()
                .in(User::getDept, deptLst.stream().map(e -> e.getId()).collect(Collectors.toList()))
                .inSql(User::getId,"select user_id from re_user_role where role_id in " +
                                   "(select id from sys_role " +
                                   "where role_num='"+shefRectifyRole+"')")
                .list();

        result = users.stream().map(e->e.getId().toString()).collect(Collectors.toList());
        Assert.notEmpty(result, Arrays.toString(depts) + "部门下没有整改人员,请检查人员配置");
        return result;

    }

    @Override
    public String desc() {
        return "获取部门所有人";
    }
}
