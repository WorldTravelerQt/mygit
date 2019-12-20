package com.scxh.springbootshiro.config;

import com.scxh.springbootshiro.pojo.User;
import com.scxh.springbootshiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: 乔童
 * @Description: 自定义realm
 * @Date: 2019/12/12 20:12
 * @Version: 1.0
 */
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获取当前用户
        User currentUser = (User) principalCollection.getPrimaryPrincipal();
        //判断当前用户是否拥有权限,这些数据是通过认证从数据库中查出来的
        if(currentUser.getPermission()!=null&&!currentUser.getPermission().isEmpty())
        {
            //如果有权限，在授权信息对象中添加这些权限
            List<String> permissions = Arrays.asList(currentUser.getPermission().split(","));
            authorizationInfo.addStringPermissions(permissions);
        }else {
            //没有权限就直接返回null
            return null;
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //将令牌转换为用户名密码并封装
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //判断是否存在当前用户的用户名
        if(token.getUsername().isEmpty()||token.getUsername()==null)
        {
            return null;
        }
        //从数据库中查询进行认证
        User currentUser = userService.findUserByName(token.getUsername());
        if(currentUser==null)
        {
            return null;
        }
        return new SimpleAuthenticationInfo(currentUser,currentUser.getPassword(),"CustomRealm");
    }
}
