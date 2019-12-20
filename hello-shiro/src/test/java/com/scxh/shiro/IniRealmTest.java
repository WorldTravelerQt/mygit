package com.scxh.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @Author: 乔童
 * @Date: 2019/12/11 15:58
 * @Version: 1.0
 */
public class IniRealmTest {
    @Test
    public void testAuthentication()
    {
        IniRealm iniRealm=new IniRealm("classpath:user.ini");
        /*
            1.构建SecurityManager环境
                设置realm
                设置安全环境
         */
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        /*
            2.主体提交认证请求
                创建token
         */
        Subject subject=SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken("Mark","123");
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        String msg = (subject.isAuthenticated() ? "认证成功" : "认证失败")+(subject.hasRole("admin")?"，管理员":"，普通用户");
        try {
            subject.checkPermission("user:delete");
        } catch (AuthorizationException e) {
            System.out.println("没有权限");
        }
        System.out.println(msg);
    }
}
