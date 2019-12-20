package com.scxh.shiro;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author: 乔童
 * @Date: 2019/12/11 10:53
 * @Version: 1.0
 */
public class AuthenticationTest {
    private SimpleAccountRealm realm = new SimpleAccountRealm();

    @Before
    public void addAccount() {
        /*
            添加账号，并为该账号添加权限"admin"
         */
        realm.addAccount("mark", "123", "admin");
    }

    @Test
    public void testAuthentication() {
        /*
            1.创建默认SecurityManager对象
                设置securityManager环境
                设置realm
         */
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        defaultSecurityManager.setRealm(realm);
        /*
            2.主体发送认证请求
                需要一个token
         */
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("mark", "123");
        subject.login(token);
        /*
            subject.hasRole方法，判断该账户是否拥有指定权限
         */
        String msg = ((subject.isAuthenticated() ? "认证成功" : "认证失败") + (subject.hasRole("admin")?",具有管理员权限":",没有管理员权限"));
        System.out.println(msg);
    }
}