package com.scxh.springbootshiro;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("com.scxh.springboot.dao")
class SpringbootShiroApplicationTests {

    @Test
    void contextLoads() {
    }

}
