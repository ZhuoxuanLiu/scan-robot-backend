package com.zhuoxuanliu.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhuoxuanliu.backend.pojo.User;
import com.zhuoxuanliu.backend.rabbitmq.RabbitQueueService;
import com.zhuoxuanliu.backend.service.UserService;
import com.zhuoxuanliu.backend.utils.R;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RabbitQueueService rabbitQueueService;

    @Value("${my-rabbit-queues.name}")
    private String[] queueNames;

    @PostMapping("/login/account")
    public LoginResponse getUserQueue(@RequestBody JSONObject jsonObject){
        System.out.println(jsonObject);
        String username = jsonObject.getString("username");
        String type = jsonObject.getString("type");
        LoginResponse response = new LoginResponse("ok", type, "admin");
        for (String queueName : queueNames) {
            rabbitQueueService.addNewQueue(queueName + "_" + username, username, queueName);
        }
        return response;
    }

    @GetMapping("/users/page/{current}/{size}")
    public R getPage(@PathVariable Integer current, @PathVariable Integer size){
        IPage<User> page = userService.getPage(current, size);
        if (current > page.getPages()) {
            page = userService.getPage((int)page.getPages(), size);
        }
        return new R(true, page);
    }

}
@Data
@AllArgsConstructor
class LoginResponse {
    private String status;
    private String type;
    private String currentAuthority;
}