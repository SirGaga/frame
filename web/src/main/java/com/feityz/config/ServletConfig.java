package com.feityz.config;

import com.bstek.uflo.console.UfloServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * servlet配置
 * @author zhangjie
 * @date 2021-04-07
 */
@Configuration
public class ServletConfig {
    /**
     * 注册流程相关的servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean<UfloServlet> buildUfloServlet(){
        ServletRegistrationBean<UfloServlet> servletServletRegistrationBean=new ServletRegistrationBean<>();
        servletServletRegistrationBean.setServlet(new UfloServlet());
        servletServletRegistrationBean.addUrlMappings("/uflo/*");

        return servletServletRegistrationBean;
    }

    /*@Bean
    public ServletRegistrationBean buildUReportServlet(){
        return new ServletRegistrationBean(new UReportServlet(),"/ureport/*");
    }*/
}
