package com.zhuoxuanliu.backend.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuoxuanliu.backend.dao.UserDao;
import com.zhuoxuanliu.backend.pojo.User;
import com.zhuoxuanliu.backend.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public IPage<User> getPage(Integer current, Integer size) {
        IPage<User> page = new Page<>(current, size);

        return userDao.selectPage(page, null);
    }
}
