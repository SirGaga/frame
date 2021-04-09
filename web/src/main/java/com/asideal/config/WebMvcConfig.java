package com.asideal.config;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.OpenSessionInViewInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * WebMvcConfig，URL Mapping不经过Controller处理直接跳转到页面上
 * @author zhangjie
 * @date 2021-04-08
 */
@Component
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private SessionFactory sessionFactory;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        OpenSessionInViewInterceptor sessionInterceptor = new OpenSessionInViewInterceptor();
        sessionInterceptor.setSessionFactory(sessionFactory);
        registry.addWebRequestInterceptor(sessionInterceptor);
    }
}
