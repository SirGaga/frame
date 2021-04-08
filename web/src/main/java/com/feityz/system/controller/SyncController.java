package com.feityz.system.controller;

import com.feityz.common.Rest;
import com.feityz.system.entity.Dept;
import com.feityz.system.entity.Role;
import com.feityz.system.entity.User;
import com.feityz.system.service.IDeptService;
import com.feityz.system.service.IRoleService;
import com.feityz.system.service.IUserService;
import com.feityz.system.vo.RoleInput;
import com.feityz.system.vo.UserInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@Api(tags = "同步模块")
@RestController
@RequestMapping("/sync")
public class SyncController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IDeptService deptService;

    @ApiOperation(value = "添加角色", notes = "添加角色")
    @PostMapping("/role")
    public Rest syncRole(@RequestBody RoleInput input){
        roleService.saveRoles(input);
        return Rest.success();
    }

    @ApiOperation(value = "删除角色", notes = "删除角色")
    @DeleteMapping("/role/{roleNum}")
    public Rest deleteRole(@PathVariable String roleNum){
        Role role = roleService.lambdaQuery().eq(Role::getRoleNum,roleNum).one();
        Assert.notNull(role,"找不到编号为["+roleNum+"]的角色");
        roleService.removeById(role);
        return Rest.success();
    }

    @ApiOperation(value = "添加人员", notes = "添加人员")
    @PostMapping("/user")
    public Rest syncUser(@RequestBody UserInput input){
        userService.saveUserSync(input);
        return Rest.success();
    }

    @ApiOperation(value = "添加部门", notes = "添加部门")
    @PostMapping("/dept")
    public Rest syncDept(@RequestBody Dept input){

        deptService.saveDeptSync(input);

        return Rest.success();
    }

    @ApiOperation(value = "删除人员", notes = "删除人员")
    @DeleteMapping("/user/{userNum}")
    public Rest deleteUser(@PathVariable String userNum){
        User user = userService.lambdaQuery().eq(User::getUserNum,userNum).one();
        Assert.notNull(user,"找不到编号为["+userNum+"]的人员");
        userService.removeById(user);
        return Rest.success();
    }
}
