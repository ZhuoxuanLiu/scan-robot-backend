package com.zhuoxuanliu.backend.rabbitmq;


public interface RabbitQueueService {
    void addNewQueue(String queueName, String exchangeName, String routingKey);
    void removeQueueFromListener(String listenerId, String queueName);
    boolean checkQueueExistOnListener(String listenerId, String queueName);
}
