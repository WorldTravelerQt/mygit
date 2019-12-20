package com.scxh.springbootshiro.dao;

import com.scxh.springbootshiro.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: 乔童
 * @Description: 用户映射器
 * @Date: 2019/12/12 10:39
 * @Version: 1.0
 */
@Mapper
@Repository
public interface UserMapper {
    List<User> findAll();
    User findUserByName(String username);
}
