package com.feityz.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feityz.common.BaseController;
import com.feityz.common.Rest;
import com.feityz.system.entity.Role;
import com.feityz.system.service.IRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    public IRoleService roleService;

    @GetMapping("/index")
    @RequiresPermissions("role:index")
    public String index(Model model) {
        return "role/index";
    }

    @ResponseBody
    @PostMapping("/list")
    @RequiresPermissions("role:list")
    public Rest list(Role condition, int page, int limit) {
        IPage<Role> pageInfo = roleService.getPage(condition, new Page<Role>(page, limit));
        return Rest.success().setData(pageInfo.getRecords()).setTotal(pageInfo.getTotal());
    }

    @ResponseBody
    @PostMapping("/listAll")
    @RequiresPermissions("role:listAll")
    public Rest listAll(Role condition) {
        return Rest.success().setData(roleService.listAll(condition));
    }


    @PostMapping("/save")
    @RequiresPermissions("role:save")
    @ResponseBody
    public Rest save(Role entity) {

        return Rest.success().setData(roleService.save(entity));
    }


    @GetMapping("/get")
    @ResponseBody
    public Rest get(String id) {

        return Rest.success().setData(roleService.getById(id));
    }

    @GetMapping("/delete")
    @RequiresPermissions("role:delete")
    @ResponseBody
    public Rest delete(Long id) {
        boolean success = roleService.removeById(id);
        return Rest.success();
    }

    @GetMapping("/listAllForMuilte")
    @ResponseBody
    public Rest listForMuilte(Role condition) {
        return Rest.success().setData(roleService.listAllForMuilte(condition));
    }


}
