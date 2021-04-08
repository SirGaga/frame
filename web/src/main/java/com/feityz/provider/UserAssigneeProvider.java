package com.feityz.provider;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bstek.uflo.env.Context;
import com.bstek.uflo.model.ProcessInstance;
import com.bstek.uflo.process.assign.AssigneeProvider;
import com.bstek.uflo.process.assign.Entity;
import com.bstek.uflo.process.assign.PageQuery;
import com.feityz.system.entity.User;
import com.feityz.system.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UserAssigneeProvider implements AssigneeProvider {

    @Autowired
    private IUserService userService;

    @Override
    public boolean isTree() {
        return false;
    }

    @Override
    public String getName() {
        return "按人员";
    }

    @Override
    public void queryEntities(PageQuery<Entity> pageQuery, String parentId) {
        IPage<User> page = new Page<>(pageQuery.getPageIndex(),pageQuery.getPageSize());
        String search = pageQuery.getQueryParameter().getName();
        page = userService.lambdaQuery()
                .like(StringUtils.isNotEmpty(search),User::getUserNum,search)
                .or(StringUtils.isNotEmpty(search),n->{
                    n.like(User::getUserName,search);
                })
                .page(page);
        //userService.page(page);
        List<User> users = page.getRecords();
        List<Entity> entities = new ArrayList<>();
        users.forEach(n->{
            Entity en = new Entity(n.getId().toString(),n.getUserName());
            entities.add(en);
        });
        pageQuery.setResult(entities);
        pageQuery.setRecordCount((int)page.getTotal());

    }

    @Override
    public Collection<String> getUsers(String entityId, Context context, ProcessInstance processInstance) {
        List<String> user = new ArrayList<String>(){{
            add(entityId);
        }};
        return user;
    }

    @Override
    public boolean disable() {
        return false;
    }
}
