package com.zhuoxuanliu.backend.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.zhuoxuanliu.backend.socket.MessageHandler;
import com.zhuoxuanliu.backend.socket.RefreshRequest;
import io.netty.channel.ChannelId;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

@Component
public class MyMessageListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        //获取用户id，可以通过队列名称获取
        System.out.println("Receive message: " + new String(message.getBody()));
        String queueName = message.getMessageProperties().getConsumerQueue();
        String username = queueName.substring(queueName.lastIndexOf("_") + 1);
        ChannelId channelId = MessageHandler.userMap.get(username);
        if (channelId != null) {
            io.netty.channel.Channel nettyChannel = MessageHandler.channelGroup.find(channelId);
            if (nettyChannel != null) {
                //如果连接不为空，表示用户在线
                //封装返回数据
                RefreshRequest request = new RefreshRequest(1);
                String refresh = JSONObject.toJSONString(request);
                // 加入时间戳 防止浏览器缓存接收不到消息
                JSONObject obj = JSON.parseObject(refresh);
                obj.put("timestamp", System.currentTimeMillis());
                String frame = JSONObject.toJSONString(obj);
                nettyChannel.writeAndFlush(new TextWebSocketFrame(frame));
                System.out.println("send success!");
            }
        }

    }
}

