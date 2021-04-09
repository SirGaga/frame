package com.asideal.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

    /***
     * 判断一个请求是否为AJAX请求,是则返回true
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String xRequestedWith=request.getHeader("X-Requested-With");
        if(xRequestedWith!= null && xRequestedWith.indexOf("XMLHttpRequest")!=-1){
            return true;
        }else{
            return false;
        }
    }
}
