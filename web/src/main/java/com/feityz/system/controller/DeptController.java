package com.feityz.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feityz.common.BaseController;
import com.feityz.common.Rest;
import com.feityz.system.entity.Dept;
import com.feityz.system.service.IDeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author Line
 * @since 2019-11-17
 */
@ApiIgnore
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController {
    @Autowired
    public IDeptService deptService;

    @GetMapping("/index")
    public String index(Model model) {
        return "dept/index";
    }

    @ResponseBody
    @GetMapping("/list")
    public Rest list(Dept condition, int page, int limit) {
        IPage<Dept> pageInfo = deptService.getPage(condition, new Page<Dept>(page, limit));
        return Rest.success().setData(pageInfo.getRecords()).setTotal(pageInfo.getTotal());
    }

    @PostMapping("/save")
    @ResponseBody
    public Rest save(Dept entity) {
        deptService.checkDept(entity);
        return Rest.success().setData(deptService.saveOrUpdate(entity));
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public Rest get(@PathVariable("id") Long id) {
        return Rest.success().setData(deptService.getById(id));
    }


    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public Rest delete(@PathVariable("id") String id) {
        deptService.removeById(id);
        return Rest.success();
    }

    @ResponseBody
    @GetMapping("/listAll")
    public Rest listAll(Dept condition) {
        return Rest.success().setData(deptService.listAll(condition));
    }

}
