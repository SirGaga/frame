package com.feityz.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feityz.system.dao.RoleMapper;
import com.feityz.system.dao.UserAndRolesMapper;
import com.feityz.system.dao.UserMapper;
import com.feityz.system.entity.Dept;
import com.feityz.system.entity.User;
import com.feityz.system.entity.UserAndRoles;
import com.feityz.system.service.IDeptService;
import com.feityz.system.service.IUserService;
import com.feityz.system.vo.UserInput;
import com.feityz.util.Md5Util;
import com.feityz.util.SpringUtils;
import exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Resource
    private UserAndRolesMapper userAndRolesMapper;
    @Resource
    private RoleMapper roleMapper;
    @Autowired
    private IDeptService deptService;

    @Override
    public IPage<User> getPage(User condition, Page<User> page) {
        page.setRecords(userMapper.listPage(page, condition));
        return page;
    }

    @Override
    public List<User> listAll(User condition) {
        QueryWrapper query = new QueryWrapper();
        return userMapper.listPage(condition);
    }

    @Override
    public boolean save(User entity) {
        checkUser(entity);
        String[] roles = entity.getRoles().split(",");
        if (entity.getId() == null) {
            entity.setPassword(Md5Util.getPassword(entity.getUserNum(), entity.getPassword()));
            userMapper.insert(entity);
        } else {
            User sourceUser = userMapper.selectById(entity.getId());
            if (StringUtils.isEmpty(entity.getPassword())) {
                entity.setPassword(sourceUser.getPassword());
            } else {
                entity.setPassword(Md5Util.getPassword(entity.getUserNum(), entity.getPassword()));
            }
            userMapper.updateById(entity);
        }
        // 保存人员角色关系
        QueryWrapper<UserAndRoles> condition = new QueryWrapper<UserAndRoles>();
        condition.eq("user_id", entity.getId());
        userAndRolesMapper.delete(condition);
        for (String roleId : roles) {
            if (StringUtils.isEmpty(roleId)) {
                continue;
            }
            UserAndRoles relation = new UserAndRoles();
            relation.setUserId(entity.getId());
            relation.setRoleId(Long.valueOf(roleId));
            userAndRolesMapper.insert(relation);
        }
        return true;
    }

    @Override
    public boolean saveUserSync(UserInput entity) {
        String userNum = entity.getUserNum();
        User user = lambdaQuery().eq(User::getUserNum,entity.getUserNum()).one();
        if(user==null){
            user = new User();
            user.setUserName(entity.getUserName());
            user.setUserNum(entity.getUserNum());
            user.setPassword(Md5Util.getPassword(entity.getUserNum(), entity.getPassWord()));
            userMapper.insert(user);
        }else{
            //user = new User();
            user.setUserName(entity.getUserName());
            user.setUserNum(entity.getUserNum());
            user.setPassword(Md5Util.getPassword(entity.getUserNum(), entity.getPassWord()));
            userMapper.updateById(user);
        }
        //更新人员部门
        String deptNum = entity.getDeptNum();

        if(StringUtils.isEmpty(deptNum)) {
            return true;
        }

        Dept dept = deptService.lambdaQuery().eq(Dept::getDeptNum,deptNum).one();

        if(dept==null) {
            dept = new Dept();
            dept.setDeptNum(deptNum);
            dept.setDeptName(entity.getDeptName());
            deptService.saveOrUpdate(dept);
        } else{
            dept.setDeptName(entity.getDeptName());
            deptService.saveOrUpdate(dept);
        }
        user.setDept(dept.getId());
        userMapper.updateById(user);
        return true;
    }

    @Override
    public User getUserByCode(String code) {
        return this.lambdaQuery().eq(User::getUserNum,code).one();
    }

    @Override
    public void changePassword(User user) {
        User userRes = this.getById(SpringUtils.getLoginUser().getId());
        String oldPwd = userRes.getPassword();
        //输入的原密码
        String passwordInput = Md5Util.getPassword(userRes.getUserNum(), user.getOldPassword());

        if (!oldPwd.equals(passwordInput)) {
            throw new BizException("原密码不正确");
        } else {
            userRes.setPassword(Md5Util.getPassword(userRes.getUserNum(), user.getPassword()));
            this.updateById(userRes);
        }
    }

    @Override
    public boolean regist(User user) {
        //先查找有没有该用户
        checkUser(user);

        user.setPassword(Md5Util.getPassword(user.getUserNum(), user.getPassword()));

        baseMapper.insert(user);

        return false;
    }

    private boolean checkUser(User user) {
        List<User> users = this.lambdaQuery().eq(User::getUserNum,user.getUserNum()).list();
        //插入时候
        if (user.getId() == null) {
            if (users != null && users.size() > 0) {
                throw new BizException("已存在编号为[" + user.getUserNum() + "]的用户");
            }
        } else {
            //更新操作
            if (users.size() == 1) {
                if (!user.getId().equals(users.get(0).getId())) {
                    throw new BizException("已存在编号为[" + user.getUserNum() + "]的用户");
                }
            }
        }
        return true;
    }
}
