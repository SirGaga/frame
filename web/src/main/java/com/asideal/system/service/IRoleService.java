package com.asideal.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.asideal.system.entity.Role;
import com.asideal.system.vo.RoleInput;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Role服务接口
 * </p>
 *
 * @author Line
 * @since 2019-11-13
 */
public interface IRoleService extends IService<Role> {

    IPage<Role> getPage(Role condition, Page<Role> page);

    @Override
    boolean save(Role role);

    List<Role> listAll(Role condition);

    List<Map<String, String>> listAllForMuilte(Role condition);

    void saveRoles(RoleInput input);
}