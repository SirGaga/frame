package com.asideal.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.asideal.common.BaseController;
import com.asideal.common.Rest;
import com.asideal.system.entity.Parameter;
import com.asideal.system.service.IParameterService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(tags = "系统参数接口")
@Controller
@RequestMapping("/parameter")
public class ParameterController extends BaseController {

    @Autowired
    public IParameterService parameterService;

    /**
     * 跳转列表页面
     *
     * @param model
     * @return
     */
    @GetMapping("/index")
    @RequiresPermissions("parameter:index")
    public String index(Model model) {
        return "parameter/index";
    }

    /**
     * 分页查询数据
     *
     * @param condition
     * @param page
     * @param limit
     * @return
     */
    @ResponseBody
    @PostMapping("/list")
    @RequiresPermissions("parameter:list")
    public Rest list(Parameter condition, int page, int limit) {
        IPage<Parameter> pageInfo = parameterService.getPage(condition, new Page<Parameter>(page, limit));
        return Rest.success().setData(pageInfo.getRecords()).setTotal(pageInfo.getTotal());
    }

    /**
     * 保存数据
     *
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    @RequiresPermissions("parameter:save")
    public Rest save(Parameter entity) {
        parameterService.checkParameter(entity);
        return Rest.success().setData(parameterService.saveOrUpdate(entity));
    }

    //根据主键获取
    @GetMapping("/get")
    @ResponseBody
    public Rest get(String id) {
        return Rest.success().setData(parameterService.getById(id));
    }

    //删除
    @GetMapping("/delete")
    @RequiresPermissions("parameter:delete")
    @ResponseBody
    public Rest delete(String id) {
        return Rest.success().setData(parameterService.removeById(id));
    }

}
