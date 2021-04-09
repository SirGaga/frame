package com.asideal.common;

import com.asideal.system.entity.User;
import com.asideal.util.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 基础控制器
 * @author zhangjie
 * @date 2021-04-08
 */
@Controller
public class BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 从thread local获取网络上下文
     */
    public HttpServletRequest getServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes;
        if (requestAttributes instanceof ServletRequestAttributes) {
            servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            return servletRequestAttributes.getRequest();
        }
        return null;
    }

    public HttpServletResponse getServletResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes;
        if (requestAttributes instanceof ServletRequestAttributes) {
            servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            return servletRequestAttributes.getResponse();
        }
        return null;
    }

    /**
     * 获取当前客户端session对象
     *
     * @return
     */
    public HttpSession getSession() {
        return getServletRequest().getSession();
    }

    /**
     * 获取当前登录的用户
     * @return 返回当前登录的用户实体
     */
    protected User getLoginUser() {

        return SpringUtils.getLoginUser();
    }

}
