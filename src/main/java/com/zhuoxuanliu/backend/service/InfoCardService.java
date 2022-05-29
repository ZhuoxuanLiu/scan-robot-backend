package com.zhuoxuanliu.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuoxuanliu.backend.pojo.InfoCard;


public interface InfoCardService extends IService<InfoCard> {
    InfoCard getInfoCard();
}
