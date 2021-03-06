package com.asideal.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bstek.uflo.command.CommandService;
import com.bstek.uflo.command.impl.SaveProcessInstanceVariablesCommand;
import com.bstek.uflo.expr.ExpressionContext;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.model.task.TaskType;
import com.bstek.uflo.model.variable.Variable;
import com.bstek.uflo.process.node.Node;
import com.bstek.uflo.process.node.StartNode;
import com.bstek.uflo.process.node.TaskNode;
import com.bstek.uflo.process.security.ComponentAuthority;
import com.bstek.uflo.service.ProcessService;
import com.bstek.uflo.service.TaskOpinion;
import com.bstek.uflo.service.TaskService;
import com.asideal.system.dao.TaskInfoMapper;
import com.asideal.system.entity.Dept;
import com.asideal.system.entity.TaskInfo;
import com.asideal.system.entity.User;
import com.asideal.system.service.IDeptService;
import com.asideal.system.service.ITaskInfoService;
import com.asideal.system.service.IUserService;
import com.asideal.system.vo.*;
import com.asideal.util.SpringUtils;
import exception.BizException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskInfoServiceImpl extends ServiceImpl<TaskInfoMapper, TaskInfo> implements ITaskInfoService {
    @Resource
    private TaskService taskService;
    @Resource
    private IUserService userService;
    @Resource
    private ProcessService processService;
    @Resource
    private CommandService commandService;
    @Resource
    private ExpressionContext expressionContext;
    @Resource
    private IDeptService deptService;

    @Override
    public IPage<TaskInfo> getPage(TaskInfo condition, Page<TaskInfo> page) {
        page.setRecords(baseMapper.listPage(page, condition));
        return page;
    }


    @Override
    @Transactional
    public Map completeTask(TaskInput input){
        //?????????????????????
        if(input.getVariables()!=null) {
            taskService.setTaskVariable(input.getVariables(), input.getTaskId());
        }
        //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        List<String> nodeNames = taskService.getAvaliableAppointAssigneeTaskNodes(input.getTaskId());
        //Task task = taskService.getTask(input.getTaskId());

        if(nodeNames.size()==1){
            input.setNextNodeName(nodeNames.get(0));
        }

        if(nodeNames.size()>0 && (StringUtils.isEmpty(input.getNextNodeName())
            ||StringUtils.isEmpty(input.getAssignee()))){
            Map<String, List<User>> resultMap = new HashMap<>(16);
            for (String nodeName:nodeNames) {
                //??????????????????????????????????????????,?????????????????????,????????????????????????????????????
                /*List<Task> tasks = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId())
                        .nodeName(nodeName).addOrderDesc("createDate").list();
                if(tasks.size()>0){
                    Task firstTask = tasks.get(0);
                    if(StringUtils.isEmpty(firstTask.getAssignee())){
                        throw new IllegalArgumentException("???????????????????????????");
                    }

                    GetNextNodeInput nextNodeInput = new GetNextNodeInput();
                    nextNodeInput.setTaskId(input.getTaskId());
                    nextNodeInput.setVariables(input.getVariables());
                    User firstAssignee = userService.getById(firstTask.getAssignee());
                    nextNodeInput.setAssignee(firstAssignee.getUserNum()+"_"+firstAssignee.getUserName());
                    setNextAssignee(nextNodeInput);

                    TaskOpinion opinion = new TaskOpinion(input.getOpinion());
                    opinion.setOperation(input.getOperation());
                    taskService.start(input.getTaskId());
                    //???????????????????????????????????????????????????????????????
                    if (StringUtils.isNotEmpty(input.getNextNodeName()) && input.getAssignee() != null) {
                        taskService.saveTaskAppointor(input.getTaskId(),
                                firstTask.getAssignee(),nodeName);
                    }
                    taskService.complete(input.getTaskId(),input.getVariables(),opinion);
                    return null;
                }else {*/
                    List<String> userIds = taskService.getTaskNodeAssignees(input.getTaskId(),nodeName);
                    List<User> users = userService.lambdaQuery().in(User::getId,userIds).list();
                    resultMap.put(nodeName, users);
                /*}*/
            }
            return resultMap;
        }else {
            completeSingle(input);
            return null;
        }
    }

    private void completeSingle(TaskInput input){
        Task task = taskService.getTask(input.getTaskId());
        if(task.getType().equals(TaskType.Participative)){
            //?????????????????????
            String appointor = input.getAppointor();
            if(appointor == null){
                appointor = SpringUtils.getLoginUser().getId().toString();
            }else{
                User user = userService.lambdaQuery().eq(User::getUserNum,appointor).one();
                Assert.notNull(user,"???????????????????????????");
                appointor = user.getId().toString();
            }
            String[] owners = task.getOwner().split(",");

            Assert.isTrue(Arrays.asList(owners).contains(appointor),"???????????????????????????["+appointor+"]");

            taskService.claim(input.getTaskId(),appointor);

        }
        TaskOpinion opinion = new TaskOpinion(input.getOpinion());
        opinion.setOperation(input.getOperation());

        GetNextNodeInput nextNodeInput = new GetNextNodeInput();
        nextNodeInput.setTaskId(input.getTaskId());
        nextNodeInput.setVariables(input.getVariables());
        setNextAssignee(nextNodeInput);
        //????????????
        taskService.start(input.getTaskId());
        if (StringUtils.isNotEmpty(input.getNextNodeName()) && input.getAssignee() != null) {
            String[] assignee = input.getAssignee().split(",");
            List<String> users = userService.lambdaQuery().in(User::getUserNum,assignee)
                    .list().stream().map(e->e.getId().toString()).collect(Collectors.toList());
            String[] assigneeIds = ArrayUtil.toArray(users,String.class);

            taskService.saveTaskAppointor(input.getTaskId(),
                    assigneeIds,input.getNextNodeName());
        }

        taskService.complete(input.getTaskId(),input.getVariables(), opinion);

    }

    @Override
    public List<NextNodeVo> getNextNodeByTaskId(GetNextNodeInput input){
        Task currentTask = taskService.getTask(input.getTaskId());
        //?????????????????????
        taskService.setTaskVariable(input.getVariables(),input.getTaskId());
        List<NextNodeVo> nextNodes = new ArrayList<>();
        List<String> nodeNames = taskService.getAvaliableAppointAssigneeTaskNodes(input.getTaskId());
        if(nodeNames.size()>0){
            for (String nodeName:nodeNames) {
                NextNodeVo vo = new NextNodeVo();
                vo.setNextNodeName(nodeName);
                /*List<Task> tasks = taskService.createTaskQuery().processInstanceId(currentTask.getProcessInstanceId())
                        .nodeName(nodeName).addOrderDesc("createDate").list();
                if(tasks.size()>0) {
                    Task firstTask = tasks.get(0);
                    if (StringUtils.isNotEmpty(firstTask.getAssignee())) {
                        User user = userService.getById(firstTask.getAssignee());
                        vo.setAssignee(new ArrayList<User>(){{
                            add(user);
                        }});
                    }
                }else {*/
                    List<String> userIds = taskService.getTaskNodeAssignees(currentTask.getId(), nodeName);
                    if (userIds.size() == 0) {
                        throw new BizException("????????????????????????,?????????????????????");
                    }
                    List<User> users = userService.lambdaQuery().in(User::getId, userIds).list();
                    vo.setAssignee(users);
                    nextNodes.add(vo);
                /*}*/
            }
        }
        return nextNodes;
    }


    @Override
    @Transactional
    public List<NextNodeVo> getNextNodeNormal(GetNextNodeInput input){
        Task currentTask = taskService.getTask(input.getTaskId());
        //?????????????????????
        taskService.setTaskVariable(input.getVariables(),input.getTaskId());
        List<NextNodeVo> nextNodes = new ArrayList<>();
        List<String> nodeNames = taskService.getAppointAssigneeTaskNodes(input.getTaskId());
        if(nodeNames.size()>0){
            for (String nodeName:nodeNames) {
                NextNodeVo vo = new NextNodeVo();
                vo.setNextNodeName(nodeName);

                List<String> userIds = taskService.getTaskNodeAssignees(currentTask.getId(), nodeName);
                if (userIds.size() == 0) {
                    throw new BizException("????????????????????????,?????????????????????");
                }
                List<User> users = userService.lambdaQuery().in(User::getId, userIds).list();
                for (User user: users) {
                    Dept dept = deptService.getById(user.getDept());
                    if(dept!=null){
                        user.setDeptName(dept.getDeptName());
                        user.setDeptNum(dept.getDeptNum());
                    }
                }
                vo.setAssignee(users);
                nextNodes.add(vo);
            }
        }
        return nextNodes;
    }

    @Override
    @Transactional
    public void rollBack(RollBackInput input){
        Task task = taskService.getTask(input.getTaskId());
        // ????????????????????? ??????????????????????????????,???????????????A-B-A???????????????
        // String prevTask = task.getPrevTask();
        //???????????????????????????????????????
        List<TaskInfo> tasks = lambdaQuery().eq(TaskInfo::getProcessInstanceId,task.getProcessInstanceId())
                .eq(TaskInfo::getNodeName,task.getNodeName()).orderByAsc(TaskInfo::getCreateDate).list();
        taskService.rollback(input.getTaskId(),tasks.get(0).getPrevTask(),input.getVariables(),new TaskOpinion(input.getOpinion()));
    }

    @Override
    @Transactional
    public void rollBackStart(RollBackInput input) {
        Task task = taskService.getTask(input.getTaskId());
        StartNode startnode = processService.getProcessById(task.getProcessId()).getStartNode();
        taskService.rollback(task.getId(),startnode.getName(),input.getVariables(),new TaskOpinion(input.getOpinion()));
    }

    @Override
    @Transactional
    public void removeProcessByTaskId(long taskId){
        Task task = taskService.getTask(taskId);
        ProcessInstance instance = processService.getProcessInstanceById(task.getProcessInstanceId());
        processService.deleteProcessInstance(instance);
    }

    @Override
    @Transactional
    public void changeAssignee(ChangeAssigneeInput input) {
        taskService.changeTaskAssignee(input.getTaskId(),input.getAssignee().toString());
    }

    @Override
    @Transactional
    public void reject(RollBackInput input){
        Task task = taskService.getTask(input.getTaskId());
        ProcessInstance processInstance=processService.getProcessInstanceById(task.getProcessInstanceId());
        expressionContext.addContextVariables(processInstance, input.getVariables());
        commandService.executeCommand(new SaveProcessInstanceVariablesCommand(processInstance, input.getVariables()));
        List<String> nodeNames = taskService.getAvaliableAppointAssigneeTaskNodes(input.getTaskId());
        if(nodeNames.size()>0){
            throw new BizException("?????????????????????????????????");
        }
        //??????????????????????????????
        String nodeName = nodeNames.get(0);
        taskService.rollback(task,nodeName,input.getVariables(),new TaskOpinion(input.getOpinion()));
    }

    @Override
    public List<ComponentAuthority> taskAuth(Long taskId,String key){
        Task task = taskService.getTask(taskId);

        Node node = processService.getProcessById(task.getProcessId()).getNode(task.getNodeName());

        List<ComponentAuthority> componentAuthorities = new ArrayList<>();

        String branchName = "";
        if(StringUtils.isNotEmpty(key)){
            List<Variable> variables = processService.createProcessVariableQuery().processInstanceId(task.getProcessInstanceId()).
                    key(key).list();
            cn.hutool.core.lang.Assert.isTrue(CollectionUtil.isNotEmpty(variables),"???????????????");

            branchName = variables.get(0).getValue().toString();

        }

        if(node instanceof TaskNode){

            TaskNode taskNode = (TaskNode) node;
            componentAuthorities = taskNode.getComponentAuthorities();
            String finalBranchName = branchName;
            componentAuthorities.forEach(e->{
                e.setBranchName(finalBranchName);
                e.setTaskId(taskId);
            });
        }

        return componentAuthorities;
    }

    @Override
    public void setNextAssignee(GetNextNodeInput input){
        List<NextNodeVo> ls = getNextNodeNormal(input);
        String assignees = "";
        if(ls==null || ls.size()==0){
            return;
        }
        if(StringUtils.isNotEmpty(input.getAssignee())){
            assignees = input.getAssignee();
        }else{
            assignees= ls.get(0).getAssignee().stream().map(e->e.getUserNum()+"_"+e.getUserName()).collect(Collectors.joining(","));
        }
        if(StringUtils.isNotEmpty(assignees)){
            taskService.setNextAssignee(assignees,input.getTaskId());
        }

    }
}
