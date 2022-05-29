package com.zhuoxuanliu.backend.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
@Slf4j
public class RabbitQueueServiceImpl implements RabbitQueueService{
    @Resource
    private RabbitAdmin rabbitAdmin;
    @Resource
    private MyMessageListener myMessageListener;
    @Resource
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;
    @Resource
    public RabbitListenerEndpointRegistrar registrar;

    @Override
    public void addNewQueue(String queueName, String exchangeName, String routingKey) {
        Queue queue = new Queue(queueName, true, false, false);
        DirectExchange directExchange = new DirectExchange(exchangeName);
        Binding binding = new Binding(
                queueName,
                Binding.DestinationType.QUEUE,
                exchangeName,
                routingKey,
                null
        );
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(directExchange);
        rabbitAdmin.declareBinding(binding);
        if (!checkQueueExistOnListener(queueName, queueName)) {
            SimpleRabbitListenerEndpoint rabbitListenerEndpoint = new SimpleRabbitListenerEndpoint();
            rabbitListenerEndpoint.setId(queueName);
            rabbitListenerEndpoint.setQueues(queue);
            rabbitListenerEndpoint.setMessageListener(myMessageListener);
            registrar.registerEndpoint(rabbitListenerEndpoint);
        } else {
            log.info("given queue name : " + queueName + " exist on given listener id : " + queueName);
        }
    }

    @Override
    public void removeQueueFromListener(String listenerId, String queueName) {
        if (checkQueueExistOnListener(listenerId, queueName)) {
            getMessageListenerContainerById(listenerId).removeQueueNames(queueName);
            log.info("deleting queue from rabbit management");
            this.rabbitAdmin.deleteQueue(queueName);
        } else {
            log.info("given queue name : " + queueName + " not exist on given listener id : " + listenerId);
        }
    }

    @Override
    public boolean checkQueueExistOnListener(String listenerId, String queueName) {
        try {
            log.info("checking queueName : " + queueName + " on listener id : " + listenerId);
            if (getMessageListenerContainerById(listenerId) != null) {
                String[] queueNames = getMessageListenerContainerById(listenerId).getQueueNames();
                if (queueNames.length > 0) {
                    for (String name : queueNames) {
                        if (name.equals(queueName)) {
                            log.info("queue name exist on listener, returning true");
                            return true;
                        }
                    }
                } else {
                    log.info("there is no queue exist on listener");
                }
            }
            log.info("there is no queue exist on listener");
            return false;
        } catch (Exception e) {
            log.error("Error on checking queue exist on listener");
            return false;
        }
    }
    private AbstractMessageListenerContainer getMessageListenerContainerById(String listenerId) {
        return (AbstractMessageListenerContainer) rabbitListenerEndpointRegistry.getListenerContainer(listenerId);
    }
}
