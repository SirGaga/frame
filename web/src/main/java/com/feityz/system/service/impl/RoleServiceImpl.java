package com.feityz.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feityz.system.dao.RoleMapper;
import com.feityz.system.dao.UserAndRolesMapper;
import com.feityz.system.entity.Role;
import com.feityz.system.entity.RoleAndMenu;
import com.feityz.system.entity.User;
import com.feityz.system.entity.UserAndRoles;
import com.feityz.system.service.IRoleAndeMenuService;
import com.feityz.system.service.IRoleService;
import com.feityz.system.service.IUserService;
import com.feityz.system.vo.RoleInput;
import exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * Role服务类
 * </p>
 *
 * @author Line
 * @since 2019-11-13
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper iRoleMapper;
    @Autowired
    private UserAndRolesMapper userAndRolesMapper;
    @Autowired
    private IRoleAndeMenuService roleAndeMenuService;
    @Autowired
    private IUserService userService;

    @Override
    public IPage<Role> getPage(Role condition, Page<Role> page) {

        QueryWrapper<Role> query = new QueryWrapper<Role>();
        if (StringUtils.isNotEmpty(condition.getRoleName())) {
            query.like(Role.ROLE_NAME, condition.getRoleName());
        }
        if (StringUtils.isNotEmpty(condition.getRoleNum())) {
            query.like(Role.ROLE_NUM, condition.getRoleNum());
        }
        IPage<Role> result = iRoleMapper.selectPage(page, query);
        return result;
    }

    @Override
    @Transactional
    public boolean save(Role entity) {
        checkRole(entity);
        if (entity.getId() == null) {
            iRoleMapper.insert(entity);
        } else {
            iRoleMapper.updateById(entity);
        }
        //写入关系
        //先删除
        QueryWrapper<RoleAndMenu> deleteCondition = new QueryWrapper<RoleAndMenu>();
        deleteCondition.eq(RoleAndMenu.ROLE_ID, entity.getId());
        roleAndeMenuService.remove(deleteCondition);
        List<RoleAndMenu> relations = new ArrayList<RoleAndMenu>();
        for (String menuId : entity.getMenus().split(",")) {
            if (menuId.isEmpty()) {
                continue;
            }
            RoleAndMenu relation = new RoleAndMenu();
            relation.setMenuId(Long.valueOf(menuId));
            relation.setRoleId(entity.getId());
            relations.add(relation);
        }
        roleAndeMenuService.saveBatch(relations);
        return true;
    }

    @Override
    public List<Role> listAll(Role condition) {
        QueryWrapper<Role> query = new QueryWrapper<Role>();
        return iRoleMapper.selectList(query);
    }

    @Override
    public List<Map<String, String>> listAllForMuilte(Role condition) {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        List<Role> roles = this.listAll(condition);
        List<Long> roleIds = new ArrayList<Long>();
        if (condition.getUserId() != null) {
            QueryWrapper<UserAndRoles> relationCondition = new QueryWrapper<UserAndRoles>();
            relationCondition.eq("user_id", condition.getUserId());
            List<UserAndRoles> relations = userAndRolesMapper.selectList(relationCondition);
            roleIds = relations.stream().map(UserAndRoles::getRoleId).collect(Collectors.toList());
        }
        for (Role role : roles) {
            Map loc = new HashMap();
            loc.put("name", role.getRoleName());
            loc.put("value", role.getId());
            if (roleIds.contains(role.getId())) {
                loc.put("selected", "selected");
            }
            result.add(loc);
        }
        return result;
    }

    private boolean checkRole(Role role) {
        QueryWrapper<Role> query = new QueryWrapper<Role>();
        query.eq(Role.ROLE_NUM, role.getRoleNum());
        List<Role> roles = iRoleMapper.selectList(query);
        //插入时候
        if (role.getId() == null) {
            if (roles != null && roles.size() > 0) {
                throw new BizException("已存在编号为[" + role.getRoleNum() + "]的角色");
            }
        } else {
            //更新操作
            if (roles.size() == 1) {
                if (!role.getId().equals(roles.get(0).getId())) {
                    throw new BizException("已存在编号为[" + role.getRoleNum() + "]的角色");
                }
            }
        }
        return true;
    }

    public List<Long> selectMenuIdByRoleId(Long roleId) {

        List menuIds = roleAndeMenuService.getBaseMapper()
                .selectObjs(roleAndeMenuService.lambdaQuery().select(RoleAndMenu::getMenuId)
                        .eq(RoleAndMenu::getRoleId, roleId));
        return menuIds;
    }

    @Override
    @Transactional
    public void saveRoles(RoleInput input){
        Role role = lambdaQuery().eq(Role::getRoleNum,input.getRoleNum()).one();

        if(role==null){
            role = new Role();
            role.setRoleNum(input.getRoleNum());
            role.setRoleName(input.getRoleName());
            iRoleMapper.insert(role);
        }else{
            role.setRoleName(input.getRoleName());
            role.setRoleNum(input.getRoleNum());
            iRoleMapper.updateById(role);
        }
        //保存人员和角色关系
        //先删除角色下的人员
        QueryWrapper<UserAndRoles> condition = new QueryWrapper<UserAndRoles>();
        condition.eq("role_id", role.getId());
        userAndRolesMapper.delete(condition);

        String[] userNums = input.getUsers().split(";");

        for (String userNum : userNums) {
            if (StringUtils.isEmpty(userNum)) {
                continue;
            }
            User user = userService.lambdaQuery().eq(User::getUserNum,userNum).one();
            if(user==null) {
                continue;
            }else{
                UserAndRoles relation = new UserAndRoles();
                relation.setUserId(user.getId());
                relation.setRoleId(role.getId());
                userAndRolesMapper.insert(relation);
            }
        }
    }

}