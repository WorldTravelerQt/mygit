package com.scxh.springbootshiro.service.impl;

import com.scxh.springbootshiro.dao.UserMapper;
import com.scxh.springbootshiro.pojo.User;
import com.scxh.springbootshiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: 乔童
 * @Description:
 * @Date: 2019/12/12 10:50
 * @Version: 1.0
 */
@Transactional(propagation = Propagation.REQUIRED,readOnly = false)
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public User findUserByName(String username) {
        return userMapper.findUserByName(username);
    }
}
