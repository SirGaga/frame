package com.feityz.system.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.service.ProcessService;
import com.bstek.uflo.service.StartProcessInfo;
import com.bstek.uflo.service.TaskOpinion;
import com.bstek.uflo.service.TaskService;
import com.feityz.system.dao.ProcessInstanceMapper;
import com.feityz.system.entity.User;
import com.feityz.system.service.IProcessInstanceService;
import com.feityz.system.service.IProcessRunService;
import com.feityz.system.service.ITaskInfoService;
import com.feityz.system.service.IUserService;
import com.feityz.system.vo.CancelInput;
import com.feityz.system.vo.GetNextNodeInput;
import com.feityz.system.vo.NextNodeVo;
import com.feityz.system.vo.ProcessStartInput;
import com.feityz.util.SpringUtils;
import exception.BizException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

/**
 * 流程运行实现
 * @author zhangjie
 * @date 2021-04-07
 */
@Service
public class ProcessRunServiceImpl implements IProcessRunService {
    @Resource
    private ProcessService processService;
    @Resource
    private TaskService taskService;
    @Resource
    private ITaskInfoService taskInfoService;
    @Resource
    private IProcessInstanceService processInstanceService;
    @Resource
    private IUserService userService;
    @Resource
    private ProcessInstanceMapper processInstanceMapper;


    @Override
    @Transactional
    public Map start(ProcessStartInput input) {

        //检查表单重复提交
        checkBizId(input.getBizId());
        //分布式调用需要把人员传过来,集成模式从session中取人员
        String promoter = input.getPromoter();
        if (StringUtils.isEmpty(promoter)) {
            promoter = SpringUtils.getLoginUser().getId().toString();
        }else{

            User user =userService.lambdaQuery().eq(User::getUserNum,promoter).one();

            Assert.notNull(user,"系统中找不到编号为["+promoter+"]的人员");

            promoter = user.getId().toString();
        }
        Assert.hasLength(promoter,"登录人员为空");
        Map<String, Object> result = new HashMap<>();
        StartProcessInfo info = new StartProcessInfo();
        info.setBusinessId(input.getBizId());
        info.setPromoter(promoter.toString());
        info.setSubject(input.getSubject());
        //设置流程参数
        Map variables = input.getVariables();
        if(variables==null) {
            variables = new HashMap();
        }
        if(variables.get("users")!=null){
            String users = variables.get("users").toString();
            String userLst = userService.lambdaQuery()
                    .in(User::getUserNum,users.split(",")).list()
                    .stream().map(User::getId).map(String::valueOf)
                    .distinct().collect(joining(","));
            variables.put("users",userLst);
        }
        info.setCompleteStartTask(false);
        input.setVariables(variables);
        /**尝试开启流程start**/
        ProcessInstance instance = processService.startProcessByKey(input.getProcessKey(), info);
        List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId(instance.getId())
                .addAssignee(promoter.toString())
                .list();

        Task currentTask = tasks.get(0);

        GetNextNodeInput nextInput = new GetNextNodeInput();
        nextInput.setVariables(variables);
        nextInput.setTaskId(currentTask.getId());

        //获取下一步必须要指定人员的节点,否则直接完成该任务
        List<NextNodeVo> nextNodes = taskInfoService.getNextNodeByTaskId(nextInput);

        //这里指下一步有节点需要指定人员
        if (nextNodes != null && nextNodes.size() > 0) {
            //这里表示第二次提交的时候是否把人员带了过来(指定了人员)
            if(StringUtils.isNotEmpty(input.getAssignee())) {
                String nextNodeName = nextNodes.get(0).getNextNodeName();

                if(nextNodes.size()>1){
                    Assert.hasLength(input.getNextNodeName(),"下一步中有多个节点,需要指定");

                    boolean hasThisNode= nextNodes.stream().anyMatch(e->e.getNextNodeName().equals(input.getNextNodeName()));

                    Assert.isTrue(hasThisNode,"下一步需要指定的节点中不包括["+input.getNextNodeName()+"]");

                    nextNodeName = input.getNextNodeName();

                }

                List<String> userIds = userService.lambdaQuery().in(User::getUserNum, (Object) input.getAssignee().split(","))
                        .list().stream().map(e->e.getId().toString()).collect(Collectors.toList());
                Assert.isTrue(userIds.size()>0,"根据人员编号找不到对应的人员");
                String[] userArray = ArrayUtil.toArray(userIds,String.class);
                List<User> users = userService.lambdaQuery().in(User::getId,userIds).list();
                nextInput.setAssignee(users.stream().map(e->e.getUserNum()+"_"+e.getUserName()).collect(joining(",")));
                taskInfoService.setNextAssignee(nextInput);
                taskService.start(currentTask.getId());
                taskService.saveTaskAppointor(currentTask.getId(),
                        userArray, nextNodeName);
                taskService.complete(currentTask.getId(),new TaskOpinion(input.getOpinion(),input.getOperation()));
            }else {
                //第一次提交的时候发现下一步有需要指定的人员,先将人员和节点返回出去,并将此次的流程实例关闭,等待下一次提交
                processService.deleteProcessInstance(instance);
                //历史表一并删除
                processInstanceService.deleteHisInstance(instance.getId());
                result.put("nextNode", nextNodes);
            }
        } else {
            //下一步没有节点需要指定人员的话直接完成
            taskInfoService.setNextAssignee(nextInput);
            taskService.start(currentTask.getId());
            taskService.complete(currentTask.getId(),new TaskOpinion(input.getOpinion(),input.getOperation()));
            result.put("nextNode", new ArrayList<>());
        }
        result.put("instanceId",instance.getId());
        return result;
    }

    @Transactional
    @Override
    public void remove(long id) {
        processService.deleteProcess(id);
    }

    @Transactional
    @Override
    public void cancel(CancelInput input){
        Task task = taskService.getTask(input.getTaskId());

        ProcessInstance instance = processService.getProcessInstanceById(task.getProcessInstanceId());
        processService.deleteProcessInstance(instance);
    }

    private void checkUsers(String assignee){

        String[] users = assignee.split(",");

        for (String userid:users) {
            User user = userService.lambdaQuery().eq(User::getUserNum,userid).one();

            Assert.notNull(user,"找不到编号为["+userid+"]的用户");
        }

    }

    private boolean checkBizId(String bizId){

        int instanceCount = processInstanceMapper.selectInstanceByBizId(bizId);

        int hisInstanceCount = processInstanceMapper.selectHisInstanceByBizId(bizId);

        if(instanceCount>0 || hisInstanceCount>0){
            throw new BizException("表单ID["+bizId+"]禁止重复提交");
        }

        return true;
    }

    @Override
    public Object getVaribale(long instancesId){

        return processService.getProcessVariable("dept", instancesId);
    }

}
