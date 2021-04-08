package com.feityz.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bstek.uflo.service.HistoryService;
import com.bstek.uflo.service.ProcessService;
import com.feityz.common.Rest;
import com.feityz.system.entity.ProcessInstanceVo;
import com.feityz.system.input.InstanceInput;
import com.feityz.system.service.IProcessInstanceService;
import com.feityz.util.SpringUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 流程实体控制器
 * @author zhangjie
 * @date 2021-04-07
 */
@Controller
@RequestMapping("/processInstance")
public class ProcessInstanceController {

    @Resource
    private IProcessInstanceService processInstanceService;
    @Resource
    private ProcessService processService;
    @Resource
    private HistoryService historyService;

    @ApiOperation(value = "跳转到流程监控页面(处理人)", notes = "跳转到流程监控页面(处理人)")
    @GetMapping("/assignee")
    @ApiIgnore
    public String assignee(){
        return "process/assignee";
    }

    @ApiOperation(value = "跳转到流程监控页面(发起人)", notes = "跳转到流程监控页面(发起人)")
    @GetMapping("/promoter")
    @ApiIgnore
    public String promoter(){
        return "process/promoter";
    }

    @ApiOperation(value = "打开通用查看页面", notes = "打开通用查看页面(发起人)")
    @GetMapping("/commonView")
    @ApiIgnore
    public String commonView(Long instanceId, Model model){

        Map result = processInstanceService.getInstanceParams(instanceId);
        model.addAttribute("instanceId",instanceId);
        model.addAttribute("url",result.get("url"));
        model.addAttribute("bizId",result.get("bizId"));
        return "process/view";
    }

    @ApiOperation(value = "流程监控(处理人)", notes = "流程监控(处理人)")
    @PostMapping("/assignee/list")
    @ResponseBody
    @ApiIgnore
    public Rest listByAssignee(ProcessInstanceVo condition, int page, int limit){

        if(StringUtils.isEmpty(condition.getAssignee())){
            condition.setAssignee(SpringUtils.getLoginUser().getId().toString());
        }

        IPage<ProcessInstanceVo> pageInfo =
                processInstanceService.pageByAssignee(condition,new Page<ProcessInstanceVo>(page, limit));
        return Rest.success().setData(pageInfo.getRecords()).setTotal(pageInfo.getTotal());
    }
    @ApiOperation(value = "流程监控(发起人)", notes = "流程监控(发起人)")
    @PostMapping("/promoter/list")
    @ResponseBody
    @ApiIgnore
    public Rest listByPromoter(ProcessInstanceVo condition, int page, int limit){
        if(StringUtils.isEmpty(condition.getPromoter())){
            condition.setPromoter(SpringUtils.getLoginUser().getId().toString());
        }
        IPage<ProcessInstanceVo> pageInfo =
                processInstanceService.pageByPromoter(condition,new Page<ProcessInstanceVo>(page, limit));
        return Rest.success().setData(pageInfo.getRecords()).setTotal(pageInfo.getTotal());
    }

    @ApiOperation(value = "流程监控(所有)", notes = "流程监控(所有)")
    @PostMapping("/all")
    @ResponseBody
    public Rest listAll(@RequestBody InstanceInput input){
        ProcessInstanceVo condition = new ProcessInstanceVo();
        condition.setProcessKey(input.getProcessKey());
        IPage<ProcessInstanceVo> pageInfo =
                processInstanceService.pageAll(condition,new Page<ProcessInstanceVo>(input.getPage(), input.getLimit()));
        return Rest.success().setData(pageInfo.getRecords()).setTotal(pageInfo.getTotal());
    }

}
