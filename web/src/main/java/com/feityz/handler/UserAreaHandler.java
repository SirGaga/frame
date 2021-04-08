package com.feityz.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.variable.Variable;
import com.bstek.uflo.process.handler.AssignmentHandler;
import com.bstek.uflo.process.node.TaskNode;
import com.feityz.common.Result;
import com.feityz.system.entity.Dept;
import com.feityz.system.entity.User;
import com.feityz.system.service.IDeptService;
import com.feityz.system.service.IParameterService;
import com.feityz.system.service.IUserService;
import com.feityz.util.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 人员所在部门的所有人员
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
                user.setPassword(MD5Util.getPassword(user.getUserNum(), "123456"));
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
