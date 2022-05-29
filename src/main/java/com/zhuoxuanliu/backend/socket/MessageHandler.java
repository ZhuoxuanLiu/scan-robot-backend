package com.zhuoxuanliu.backend.socket;


import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //GlobalEventExecutor.INSTANCE 是全局的事件执行器，是一个单例
    public static final ChannelGroup channelGroup=  new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 存储用户id和用户的channelId绑定
     */
    public static ConcurrentHashMap<String, ChannelId> userMap = new ConcurrentHashMap<>();
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端链接完成");
        //添加到group
        channelGroup.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //首次连接是fullHttprequest，，把用户id和对应的channel对象存储起来
        if (msg instanceof FullHttpRequest){
            FullHttpRequest request =(FullHttpRequest) msg;
            //获取用户参数
            System.out.println(request.uri());
            String username = getRequestParams(request.uri());
            //保存到登录信息map
            userMap.put(username,ctx.channel().id());

        }else if (msg instanceof TextWebSocketFrame){
            //正常的text类型
            TextWebSocketFrame frame= (TextWebSocketFrame) msg;
            //转换实体类
            SocketMessage socketMessage = JSON.parseObject(frame.text(), SocketMessage.class);
            System.out.println(socketMessage.getMessageType());
            if (!socketMessage.getMessage().isEmpty()) {
                Map<String, String> messageMap = socketMessage.getMessage();
                System.out.println(messageMap);
            }
        }
        super.channelRead(ctx, msg);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端断开");
        //移除channelGroup 通道组
        channelGroup.remove(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

    }

    private static String getRequestParams(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
