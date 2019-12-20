package com.scxh.springbootshiro.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 乔童
 * @Description: 用户实体类
 * @Date: 2019/12/12 10:37
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer uid;
    private String username;
    private String password;
    private String permission;
}
