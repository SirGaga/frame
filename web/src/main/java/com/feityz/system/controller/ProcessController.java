package com.feityz.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bstek.uflo.model.HistoryProcessInstance;
import com.bstek.uflo.model.ProcessDefinition;
import com.bstek.uflo.service.HistoryService;
import com.bstek.uflo.service.ProcessService;
import com.bstek.uflo.service.TaskService;
import com.feityz.common.Rest;
import com.feityz.system.entity.ProcessDefinitionVo;
import com.feityz.system.service.IProcessDefinitionService;
import com.feityz.system.service.IProcessInstanceService;
import com.feityz.system.service.IProcessRunService;
import com.feityz.system.vo.CancelInput;
import com.feityz.system.vo.ProcessStartInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/process")
@Api(tags = "流程接口")
public class ProcessController {
    @Resource
    private ProcessService processService;
    @Resource
    private TaskService taskService;
    @Resource
    private HistoryService historyService;
    @Resource
    private IProcessRunService processRunService;
    @Resource
    private IProcessDefinitionService definitionService;
    @Resource
    private IProcessInstanceService processInstanceService;

    @GetMapping("/index")
    //@RequiresPermissions("process:index")
    @ApiIgnore
    public String index(Model model) {
        return "process/index";
    }

    @ResponseBody
    @GetMapping("/list")
    //@RequiresPermissions("process:list")
    @ApiIgnore
    public Rest list(ProcessDefinitionVo condition, int page, int limit) {
        //只查最新版本
        condition.setLastVersion(true);
        IPage<ProcessDefinitionVo> pageInfo =
                definitionService.definitionPage(new Page<ProcessDefinitionVo>(page, limit),condition);
        return Rest.success().setData(pageInfo.getRecords()).setTotal(pageInfo.getTotal());
    }

    @DeleteMapping("/delete")
    //@RequiresPermissions("process:delete")
    @ResponseBody
    @ApiIgnore
    public Rest delete(Long id) {
        ProcessDefinition process = processService.getProcessById(id);
        ProcessDefinition processes = processService.getProcessByKey(process.getKey());
        processRunService.remove(id);
        return Rest.success();
    }

    @ApiOperation(value = "发起流程", notes = "发起流程")
    @ResponseBody
    @PostMapping("/start")
    public Rest start(@RequestBody ProcessStartInput input){
        Map result = processRunService.start(input);
        List nextNodeVos = (List) result.get("nextNode");
        if(nextNodeVos!=null && nextNodeVos.size()>0){
            return Rest.failure("请指定节点和人员,并重新调用start接口").setData(nextNodeVos);
        }
        return Rest.success().setData(result);
    }

    @ResponseBody
    @PostMapping("/cancel")
    @ApiOperation(value = "根据任务id撤销流程", notes = "根据任务id撤销流程")
    @ApiIgnore
    public Rest cancel(@RequestBody CancelInput input){
        processRunService.cancel(input);

        return Rest.success();
    }

    @ApiOperation(value = "查看流程是否结束", notes = "流程监控(发起人)")
    @GetMapping("/end/{instanceId}")
    @ResponseBody
    public Rest isEnd(@PathVariable Long instanceId){
        HistoryProcessInstance instance = historyService.getHistoryProcessInstance(instanceId);
        boolean end = instance.getEndDate() != null;
        if(end){
            return Rest.success().setMessage("流程已结束");
        }else{
            return Rest.failure().setMessage("流程未结束");
        }
    }

    @ApiOperation(value = "根据实例Id获取当前流程节点和处理人", notes = "根据实例Id获取当前流程节点和处理人")
    @GetMapping("/current/{instanceId}")
    @ResponseBody
    public Rest getCurrentNode(@PathVariable Long instanceId){
        return Rest.success().setData(processInstanceService.getCurrent(instanceId));
    }
}
