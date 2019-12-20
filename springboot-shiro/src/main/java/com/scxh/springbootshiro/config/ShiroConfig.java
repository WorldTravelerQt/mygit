package com.scxh.springbootshiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @Author: 乔童
 * @Description: shiro配置类
 * @Date: 2019/12/12 20:13
 * @Version: 1.0
 */
@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(WebSecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(securityManager);

        //设置拦截器
        HashMap<String,String> filterChainDefinitions=new LinkedHashMap<>();
        /*
            权限：
            anon：所有人
            authc：已认证
            user：使用记住我
            perms：资源权限
            role：角色权限
         */
        filterChainDefinitions.put("/user/add","perms[user:add]");
        filterChainDefinitions.put("/user/update","perms[user:update]");
        bean.setFilterChainDefinitionMap(filterChainDefinitions);

        //设置登录url
        bean.setLoginUrl("/toLogin");
        bean.setUnauthorizedUrl("/unauth");
        return bean;
    }

    @Bean("securityManager")
    public WebSecurityManager getWebSecurityManager(Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(realm);
        return securityManager;
    }

    @Bean
    public CustomRealm realm() {
        return new CustomRealm();
    }

    @Bean
    public ShiroDialect getShiroDialect()
    {
        return new ShiroDialect();
    }
}
