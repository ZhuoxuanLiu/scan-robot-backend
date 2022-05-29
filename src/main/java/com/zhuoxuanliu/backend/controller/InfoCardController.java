package com.zhuoxuanliu.backend.controller;

import com.zhuoxuanliu.backend.service.InfoCardService;
import com.zhuoxuanliu.backend.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/dashboard")
public class InfoCardController {

    @Resource
    private InfoCardService infoCardService;

    @GetMapping("/info_card")
    public R getUserById(){
        return new R(true, infoCardService.getInfoCard());
    }

}
