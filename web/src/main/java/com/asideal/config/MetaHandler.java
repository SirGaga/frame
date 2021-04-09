package com.asideal.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.asideal.util.SpringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * 处理新增和更新的基础数据填充
 * 配合BaseEntity和MyBatisPlusConfig使用
 * @author zhangjie
 * @date 2021-04-07
 */
@Component
public class MetaHandler implements MetaObjectHandler {
    private static final Logger logger = LoggerFactory.getLogger(MetaHandler.class);

    /**
     * 新增数据执行
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("insertTime", new Date(), metaObject);
        this.setFieldValByName("insertUser", String.valueOf(getUserId()), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateUser", String.valueOf(getUserId()), metaObject);
        this.setFieldValByName("flag", true, metaObject);
    }

    /**
     * 更新数据执行
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateUser", String.valueOf(getUserId()), metaObject);
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    private Long getUserId() {
        Long userId = 0L;
        try {
            if (SpringUtils.getLoginUser() != null) {
                userId = SpringUtils.getLoginUser().getId();
            }
        }catch (Exception e){

        }

        return userId;
    }
}
