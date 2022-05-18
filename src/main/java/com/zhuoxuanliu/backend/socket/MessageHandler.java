package com.zhuoxuanliu.backend.socket;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;

public class MessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    //GlobalEventExecutor.INSTANCE 是全局的事件执行器，是一个单例
    private static final ChannelGroup channelGroup=  new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 存储用户id和用户的channelId绑定
     */
    public static ConcurrentHashMap<Integer, ChannelId> userMap = new ConcurrentHashMap<>();
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端链接完成");
        //添加到group
        channelGroup.add(ctx.channel());
        ctx.channel().id();

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("收到客户端消息");
        //首次连接是fullHttprequest，，把用户id和对应的channel对象存储起来
        if (msg instanceof FullHttpRequest){
            FullHttpRequest request =(FullHttpRequest) msg;
            //获取用户参数
            Integer userId = getUrlParams(request.uri());
            //保存到登录信息map
            if (userId != null) userMap.put(userId,ctx.channel().id());

            //如果url包含参数，需要处理
            if (request.uri().contains("?")) {
                String newUri = request.uri().substring(0, request.uri().indexOf("?"));
                request.setUri(newUri);
            }

        }else if (msg instanceof TextWebSocketFrame){
            //正常的text类型
            TextWebSocketFrame frame= (TextWebSocketFrame) msg;
            System.out.println("消息内容"+frame.text());
            //转换实体类
            SocketMessage socketMessage = JSON.parseObject(frame.text(), SocketMessage.class);
            if ("group".equals(socketMessage.getMessageType())) {
                //推送群聊信息
                //groupMap.get(socketMessage.getChatId()).writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(socketMessage)));
                System.out.println("推送群聊消息");
            } else {
                //处理私聊的任务，如果对方也在线,则推送消息
                ChannelId channelId = userMap.get(socketMessage.getId());
                if (channelId != null) {
                    Channel ct = channelGroup.find(channelId);
                    if (ct != null) {
                        ct.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(socketMessage)));
                    }
                }
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

    private static Integer getUrlParams(String url) {
        if (!url.contains("/user/")) {
            return null;
        }
        String userId = url.substring(url.indexOf("/user/") + 6);
        return Integer.parseInt(userId);
    }
}
