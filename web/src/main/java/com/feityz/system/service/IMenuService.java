package com.feityz.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feityz.system.entity.Menu;
import com.feityz.system.entity.User;

import java.util.List;

/**
 * <p>
 * Menu服务接口
 * </p>
 *
 * @author Line
 * @since 2019-11-14
 */
public interface IMenuService extends IService<Menu> {

    IPage<Menu> getPage(Menu condition, Page<Menu> page);

    List<Menu> listAll(String roleId);

    List<Menu> listAllPermission();

    List<Menu> selectPermissionByUser(User user);
}