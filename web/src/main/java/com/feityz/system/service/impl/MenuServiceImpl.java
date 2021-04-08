package com.feityz.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feityz.system.dao.MenuMapper;
import com.feityz.system.dao.RoleAndMenuMapper;
import com.feityz.system.dao.UserAndRolesMapper;
import com.feityz.system.entity.Menu;
import com.feityz.system.entity.RoleAndMenu;
import com.feityz.system.entity.User;
import com.feityz.system.entity.UserAndRoles;
import com.feityz.system.service.IMenuService;
import com.feityz.util.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * Menu服务类
 * </p>
 *
 * @author zhangjie
 * @since 2021-04-07
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RoleAndMenuMapper roleAndMenuMapper;
    @Resource
    private UserAndRolesMapper userAndRolesMapper;

    @Override
    public IPage<Menu> getPage(Menu condition, Page<Menu> page) {
        QueryWrapper<Menu> query = new QueryWrapper<Menu>();
        if (StringUtils.isNotEmpty(condition.getTitle())) {
            query.like(Menu.MENU_NAME, condition.getTitle());
        }
        if (StringUtils.isNotEmpty(condition.getIcon())) {
            query.like(Menu.ICON, condition.getIcon());
        }
        if (StringUtils.isNotEmpty(condition.getLeaf())) {
            query.like(Menu.LEAF, condition.getLeaf());
        }
        if (StringUtils.isNotEmpty(condition.getUrl())) {
            query.like(Menu.URL, condition.getUrl());
        }
        IPage<Menu> result = menuMapper.selectPage(page, query);
        return result;
    }

    @Override
    public List<Menu> listAll(String roleId) {
        List<Long> menuIds = new ArrayList<Long>();

        if (StringUtils.isNotEmpty(roleId)) {
            QueryWrapper<RoleAndMenu> relationCondition = new QueryWrapper<RoleAndMenu>();
            relationCondition.eq(RoleAndMenu.ROLE_ID, roleId);
            List<RoleAndMenu> relations = roleAndMenuMapper.selectList(relationCondition);
            menuIds = relations.stream().map(RoleAndMenu::getMenuId).collect(Collectors.toList());
        }
        Menu vMenu = new Menu();
        vMenu.setId(0L);
        this.recursionMenu(vMenu, menuIds);
        //先查父节点
        return vMenu.getChildren();
    }

    @Override
    public List<Menu> listAllPermission() {
        List<Long> menuIds = new ArrayList<Long>();
        //先查出人员对应的角色
        User loginUser = SpringUtils.getLoginUser();
        QueryWrapper<UserAndRoles> roleCondition = new QueryWrapper<UserAndRoles>();
        roleCondition.eq("user_id", loginUser.getId());
        List<UserAndRoles> roles = userAndRolesMapper.selectList(roleCondition);
        List<Long> roleIds = roles.stream().map(e -> e.getRoleId()).collect(Collectors.toList());
        //end
        QueryWrapper<RoleAndMenu> relationCondition = new QueryWrapper<RoleAndMenu>();
        List<RoleAndMenu> relations = new ArrayList<RoleAndMenu>();
        if (roleIds.size() > 0) {
            relationCondition.in(RoleAndMenu.ROLE_ID, roleIds);
            relations = roleAndMenuMapper.selectList(relationCondition);
        }
        menuIds = relations.stream().map(e -> e.getMenuId()).collect(Collectors.toList());
        Menu vMenu = new Menu();
        vMenu.setId(0L);
        this.recursionMenuPermisson(vMenu, menuIds);
        //先查父节点
        return vMenu.getChildren();
    }


    /**
     * 递归查找
     */
    private void recursionMenu(Menu menu, List<Long> menuIds) {
        QueryWrapper<Menu> condition = new QueryWrapper<Menu>();
        condition.eq(Menu.PARENT_ID, menu.getId());
        List<Menu> menus = menuMapper.selectList(condition);
        menu.setChildren(menus);
        for (Menu child : menus) {
            menu.setSpread(true);
            if (menuIds.contains(child.getId())) {
                child.setChecked(true);
            }
            recursionMenu(child, menuIds);
        }
    }

    /**
     * 递归查找带权限
     */
    private void recursionMenuPermisson(Menu menu, List<Long> menuIds) {
        User loginUser = SpringUtils.getLoginUser();
        QueryWrapper<Menu> condition = new QueryWrapper<Menu>();
        condition.eq(Menu.PARENT_ID, menu.getId());
        List<Menu> menus = menuMapper.selectList(condition);
        List<Menu> hasMenu = new ArrayList<Menu>();
        for (Menu sourceMenu : menus) {
            //如果该人员是超级管理员则添加全部菜单
            if (loginUser.getUserNum().equals("admin")) {
                hasMenu.add(sourceMenu);
            } else {
                if (menuIds.contains(sourceMenu.getId())) {
                    hasMenu.add(sourceMenu);
                }
            }
        }
        menu.setChildren(hasMenu);
        for (Menu child : hasMenu) {
            child.setSpread(true);
            if ("1".equals(child.getLeaf())) {
                continue;
            } else {
                recursionMenuPermisson(child, menuIds);
            }
        }
    }

    @Override
    public List<Menu> selectPermissionByUser(User user) {
        //先查出人员
        if (user.getUserNum().equals("admin")) {
            return menuMapper.selectList(new QueryWrapper<>());
        }
        QueryWrapper<UserAndRoles> roleCondition = new QueryWrapper<>();
        roleCondition.eq("user_id", user.getId());
        List<UserAndRoles> roles = userAndRolesMapper.selectList(roleCondition);
        List<String> roleIds = roles.stream().map(e -> String.valueOf(e.getRoleId())).collect(Collectors.toList());
        //根据角色查找菜单
        QueryWrapper<RoleAndMenu> relationCondition = new QueryWrapper<>();
        if (roleIds.size() == 0) {
            return new ArrayList<Menu>();
        }
        relationCondition.in(RoleAndMenu.ROLE_ID, roleIds);
        List<RoleAndMenu> relations = roleAndMenuMapper.selectList(relationCondition);
        List<String> menuIds = relations.stream().map(e -> String.valueOf(e.getMenuId())).collect(Collectors.toList());
        if (menuIds.size() == 0) {
            return new ArrayList<Menu>();
        }
        //查找出菜单
        QueryWrapper<Menu> menuCondition = new QueryWrapper<>();
        menuCondition.in(Menu.ID, menuIds);
        List<Menu> menus = menuMapper.selectList(menuCondition);
        return menus;

    }

}