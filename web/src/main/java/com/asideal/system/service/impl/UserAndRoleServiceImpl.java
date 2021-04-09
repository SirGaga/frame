package com.asideal.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.asideal.system.dao.UserAndRolesMapper;
import com.asideal.system.entity.UserAndRoles;
import com.asideal.system.service.IUserAndRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserAndRoleServiceImpl extends ServiceImpl<UserAndRolesMapper, UserAndRoles> implements IUserAndRoleService {
}
