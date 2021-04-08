package com.feityz.config;

import com.feityz.system.entity.Menu;
import com.feityz.system.entity.User;
import com.feityz.system.service.IMenuService;
import com.feityz.system.service.IUserService;
import com.feityz.util.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * shiro的自定义角色权限认证检查
 * @author zhangjie
 * @date 2021-04-08
 */
public class CustomRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Set<String> stringSet = new HashSet<>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = SpringUtils.getLoginUser();
        if("admin".equals(user.getUserName())){

            info.addRole("admin");
            info.addStringPermission("*:*:*");

        }else{
            IMenuService menuService = SpringUtils.getBean("menuServiceImpl");
            List<Menu> menus = menuService.selectPermissionByUser(user);
            menus.forEach(n -> {
                if (StringUtils.isNotEmpty(n.getPermission())) {
                    stringSet.add(n.getPermission());
                }
            });
            info.setStringPermissions(stringSet);
        }

        return info;
    }

    /**
     * 这里可以注入userService,为了方便演示，我就写死了帐号了密码 private UserService userService
     *
     * 获取即将需要认证的信息
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        // 根据用户名从数据库获取密码
        IUserService userService = SpringUtils.getBean("userServiceImpl");
        User user = userService.lambdaQuery().eq(User::getUserNum,userName).one();
        if (user == null) {
            return null;
        }
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUserNum());
        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt,
                getName());
        return authcInfo;
    }
    /*@Override
    public  boolean isPermitted(PrincipalCollection principals, String permission){
        User user = (User) principals.getPrimaryPrincipal();
        // 如果是管理员拥有所有的访问权限
        return user.getUserNum().equals("admin");
    }
    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        User user = (User) principals.getPrimaryPrincipal();
        // 如果是管理员拥有所有的角色权限
        return user.getUserNum().equals("admin");
    }*/
}
