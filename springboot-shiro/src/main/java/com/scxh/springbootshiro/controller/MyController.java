package com.scxh.springbootshiro.controller;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Author: 乔童
 * @Date: 2019/12/11 20:33
 * @Version: 1.0
 */
@Controller
public class MyController {
    private Logger logger= Logger.getLogger(MyController.class);
    @RequestMapping({"/","/index"})
    public String index()
    {
        return "index";
    }
    @RequestMapping("user/add")
    public String toAdd()
    {
        return "user/add";
    }
    @RequestMapping("user/update")
    public String toUpdate()
    {
        return "user/update";
    }

    /**
     * 登录页面
     */
    @RequestMapping("/toLogin")
    public String toLogin()
    {
        return "login";
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session)
    {
        System.out.println("登录中...");
        //1.获取当前的用户
        Subject currentUser= SecurityUtils.getSubject();
        //2.封装用户登录的数据
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        //执行登录认证
        try {
            currentUser.login(token);
            logger.info("用户：["+currentUser.getPrincipal()+"]通过了认证");
            session.setAttribute("loginUser",token);
            session.setAttribute("sayHello","hello "+token.getUsername());
            return "index";
        } catch (UnknownAccountException e) {
            model.addAttribute("error","用户名错误！");
            return "login";
        }catch (IncorrectCredentialsException e) {
            model.addAttribute("error","密码错误！");
            return "login";
        }catch (AuthenticationException e) {
            model.addAttribute("error","账号已被锁定");
            return "login";
        }
    }

    /**
     * 注销
     */
    @RequestMapping("/logout")
    public String logout()
    {
        Subject currentUser=SecurityUtils.getSubject();
        logger.info("用户["+currentUser.getPrincipal()+"]注销了");
        currentUser.logout();
        return "redirect:index";
    }

    /**
     * 未授权页面
     */
    @RequestMapping("/unauth")
    @ResponseBody
    public String unauthorized()
    {
        return "未经授权无法访问次页面";
    }
}
