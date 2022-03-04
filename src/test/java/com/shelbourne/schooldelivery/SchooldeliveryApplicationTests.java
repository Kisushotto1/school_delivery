package com.shelbourne.schooldelivery;

import com.shelbourne.schooldelivery.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SchooldeliveryApplicationTests {

//    @Autowired
//    private UserMapper userMapper;

    @Test
    void contextLoads() {
//        System.out.println(userMapper.findAll());
        System.out.println(fun());
    }

    public int fun() {
        try {
            int a = 4 / 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("cdsbvbshj");
        return 12345;
    }
}
