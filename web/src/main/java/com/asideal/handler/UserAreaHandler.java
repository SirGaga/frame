package com.asideal.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.variable.Variable;
import com.bstek.uflo.process.handler.AssignmentHandler;
import com.bstek.uflo.process.node.TaskNode;
import com.asideal.common.Result;
import com.asideal.system.entity.User;
import com.asideal.system.service.IDeptService;
import com.asideal.system.service.IParameterService;
import com.asideal.system.service.IUserService;
import com.asideal.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 人员所在部门的所有人员
 * @author zhangjie
 */
@Component("userArea")
public class UserAreaHandler implements AssignmentHandler {
    @Autowired
    private IUserService userService;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IParameterService parameterService;

    @Override
    public Collection<String> handle(TaskNode taskNode, ProcessInstance processInstance, Context context) {
        RestTemplate template = new RestTemplate();
        
        List<Variable> variables = context.getProcessService().createProcessVariableQuery().processInstanceId(processInstance.getId()).key("area").list();

        Assert.isTrue(CollectionUtil.isNotEmpty(variables),"辖区变量为空");

        String area = variables.get(0).getValue().toString();

        String url = parameterService.getByCode("LEADER_BY_AREA").getValue()+"?areas="+area;

        List result = template.getForObject(url,Result.class).getData();

        Assert.notEmpty(result,"辖区"+area+"领班人员为空");

        List<String> users = new ArrayList<>();

        for (Object o : result) {
            JSONObject obj = JSONUtil.parseObj(o);
            String userNum = obj.getStr("userName");
            String userName = obj.getStr("name");


            User user = userService.lambdaQuery().eq(User::getUserNum,userNum).one();
            if(user == null){
                user = new User();
                user.setUserName(userName);
                user.setUserNum(userNum);
                user.setRoles("");
                user.setPassword(Md5Util.getPassword(user.getUserNum(), "123456"));
                userService.save(user);
            }
            users.add(user.getId().toString());
        }
        return users;
    }

    @Override
    public String desc() {
        return "获取辖区领班";
    }
}
