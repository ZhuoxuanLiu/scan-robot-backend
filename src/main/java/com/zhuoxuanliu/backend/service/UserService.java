package com.zhuoxuanliu.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuoxuanliu.backend.pojo.User;

public interface UserService extends IService<User> {

    IPage<User> getPage(Integer current, Integer size);

}
