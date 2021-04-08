package com.feityz;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.feityz"})
@ImportResource({"classpath:uflo-console-context.xml"/*,"classpath:ureport-console-context.xml"*/})
@EnableTransactionManagement
@EnableAsync
//@EnableAutoConfiguration(exclude= HibernateJpaAutoConfiguration.class)
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication app  = new SpringApplication(WebApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
