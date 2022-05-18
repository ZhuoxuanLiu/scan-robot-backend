package com.zhuoxuanliu.backend.socket;


import lombok.Data;

@Data
public class SocketMessage {

    /**
     * 消息类型
     */
    private String messageType;
    /**
     * 消息id
     */
    private Integer id;
    /**
     * 消息内容
     */
    private String message;

}