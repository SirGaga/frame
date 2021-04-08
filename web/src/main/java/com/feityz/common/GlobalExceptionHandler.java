package com.feityz.common;

import com.alibaba.druid.support.json.JSONUtils;
import com.feityz.util.RequestUtil;
import exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * 全局异常处理
 * @author zhangjie
 * @date 2021-04-08
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    private String unauthorizedException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        System.out.println("UnauthorizedException成功拦截");
        log.error("{} UnauthorizedException", request.getRequestURI(), e);
        if (RequestUtil.isAjaxRequest(request)) {
            writeJson(Rest.failure(e.getMessage()), response);
            return null;
        } else {
            return "/403.html";
        }
    }

    @ExceptionHandler(BindException.class)
    private String bindException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        System.out.println("UnauthorizedException成功拦截");
        writeJson(Rest.failure(e.getMessage()), response);
        return null;
    }

    @ExceptionHandler(BizException.class)
    private String bizException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        writeJson(Rest.failure(e.getMessage()), response);
        return null;
    }


    @ExceptionHandler(Exception.class)
    private String handlerErrorInfo(HttpServletRequest request, HttpServletResponse response, Exception e) {
        e.printStackTrace();
        writeJson(Rest.failure(e.getMessage()), response);
        return null;
    }

    private void writeJson(Rest result, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            out = response.getWriter();
            out.write(JSONUtils.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


    /**
     * 校验错误拦截处理
     *
     * @param exception 错误信息集合
     * @return 错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Rest validationBodyException(MethodArgumentNotValidException exception, HttpServletResponse response) {

        BindingResult result = exception.getBindingResult();
        //判断是否有错误
        if (result.hasErrors()) {
            List<ObjectError> messages = result.getAllErrors();
            for (ObjectError message : messages) {
                writeJson(Rest.failure(message.getDefaultMessage()), response);
                return null;
            }
        }

        return Rest.failure("请填写正确信息");
    }

}
