package com.feityz;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bstek.uflo.command.CommandService;
import com.bstek.uflo.command.impl.CompleteTaskCommand;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.service.ProcessService;
import com.bstek.uflo.service.StartProcessInfo;
import com.bstek.uflo.service.TaskService;
import com.feityz.common.Result;
import com.feityz.config.EmailConfig;
import com.feityz.system.entity.User;
import com.feityz.system.entity.UserAndRoles;
import com.feityz.system.service.IParameterService;
import com.feityz.system.service.IProcessRunService;
import com.feityz.system.service.IUserAndRoleService;
import com.feityz.system.service.IUserService;
import com.feityz.util.MD5Util;
import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.spring5.context.SpringContextUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@SpringBootTest
public class ProcessTest {
    @Autowired
    private ProcessService processService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private IUserAndRoleService userAndRoleService;
    @Autowired
    private IProcessRunService processRunService;
    @Autowired
    private IUserService userService;
    //流程启动
    //@Test
    public void start() {
        StartProcessInfo bizForm = new StartProcessInfo("孔庆麟");
        bizForm.setBusinessId("1000");
        bizForm.setSubject("孔庆麟请假");
        Map var = new HashMap();
        var.put("dayCount", 3);
        bizForm.setVariables(var);
        ProcessInstance instance = processService.startProcessByKey("leave", bizForm);

        System.out.println("当前任务节点id：" + instance.getCurrentTask());
    }

    //@Test
    @Transactional
    public void complate() {
        taskService.start(3308);
        taskService.complete(3308);
        /*Task task = taskService.getTask(2107);
        commandService.executeCommand(new CompleteTaskCommand())*/
    }

    //@Test
    public void getNextNode() {

        //processService.getProcessInstanceById(101).getCurrentTask();

        List<String> taskName = taskService.getAvaliableAppointAssigneeTaskNodes(162);

        for (String name : taskName) {
            System.out.printf("节点:" + name);

            taskService.getTask(1).getTaskParticipators();
        }

    }

    //@Test
    public void testLamda() {
        List<String> userIds = userAndRoleService.lambdaQuery().eq(UserAndRoles::getRoleId, 1)
                .list().stream().map(e -> e.getUserId().toString()).collect(Collectors.toList());
        System.out.println("=========================");
    }

    @Test
    public void emailTest() {
        MailAccount account = new MailAccount();
        account.setHost("yzmail.feg.cn");
        account.setPort(587);
        account.setAuth(true);
        account.setStarttlsEnable(true);
        account.setSslEnable(true);
        account.setFrom("noticeserver.feityz@yz.feg.cn");
        account.setUser("noticeserver.feityz@yz.feg.cn");
        account.setPass("abc.2016");

        String result =
                MailUtil.send(account, CollUtil.newArrayList("261661487@qq.com"),
                        "测试", "邮件来自Hutool测试", false);
        System.out.printf(result);
    }

    //@Test
    //表达式测试
    /*public static void main(String[] args) {

        JexlContext jexlContext = new MapContext();
        String expression="role=='1'?'普通员工':role=='2'?'部门主管':'其他'";

        jexlContext.set("role", 2);

        Expression e = (Expression) new JexlEngine().createExpression(expression);
        String num = (String) e.evaluate(jexlContext);
        System.out.println(num);
    }*/

    /*public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        Map parms = new HashMap();
        parms.put("id","123321");
        try {
            ResponseEntity result = restTemplate.postForEntity("http://10.17.241.34:8080/leave/leave",parms,Map.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    /*@Test
    @Transactional
    public void testTrans() {
        StartProcessInfo info = new StartProcessInfo();
        info.setBusinessId("1");
        info.setPromoter("admin");
        processService.startProcessByKey("leave", info);

        if (true) {
            throw new RuntimeException("回滚测试");
        }
    }

    public static void main(String[] args) {
        String result =
                MailUtil.send(CollUtil.newArrayList("2301881221@qq.com"),
                        "测试", "邮件来自Hutool测试", false);
        System.out.printf(result);
    }
*/
    @Autowired
    private EmailConfig emailConfig;
    @Test
    public void sendEmail() {
        emailConfig.sendEmail("测试11111","测试标题22222","261661487@qq.com");
        System.out.println("测试成功");
        Map map = new HashMap();

    }

    @Autowired
    private IParameterService parameterService;

    @Test
    public void GetDutySupervisor() {
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
                user.setPassword(MD5Util.getPassword(user.getUserNum(), "123456"));
                userService.save(user);
            }
            users.add(user.getId().toString());
        }
        System.out.println(users);
    }
}


