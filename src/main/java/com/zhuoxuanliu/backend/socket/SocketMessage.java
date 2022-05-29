package com.zhuoxuanliu.backend.socket;


import lombok.Data;

import java.util.Map;

@Data
public class SocketMessage {

    /**
     * 消息类型
     */
    private String messageType;
    /**
     * 消息username
     */
    private String username;
    /**
     * 消息内容
     */
    private Map<String, String> message;

}