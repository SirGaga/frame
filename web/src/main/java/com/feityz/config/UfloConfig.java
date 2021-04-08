package com.feityz.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Properties;

/**
 * 流程控制
 * @author zhangjie
 * @date 2021-04-07
 */
@Configuration

public class UfloConfig {

    @Bean("localSessionFactoryBean")
    public LocalSessionFactoryBean localSessionFactoryBean(DataSource dataSource) throws
            PropertyVetoException, IOException {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPackagesToScan("com.bstek.uflo.model*");
        Properties prop = new Properties();
        prop.put("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
        prop.put("hibernate.show_sql",false);
        // 每次自动创建|更新|验证数据库表结构。
        // none 不做验证
        // validate 加载hibernate时，验证创建数据库表结构
        // create 每次加载hibernate，重新创建数据库表结构
        // create-drop 加载hibernate时创建，退出是删除表结构
        // update 加载hibernate自动更新数据库结构
        prop.put("hibernate.hbm2ddl.auto","none");
        prop.put("hibernate.jdbc.batch_size",100);
        sessionFactoryBean.setHibernateProperties(prop);

        return sessionFactoryBean;
    }


    @Bean("ufloTransactionManager")
    public HibernateTransactionManager ufloTransactionManager(SessionFactory sessionFactory){
        HibernateTransactionManager hi = new HibernateTransactionManager();
        hi.setSessionFactory(sessionFactory);
        return hi;
    }

}
