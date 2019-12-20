package com.scxh.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @Author: 乔童
 * @Description: 自定义realm测试
 * @Date: 2019/12/11 19:57
 * @Version: 1.0
 */
public class CustomRealmTest {
    @Test
    public void Authentication()
    {
        /*
            1.创建默认SecurityManager对象
                设置securityManager环境
                设置realm
         */
        CustomRealm realm=new CustomRealm();
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        defaultSecurityManager.setRealm(realm);
        /*
            2.主体发送认证请求
                需要一个token
         */
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("mark", "123");
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        /*
            subject.hasRole方法，判断该账户是否拥有指定权限
         */
        String msg = subject.isAuthenticated() ? "认证成功" : "认证失败";
        System.out.println(msg);
    }
}
