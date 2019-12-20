package com.scxh.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;

/**
 * @Author: 乔童
 * @Description: 自定义Realm
 * @Date: 2019/12/11 19:38
 * @Version: 1.0
 */
public class CustomRealm extends AuthorizingRealm {
    /**
     *  假装是一个数据库
     */
    private static HashMap<String,String> users=new HashMap<>();
    static {
        users.put("mark","123");
    }
    /**
     * 重写授权方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        return null;
    }

    /**
     * 重写认证方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.从主体传过来的认证信息中，获得用户名
        String username= (String) authenticationToken.getPrincipal();

        //2.通过用户名到数据库中获取凭证
        String password=getPassword(username);

        if(password==null||password.isEmpty())
        {
            return null;
        }
        /*
            第一个参数为用户名(principal参数也可以是主键/邮箱/手机或者其他的唯一标识)
            第二个参数为密码
            第三个参数是当前realm的类名
         */
        return new SimpleAuthenticationInfo(username,password,"CustomRealm");
    }

    private String getPassword(String username) {
        return users.get(username);
    }
}
