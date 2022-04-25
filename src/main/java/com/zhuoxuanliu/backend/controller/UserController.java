package com.zhuoxuanliu.backend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhuoxuanliu.backend.pojo.User;
import com.zhuoxuanliu.backend.service.UserService;
import com.zhuoxuanliu.backend.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("{id}")
    public R getUserById(@PathVariable Integer id){
        return new R(true, userService.getById(id));
    }

    @GetMapping("{current}/{size}")
    public R getUserById(@PathVariable Integer current, @PathVariable Integer size){
        IPage<User> page = userService.getPage(current, size);
        if (current > page.getPages()) {
            page = userService.getPage((int)page.getPages(), size);
        }
        return new R(true, page);
    }
}
