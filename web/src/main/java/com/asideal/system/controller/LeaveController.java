package com.asideal.system.controller;

import com.bstek.uflo.service.ProcessService;
import com.bstek.uflo.service.StartProcessInfo;
import com.asideal.util.SpringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.asideal.system.service.ILeaveService;
import com.asideal.system.entity.Leave;
import com.asideal.common.BaseController;
import com.asideal.common.Rest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import springfox.documentation.annotations.ApiIgnore;
import io.swagger.annotations.ApiOperation;

import java.util.Map;

@ApiIgnore
@Controller
@RequestMapping("/leave")
public class LeaveController extends BaseController {

    @Autowired
    public ILeaveService leaveService;
    @Autowired
    private ProcessService processService;


    @GetMapping("/index")
    @RequiresPermissions("leave:index")
    @ApiOperation(value = "跳转页面", notes = "跳转到列表页面")
    @ApiIgnore
    public String index(Model model) {
        return "leave/index";
    }

    @GetMapping("/form")
    @ApiOperation(value = "表单页面", notes = "表单页面")
    @ApiIgnore
    public String form(long bizId,Model model) {
        model.addAttribute("bizId",bizId);
        return "leave/form";
    }

    @GetMapping("/edit")
    @ApiOperation(value = "可编辑表单", notes = "可编辑表单")
    @ApiIgnore
    public String edit(long bizId,Model model) {
        model.addAttribute("bizId",bizId);
        return "leave/edit";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("leave:list")
    @ApiOperation(value = "分页查询数据", notes = "分页查询数据")
    public Rest list(Leave condition, int page, int limit) {
        IPage<Leave> pageInfo = leaveService.getPage(condition, new Page<Leave>(page, limit));
        return Rest.success().setData(pageInfo.getRecords()).setTotal(pageInfo.getTotal());
    }

    @ResponseBody
    @PutMapping("/apply/{id}")
    @ApiOperation(value = "提交", notes = "提交")
    public Rest apply(@PathVariable Long id,@RequestBody Map var){
        Leave leave = leaveService.getById(id);
        StartProcessInfo info = new StartProcessInfo();
        info.setBusinessId(id.toString());
        info.setPromoter(SpringUtils.getLoginUser().getId().toString());
        info.setSubject(leave.getApplyUser()+"的请假");
        var.put("dayCount",1);
        info.setVariables(var);
        processService.startProcessByKey("leave",info);
        return Rest.success("启动成功");
    }


    @PostMapping("/save")
    @ResponseBody
    @RequiresPermissions("leave:save")
    @ApiOperation(value = "保存数据", notes = "保存数据")
    public Rest save(Leave entity) {
        entity.setDraft(0);
        return Rest.success().setData(leaveService.saveOrUpdate(entity));
    }

    @GetMapping("/get")
    @ResponseBody
    @ApiOperation(value = "根据主键获取", notes = "根据主键获取")
    public Rest get(Long id) {
        return Rest.success().setData(leaveService.getById(id));
    }

    @GetMapping("/delete")
    @RequiresPermissions("leave:delete")
    @ResponseBody
    @ApiOperation(value = "根据主键删除", notes = "根据主键删除")
    public Rest delete(Long id) {
        return Rest.success().setData(leaveService.removeById(id));
    }

    @ApiOperation(value = "将表单从草稿改成在流程中的状态,对应流程中的leave事件", notes = "将表单从草稿改成在流程中的状态")
    @PostMapping("/leave")
    @ResponseBody
    public Rest updateToProcess(@RequestBody Leave entity){
        Leave leave = leaveService.getById(entity.getId());
        leave.setDraft(1);
        leaveService.updateById(leave);
        return Rest.success();
    }
}
