package com.zhuoxuanliu.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhuoxuanliu.backend.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}

