package com.asideal.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bstek.uflo.model.ProcessDefinition;
import com.bstek.uflo.model.task.Task;
import com.bstek.uflo.process.node.Node;
import com.bstek.uflo.process.node.StartNode;
import com.bstek.uflo.process.node.TaskNode;
import com.bstek.uflo.process.node.UserData;
import com.bstek.uflo.process.security.ComponentAuthority;
import com.bstek.uflo.service.ProcessService;
import com.bstek.uflo.service.TaskService;
import com.asideal.common.Rest;
import com.asideal.system.entity.*;
import com.asideal.system.service.IHisTaskQueryService;
import com.asideal.system.service.ITaskInfoService;
import com.asideal.system.service.IUserService;
import com.asideal.system.vo.*;
import com.asideal.util.SpringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/task")
@Api(tags = "任务接口")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ITaskInfoService taskInfoService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private IHisTaskQueryService hisTaskQueryService;

    @GetMapping("/index")
    @ApiIgnore
    public String index(){
        return "task/index";
    }

    @GetMapping("/history/index")
    @ApiIgnore
    public String history(){
        return "task/history";
    }

    @ApiOperation(value = "当前人员待办事项", notes = "当前人员待办事项")
    @PostMapping("/list")
    @ResponseBody
    @ApiIgnore
    public Rest toDo(UserTaskInput condition, int page, int limit){
        TaskInfo taskInfo = new TaskInfo();
        Long assignee = 0L;
        String userNum = condition.getUserNum();
        if(StringUtils.isEmpty(userNum)){
            assignee = SpringUtils.getLoginUser().getId();
        }else{
            User user = userService.lambdaQuery().eq(User::getUserNum,condition.getUserNum()).one();
            Assert.notNull(user,"流程系统中找不到编号为["+condition.getUserNum()+"]的人员");
            assignee = user.getId();
        }
        taskInfo.setAssignee(assignee.toString());
        taskInfo.setSubject(condition.getSubject());
        if(StringUtils.isNotEmpty(condition.getProcessKey())){
            taskInfo.setProcessKey(condition.getProcessKey());
        }
        IPage<TaskInfo> pageInfo = taskInfoService.getPage(taskInfo,new Page<TaskInfo>(page, limit));
        return Rest.success().setData(pageInfo.getRecords()).setTotal(pageInfo.getTotal());
    }
    @ApiOperation(value = "当前人员待办事项", notes = "当前人员待办事项")
    @PostMapping("/taskList")
    @ResponseBody
    public Rest todoList(@RequestBody UserTaskInput condition){
        return this.toDo(condition,condition.getPage(),condition.getLimit());
    }

    @ApiOperation(value = "获取节点参数", notes = "获取节点参数")
    @GetMapping("/userData/{taskId}")
    @ResponseBody
    public Rest userDate(@PathVariable Long taskId){
        Task task = taskService.getTask(taskId);
        ProcessDefinition process=processService.getProcessById(task.getProcessId());
        TaskNode node=(TaskNode)process.getNode(task.getNodeName());
        List<UserData> userDatas = node.getUserData();
        return Rest.success().setData(userDatas);
    }

    @ApiOperation(value = "完成任务", notes = "完成任务")
    @ResponseBody
    @PostMapping("/complate")
    public Rest complate(@RequestBody TaskInput input){
        Map map = taskInfoService.completeTask(input);
        if(map==null){
            return Rest.success();
        }else {
            return Rest.failure("请传入参数下一步节点(nextNodeName)和下一步处理人(assignee)").setData(map);
        }
    }

    @ApiOperation(value = "获取任务权限", notes = "获取任务权限")
    @ResponseBody
    @GetMapping(value = {"/auth/{taskId}","/auth/{taskId}/{key}"})
    public Rest taskAuth(@PathVariable Long taskId,@PathVariable(required = false) String key){

        return Rest.success().setData(taskInfoService.taskAuth(taskId,key));

    }

    @PostMapping("/rollBack")
    @ResponseBody
    @ApiOperation(value = "退回到上一次节点", notes = "退回到上一次节点")
    @ApiIgnore
    public Rest rollBackPrev(@RequestBody RollBackInput input){
        taskInfoService.rollBack(input);
        return Rest.success();
    }

    @PostMapping("/rollBackStart")
    @ResponseBody
    @ApiOperation(value = "退回到开始节点", notes = "退回到开始节点")
    @ApiIgnore
    public Rest rollBackStart(@RequestBody RollBackInput input){
        taskInfoService.rollBackStart(input);
        return Rest.success();
    }

    @PostMapping("/changeAssignee")
    @ResponseBody
    @ApiIgnore
    @ApiOperation(value = "转办", notes = "转办")
    public Rest changeAssignee(ChangeAssigneeInput input){
        taskInfoService.changeAssignee(input);
        return Rest.success();
    }

    @ApiOperation(value = "通用办理页面", notes = "通用办理页面")
    @GetMapping("/commonHandle")
    @ApiIgnore
    public String commonHandle(long taskId, Model model){
        Task task = taskService.getTask(taskId);

        String url = task.getUrl();

        ProcessDefinition definition = processService.getProcessById(task.getProcessId());
        Node currentNode=definition.getNode(task.getNodeName());

        if(StringUtils.isEmpty(url)){

            url = definition.getStartProcessUrl();
        }

        if(currentNode instanceof TaskNode){
            TaskNode taskNode = (TaskNode) currentNode;
            List<ComponentAuthority> authorities = taskNode.getComponentAuthorities();
            authorities.forEach(x->{
                model.addAttribute(x.getComponent(),x.getAuthority());
            });
        }
        model.addAttribute("rollBack",!(currentNode instanceof StartNode));
        model.addAttribute("url",url);
        model.addAttribute("taskId",taskId);
        model.addAttribute("bizId",task.getBusinessId());
        return "task/handle";
    }

    @ApiOperation(value = "获取下一部必须指定人员的节点", notes = "获取下一部必须指定人员的节点")
    @PostMapping("/nextNode")
    @ResponseBody
    public Rest getNextNodeByTask(@RequestBody GetNextNodeInput input){
        List<NextNodeVo> nextNodes = taskInfoService.getNextNodeByTaskId(input);
        if(CollectionUtil.isEmpty(nextNodes)){
            return Rest.failure("下一部节点没有设置必须指定人员,请按流程流转");
        }else {
            return Rest.success().setData(nextNodes);
        }
    }

    @ApiOperation(value = "获取下一步节点", notes = "获取下一步节点")
    @PostMapping("/nextNodeNormal")
    @ResponseBody
    public Rest getNextNodeNormal(@RequestBody GetNextNodeInput input){
        List<NextNodeVo> nextNodes = taskInfoService.getNextNodeNormal(input);
        if(CollectionUtil.isEmpty(nextNodes)){
            return Rest.failure("下一部节点没有设置必须指定人员,请按流程流转");
        }else {
            return Rest.success().setData(nextNodes);
        }
    }

    @ResponseBody
    @GetMapping("/historyTasks/task/{taskId}")
    @ApiIgnore
    public Rest history(@PathVariable long taskId){
        List<HistoryTaskInfo> tasks =  hisTaskQueryService.findHistoryByTaskId(taskId);
        return Rest.success().setData(tasks);
    }

    @ResponseBody
    @GetMapping("/history/{instanceId}")
    @ApiOperation(value = "获取流转记录", notes = "获取流转记录")
    public Rest historyByInstance(@PathVariable long instanceId){
        List<HistoryTaskInfo> tasks =  hisTaskQueryService.findHistoryByInstanceId(instanceId);
        return Rest.success().setData(tasks);
    }


    @ResponseBody
    @GetMapping("/remove")
    @ApiOperation(value = "根据任务ID删除流程实例", notes = "根据任务ID删除流程实例")
    @ApiIgnore
    public Rest remove(Long taskId){
        taskInfoService.removeProcessByTaskId(taskId);
        return Rest.success();
    }

}
