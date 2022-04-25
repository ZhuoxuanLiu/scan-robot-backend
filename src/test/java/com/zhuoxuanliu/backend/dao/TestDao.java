package com.zhuoxuanliu.backend.dao;

import com.zhuoxuanliu.backend.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestDao {

    @Autowired
    private UserDao userDao;

    @Test
    void testGetById(){
        System.out.println(userDao.selectById(1));
    }

    @Test
    void addUser(){
        User user = new User(null, "yang", "yang@qq.com", "female", null, null, null, null);
        userDao.insert(user);
    }
}
