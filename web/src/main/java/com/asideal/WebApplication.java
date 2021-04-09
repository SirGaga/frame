package com.asideal;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * spring boot 项目启动入口
 * @author zhangjie
 * @date 2021-04-07
 */
@SpringBootApplication(scanBasePackages = {"com.asideal"})
@ImportResource({"classpath:uflo-console-context.xml"})
@EnableTransactionManagement
@EnableAsync
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication app  = new SpringApplication(WebApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
