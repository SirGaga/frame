package com.feityz.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feityz.system.dao.UserAndRolesMapper;
import com.feityz.system.entity.UserAndRoles;
import com.feityz.system.service.IUserAndRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserAndRoleServiceImpl extends ServiceImpl<UserAndRolesMapper, UserAndRoles> implements IUserAndRoleService {
}
