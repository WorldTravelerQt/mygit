package com.scxh.springbootshiro.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @Author: 乔童
 * @Description: druid配置类
 * @Date: 2019/12/12 14:56
 * @Version: 1.0
 */
@Configuration
public class DruidConfig{
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid()
    {
        return new DruidDataSource();
    }
    @Bean
    public ServletRegistrationBean statViewResolver()
    {
        ServletRegistrationBean bean = new ServletRegistrationBean<>(new StatViewServlet(),"/druid/*");
        //登录druid后台的用户名和密码
        HashMap<String,String> map=new HashMap<>();
        map.put("loginUsername","admin");
        map.put("loginPassword","root");
        //运行谁都可以访问，不添加ip黑名单白名单
        map.put("allow","");
        bean.setInitParameters(map);

        return bean;
    }
}
