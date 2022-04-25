package com.zhuoxuanliu.backend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhuoxuanliu.backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {

}
