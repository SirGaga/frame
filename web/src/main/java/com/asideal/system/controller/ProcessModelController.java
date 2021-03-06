package com.asideal.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.asideal.common.BaseController;
import com.asideal.common.Rest;
import com.asideal.system.entity.ProcessModel;
import com.asideal.system.service.IProcessModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.io.File;

@Api(tags = "请写全接口用途注释")
@Controller
@RequestMapping("/processModel")
@ApiIgnore
public class ProcessModelController extends BaseController {

    /**
     * 文件存储路径
     */
    @Value("${fileStoreDir}")
    private String fileStoreDir;

    @Resource
    public IProcessModelService processModelService;


    @GetMapping("/index")
    //@RequiresPermissions("processModel:index")
    @ApiOperation(value = "跳转页面", notes = "跳转到列表页面")
    @ApiIgnore
    public String index(Model model) {
        return "processModel/index";
    }

    @ResponseBody
    @GetMapping("/list")
    //@RequiresPermissions("processModel:list")
    @ApiOperation(value = "分页查询数据", notes = "分页查询数据")
    public Rest list(ProcessModel condition, int page, int limit) {
        IPage<ProcessModel> pageInfo = processModelService.getPage(condition, new Page<ProcessModel>(page, limit));
        return Rest.success().setData(pageInfo.getRecords()).setTotal(pageInfo.getTotal());
    }

    @PostMapping("/save")
    @ResponseBody
    //@RequiresPermissions("processModel:save")
    @ApiOperation(value = "保存数据", notes = "保存数据")
    public Rest save(ProcessModel entity) {
        return Rest.success().setData(processModelService.saveOrUpdate(entity));
    }

    @GetMapping("/get")
    @ResponseBody
    //@RequiresPermissions("processModel:get")
    @ApiOperation(value = "根据主键获取", notes = "根据主键获取")
    public Rest get(Long id) {
        return Rest.success().setData(processModelService.getById(id));
    }

    @GetMapping("/delete")
    //@RequiresPermissions("processModel:delete")
    @ResponseBody
    @ApiOperation(value = "根据主键删除", notes = "根据主键删除")
    public Rest delete(Long id) {
        ProcessModel model = processModelService.getById(id);
        String fullPath = this.fileStoreDir + "/" + model.getFileName();
        File f = new File(fullPath);
        if (f.exists()) {
            f.delete();
        }
        return Rest.success().setData(processModelService.removeById(id));
    }
}
