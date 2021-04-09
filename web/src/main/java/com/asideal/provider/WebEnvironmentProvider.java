package com.asideal.provider;

import com.bstek.uflo.env.EnvironmentProvider;
import com.asideal.util.SpringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

@Component
@EnableTransactionManagement
public class WebEnvironmentProvider implements EnvironmentProvider {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public PlatformTransactionManager getPlatformTransactionManager() {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Override
    public String getCategoryId() {
        return null;
    }
    @Override
    public String getLoginUser() {
        String userId = "";
        try {
            userId = SpringUtils.getLoginUser().getId().toString();
        }catch (Exception e){

        }
        return userId;
    }
}
