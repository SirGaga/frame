package com.asideal.provider;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.assign.AssigneeProvider;
import com.bstek.uflo.process.assign.Entity;
import com.bstek.uflo.process.assign.PageQuery;
import com.asideal.system.entity.Role;
import com.asideal.system.entity.UserAndRoles;
import com.asideal.system.service.IRoleService;
import com.asideal.system.service.IUserAndRoleService;
import com.asideal.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleAssigneeProvider implements AssigneeProvider {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserAndRoleService userAndRoleService;
    @Autowired
    private IUserService userService;

    @Override
    public boolean isTree() {
        return false;
    }

    @Override
    public String getName() {
        return "按角色";
    }

    @Override
    public void queryEntities(PageQuery<Entity> pageQuery, String parentId) {
        IPage<Role> page = new Page<>(pageQuery.getPageIndex(),pageQuery.getPageSize());
        page = roleService.page(page);
        List<Role> depts = page.getRecords();
        List<Entity> entities = new ArrayList<>();
        depts.forEach(n->{
            Entity en = new Entity(n.getId().toString(),n.getRoleName());
            entities.add(en);
        });
        pageQuery.setResult(entities);
        pageQuery.setRecordCount((int)page.getTotal());
    }

    /**
     * 按角色取人员
     * @param entityId 处理人ID，可能是一个用户的用户名，这样就是直接返回这个用户名，也可能是一个部门的ID，那么就是返回这个部门下的所有用户的用户名等
     * @param context context 流程上下文对象
     * @param processInstance 流程实例对象
     * @return
     */
    @Override
    public Collection<String> getUsers(String entityId, Context context, ProcessInstance processInstance) {
        List<String> userIds =userAndRoleService.lambdaQuery().eq(UserAndRoles::getRoleId,entityId)
                .list().stream().map(e->e.getUserId().toString()).collect(Collectors.toList());
        return userIds;
    }

    @Override
    public boolean disable() {
        return false;
    }
}
