package com.zhuoxuanliu.backend.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zhuoxuanliu.backend.pojo.User;
import com.zhuoxuanliu.backend.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TestService {

    @Autowired
    private UserServiceImpl userService;

    @Test
    void testGetById(){
        System.out.println(userService.getById(1));
    }

    @Test
    void addUser(){
//        User user = new User(null, "yang", "yang@qq.com", "female", null, null, null, null);
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>().set(true, "name", "liu");
//        updateWrapper.set(true, "name", "liu");
        userService.update(updateWrapper);
    }
}
