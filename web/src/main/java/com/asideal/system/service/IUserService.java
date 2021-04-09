package com.asideal.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.asideal.system.entity.User;
import com.asideal.system.vo.UserInput;

import java.util.List;

public interface IUserService extends IService<User> {
    IPage<User> getPage(User condition, Page<User> page);

    @Override
    boolean save(User user);

    boolean saveUserSync(UserInput entity);

    User getUserByCode(String code);

    List<User> listAll(User condition);

    /**
     * 修改密码
     *
     * @param user
     */
    void changePassword(User user);

    boolean regist(User user);
}
