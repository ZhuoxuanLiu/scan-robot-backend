package com.zhuoxuanliu.backend.rabbitmq;

import lombok.Data;


@Data
public class BasicMqMessage {
    /**
     * 是否需要刷新请求
     */
    private Integer refresh;

}
