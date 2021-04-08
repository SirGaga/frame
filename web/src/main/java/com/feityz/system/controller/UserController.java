package com.feityz.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feityz.common.BaseController;
import com.feityz.common.Rest;
import com.feityz.system.entity.User;
import com.feityz.system.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 用户操作
 * @author 张洁
 * @date 2021-04-07
 */
@ApiIgnore
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    public IUserService userService;

    /**
     * 跳转列表页面
     *
     * @param model
     * @return
     */
    @GetMapping("/index")
    @RequiresPermissions("user:index")
    public String index(Model model) {
        return "user/index";
    }

    /**
     * @param condition
     * @param page
     * @param limit
     * @return
     */
    @ResponseBody
    @PostMapping("/list")
    @RequiresPermissions("user:list")
    public Rest list(User condition, int page, int limit) {
        IPage<User> pageInfo = userService.getPage(condition, new Page<User>(page, limit));
        return Rest.success().setData(pageInfo.getRecords()).setTotal(pageInfo.getTotal());
    }

    @ResponseBody
    @PostMapping("/listNopermission")
    public Rest listNopermission(User condition, int page, int limit) {
        IPage<User> pageInfo = userService.getPage(condition, new Page<User>(page, limit));
        return Rest.success().setData(pageInfo.getRecords()).setTotal(pageInfo.getTotal());
    }

    /**
     * 保存数据
     *
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    @RequiresPermissions("user:save")
    public Rest save(User entity) {
        return Rest.success().setData(userService.save(entity));
    }

    //根据主键获取
    @GetMapping("/get")
    @ResponseBody
    public Rest get(String id) {

        return Rest.success().setData(userService.getById(id));
    }

    //删除
    @GetMapping("/delete")
    @ResponseBody
    @RequiresPermissions("user:delete")
    public Rest delete(String id) {
        userService.removeById(id);
        return Rest.success();
    }

    /**
     * 修改用户密码
     * @param user 用户前端采集的信息
     * @return 返回是否成功
     */
    @PostMapping("/changePassword")
    @ResponseBody
    public Rest changePassword(User user) {
        userService.changePassword(user);
        return Rest.success();
    }
}
