package com.feityz.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bstek.uflo.model.ProcessDefinition;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.process.node.Node;
import com.bstek.uflo.process.node.StartNode;
import com.bstek.uflo.process.node.TaskNode;
import com.bstek.uflo.process.node.UserData;
import com.bstek.uflo.service.ProcessService;
import com.bstek.uflo.service.TaskService;
import com.feityz.system.dao.HisTaskQueryMapper;
import com.feityz.system.entity.HistoryTaskInfo;
import com.feityz.system.service.IHisTaskQueryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class HisTaskQueryServiceImpl extends ServiceImpl<HisTaskQueryMapper, HistoryTaskInfo>
        implements IHisTaskQueryService {
    @Resource
    private ProcessService processService;
    @Resource
    private TaskService taskService;

    @Override
    public List<HistoryTaskInfo> findHistoryByTaskId(Long taskId) {
        Task task = taskService.getTask(taskId);
        return baseMapper.findHistoryTask(task.getProcessInstanceId());
    }

    @Override
    public List<HistoryTaskInfo> findHistoryByInstanceId(Long instanceId) {
        List<HistoryTaskInfo> taskInfos = new ArrayList<>();
        taskInfos = baseMapper.findHistoryTask(instanceId);
        if(taskInfos!=null && taskInfos.size()>0){
            for (HistoryTaskInfo taskInfo: taskInfos) {
                ProcessDefinition process=processService.getProcessById(taskInfo.getProcessId());
                Node node = process.getNode(taskInfo.getNodeName());
                if(node instanceof TaskNode){
                    TaskNode taskNode=(TaskNode)process.getNode(taskInfo.getNodeName());
                    List<UserData> userDatas = taskNode.getUserData();
                    taskInfo.setUserData(userDatas);
                }
                if(node instanceof StartNode){
                    UserData userData = new UserData("step","1");
                    List<UserData> userDatas = new ArrayList<UserData>(){{
                        add(userData);
                    }};
                    taskInfo.setUserData(userDatas);
                }
            }
        }
        return taskInfos;
    }

    @Override
    public IPage<HistoryTaskInfo> getPage(HistoryTaskInfo condition, Page<HistoryTaskInfo> page) {
        page.setRecords(baseMapper.listPage(page, condition));
        return page;
    }
}
