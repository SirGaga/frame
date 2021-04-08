package com.feityz.handler;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.handler.AssignmentHandler;
import com.bstek.uflo.process.node.TaskNode;
import com.feityz.common.Result;
import com.feityz.system.entity.User;
import com.feityz.system.service.IParameterService;
import com.feityz.system.service.IUserService;
import com.feityz.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *  获取值班主任
 * @author zhangjie
 * @date 2021-04-08
 */
@Component("dutySupervisor")
public class DutySupervisorHandler implements AssignmentHandler {
    @Autowired
    private IUserService userService;
    @Autowired
    private IParameterService parameterService;

    @Override
    public Collection<String> handle(TaskNode taskNode, ProcessInstance processInstance, Context context) {
        RestTemplate template = new RestTemplate();

        String url = parameterService.getByCode("LEADER_BY_DUTY").getValue();

        List result = template.getForObject(url, Result.class).getData();

        Assert.notEmpty(result,"值班主任人员为空");

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
        return "获取风险研判值班主任";
    }
}
