package com.asideal.provider;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.assign.AssigneeProvider;
import com.bstek.uflo.process.assign.Entity;
import com.bstek.uflo.process.assign.PageQuery;
import com.asideal.system.entity.Dept;
import com.asideal.system.entity.User;
import com.asideal.system.service.IDeptService;
import com.asideal.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeptAssigneeProvider implements AssigneeProvider {
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IUserService userService;

    @Override
    public boolean isTree() {
        return false;
    }

    @Override
    public String getName() {
        return "按部门";
    }

    @Override
    public void queryEntities(PageQuery<Entity> pageQuery, String parentId) {
        IPage<Dept> page = new Page<>(pageQuery.getPageIndex(),pageQuery.getPageSize());
        page = deptService.page(page);
        List<Dept> depts = page.getRecords();
        List<Entity> entities = new ArrayList<>();
        depts.forEach(n->{
            Entity en = new Entity(n.getId().toString(),n.getDeptName());
            entities.add(en);
        });
        pageQuery.setResult(entities);
        pageQuery.setRecordCount((int)page.getTotal());
    }

    /**
     * 根据部门获取人员id
     * @param entityId 处理人ID，可能是一个用户的用户名，这样就是直接返回这个用户名，也可能是一个部门的ID，那么就是返回这个部门下的所有用户的用户名等
     * @param context context 流程上下文对象
     * @param processInstance 流程实例对象
     * @return
     */
    @Override
    public Collection<String> getUsers(String entityId, Context context, ProcessInstance processInstance) {
        List<String> userIds = userService.lambdaQuery().eq(User::getDept,entityId).list().stream()
                .map(e->e.getId().toString()).collect(Collectors.toList());
        return userIds;
    }

    @Override
    public boolean disable() {
        return false;
    }
}
