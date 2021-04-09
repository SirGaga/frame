package com.asideal.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.asideal.common.BaseController;
import com.asideal.common.Rest;
import com.asideal.system.entity.Menu;
import com.asideal.system.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Autowired
    public IMenuService menuService;

    /**
     * 跳转列表页面
     *
     * @param model
     * @return
     */
    @GetMapping("/index")
    public String index(Model model) {
        return "menu/index";
    }

    /**
     * @param condition
     * @param page
     * @param limit
     * @return
     */
    @ResponseBody
    @PostMapping("/list")
    public Rest list(Menu condition, int page, int limit) {
        IPage<Menu> pageInfo = menuService.getPage(condition, new Page<Menu>(page, limit));
        return Rest.success().setData(pageInfo.getRecords()).setTotal(pageInfo.getTotal());
    }

    /**
     * 保存数据
     *
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public Rest save(Menu entity) {

        return Rest.success().setData(menuService.saveOrUpdate(entity));
    }

    //根据主键获取
    @GetMapping("/get")
    @ResponseBody
    public Rest get(String id) {
        return Rest.success().setData(menuService.getById(id));
    }

    //删除
    @GetMapping("/delete")
    @ResponseBody
    public Rest delete(String id) {
        menuService.removeById(id);
        return Rest.success();
    }

    @GetMapping("/listAll")
    @ResponseBody
    public Rest listAll(String roleId) {
        return Rest.success().setData(menuService.listAll(roleId));
    }

    @GetMapping("/listAllPermission")
    @ResponseBody
    public Rest listAllPermisson(String roleId) {
        return Rest.success().setData(menuService.listAllPermission());
    }

}
