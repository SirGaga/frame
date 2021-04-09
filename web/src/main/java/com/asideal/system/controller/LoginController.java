package com.asideal.system.controller;

import com.asideal.common.Rest;
import com.asideal.system.entity.User;
import com.asideal.system.service.IMenuService;
import com.asideal.util.SpringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 登录接口
 */
@Controller
@ApiIgnore
public class LoginController {
    @Autowired
    private IMenuService menuService;

    @GetMapping({"/","/login"})
    public String toLogin(){
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public Rest login(String userName, String password) {
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        // 执行认证登陆
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            return Rest.failure("未知账户");
        } catch (IncorrectCredentialsException ice) {
            return Rest.failure("密码不正确");
        } catch (LockedAccountException lae) {
            return Rest.failure("账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            return Rest.failure("用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            return Rest.failure(ae.getCause().getMessage());
        }
        if (subject.isAuthenticated()) {
            //这里判断用户类型
            //管理员登录,跳转维护界面
            User user = SpringUtils.getLoginUser();
            String url = "/index";

            return Rest.success("登录成功").setData(url);
        } else {
            token.clear();
            return Rest.failure("登录失败");
        }
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("user", SpringUtils.getLoginUser());
        model.addAttribute("menus", menuService.listAllPermission());
        return "index";
    }

    @GetMapping("/loginUser")
    @ResponseBody
    public Rest getLoginUser() {
        User user = SpringUtils.getLoginUser();
        return Rest.success().setData(user);
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "home/welcome";
    }

}
