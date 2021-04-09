package com.asideal.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bstek.uflo.model.HistoryProcessInstance;
import com.bstek.uflo.model.ProcessDefinition;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.model.task.TaskState;
import com.bstek.uflo.process.node.Node;
import com.bstek.uflo.process.node.TaskNode;
import com.bstek.uflo.process.node.UserData;
import com.bstek.uflo.service.HistoryService;
import com.bstek.uflo.service.ProcessService;
import com.bstek.uflo.service.TaskService;
import com.asideal.system.dao.ProcessInstanceMapper;
import com.asideal.system.entity.ProcessInstanceVo;
import com.asideal.system.entity.User;
import com.asideal.system.service.IProcessInstanceService;
import com.asideal.system.service.IUserService;
import com.asideal.system.vo.CurrentNodeVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangjie
 * @date 2021-04-07
 */
@Service
public class ProcessInstanceServiceImpl implements IProcessInstanceService {

    @Resource
    private ProcessService processService;
    @Resource
    private ProcessInstanceMapper processInstanceMapper;
    @Resource
    private HistoryService historyService;
    @Resource
    private TaskService taskService;
    @Resource
    private IUserService userService;
    @Override
    public IPage<ProcessInstanceVo> pageByAssignee(ProcessInstanceVo condition, Page<ProcessInstanceVo> page) {
        page.setRecords(processInstanceMapper.pageByAssignee(page,condition));
        return page;
    }

    @Override
    public IPage<ProcessInstanceVo> pageByPromoter(ProcessInstanceVo condition, Page<ProcessInstanceVo> page) {
        page.setRecords(processInstanceMapper.pageByPromoter(page,condition));
        return page;
    }

    @Override
    public IPage<ProcessInstanceVo> pageAll(ProcessInstanceVo condition, Page<ProcessInstanceVo> page) {
        page.setRecords(processInstanceMapper.pageAll(page,condition));
        return page;
    }

    @Override
    public void deleteHisInstance(Long instanceId) {
        processInstanceMapper.deleteHisInstance(instanceId);
    }

    @Override
    public Map<String,String> getInstanceParams(long instanceId){

        ProcessInstance instance = processService.getProcessInstanceById(instanceId);

        String url;
        String bizId;

        if(instance != null){
            ProcessDefinition definition = processService.getProcessById(instance.getProcessId());

            Node node = definition.getNode(instance.getCurrentNode());

            url = definition.getStartProcessUrl();

            if(node instanceof TaskNode){
                TaskNode taskNode = (TaskNode) node;
                if(StringUtils.isNotEmpty(taskNode.getUrl())){
                    url = taskNode.getUrl();
                }
            }
            bizId = instance.getBusinessId();
        }else{
            HistoryProcessInstance historyProcessInstance = historyService.getHistoryProcessInstance(instanceId);
            //当前实例如果查不到,那表示该实例已经结束
            ProcessDefinition definition = processService.getProcessById(historyProcessInstance.getProcessId());

            url = definition.getStartProcessUrl();
            bizId = historyProcessInstance.getBusinessId();
        }

        Map<String,String> result = new HashMap<>(16);
        result.put("url",url);
        result.put("bizId",bizId);
        return result;

    }

    @Override
    public List<CurrentNodeVo> getCurrent(Long instanceId){
        List<CurrentNodeVo> nodes = new ArrayList<>();


        ProcessInstance instance = processService.getProcessInstanceById(instanceId);

        List<Task> tasks = taskService.createTaskQuery().nodeName(instance.getCurrentNode())
                .processInstanceId(instanceId)
                .addTaskState(TaskState.Created)
                .addTaskState(TaskState.Ready)
                .addTaskState(TaskState.InProgress)
                .list();
        //查出来可能会有多个Task
        for (Task task: tasks) {
            TaskNode taskNode = (TaskNode) processService.getProcessById(instance.getProcessId()).getNode(task.getNodeName());
            //任务对应的节点信息
            List<UserData> userData = taskNode.getUserData();
            CurrentNodeVo node = new CurrentNodeVo();
            node.setNodeName(task.getNodeName());
            node.setUserData(userData);
            String userIds = task.getOwner();
            if(StringUtils.isNotEmpty(userIds)){
                List<User> users =userService.lambdaQuery().in(User::getId, (Object) userIds.split(",")).list();
                node.setAssignee(users);
            }
            nodes.add(node);
        }
        return nodes;
    }
}
