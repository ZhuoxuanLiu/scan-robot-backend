package com.zhuoxuanliu.backend.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuoxuanliu.backend.mapper.InfoCardMapper;
import com.zhuoxuanliu.backend.pojo.InfoCard;
import com.zhuoxuanliu.backend.service.InfoCardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class InfoCardServiceImpl extends ServiceImpl<InfoCardMapper, InfoCard> implements InfoCardService {

    @Resource
    private InfoCardMapper infoCardMapper;

    @Override
    public InfoCard getInfoCard() {
        return infoCardMapper.selectById(1);
    }
}
