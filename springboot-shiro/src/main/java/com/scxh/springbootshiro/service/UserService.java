package com.scxh.springbootshiro.service;

import com.scxh.springbootshiro.pojo.User;

import java.util.List;

/**
 * @Author: 乔童
 * @Description: 用户业务层
 * @Date: 2019/12/12 10:50
 * @Version: 1.0
 */
public interface UserService {
    List<User> findAll();
    User findUserByName(String username);
}
