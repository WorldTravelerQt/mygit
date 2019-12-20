package com.scxh.shiro;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @Author: 乔童
 * @Description: Jdbc域
 * @Date: 2019/12/11 16:50
 * @Version: 1.0
 */
public class JdbcRealmTest {
    private static DruidDataSource dataSource;

    static {
        dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/test?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }

    @Test
    public void authentication() {
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        //jdbcRealm默认不查询permission(权限)，想要让它查询，就要设置
        jdbcRealm.setPermissionsLookupEnabled(true);
        //设置自定义的认证查询语句
        jdbcRealm.setAuthenticationQuery("select password from shiro_user where username = ?");
        //设置自定义的角色查询语句
        jdbcRealm.setUserRolesQuery("select role from shiro_role where username = ?");
        //设置自定义的权限查询语句
        jdbcRealm.setPermissionsQuery("select permission from shiro_permission where role= ? ");

        /*
            创建SecurityManager
                设置realm
                设置安全环境
         */
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        /*
            创建主体
                创建token
         */
        UsernamePasswordToken token = new UsernamePasswordToken("mark", "123");
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);

        String msg = (subject.isAuthenticated() ? "认证成功" : "认证失败")+(subject.hasRole("admin")?"是管理员":"是普通用户");
        System.out.println(msg);
        try {
            subject.checkPermission("user:delete");
        } catch (AuthorizationException e) {
            System.out.println("没有删除权限");
        }
    }
}