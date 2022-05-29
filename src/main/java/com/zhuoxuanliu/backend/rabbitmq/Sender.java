package com.zhuoxuanliu.backend.rabbitmq;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    @Autowired
    private RabbitTemplate template;

    public void send(String exchangeName, String ms, String key) {
        template.convertAndSend(exchangeName, key, ms);
        System.out.println(" [x] Sent '" + ms + "'");
    }

}
