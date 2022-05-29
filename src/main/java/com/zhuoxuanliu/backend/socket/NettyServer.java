package com.zhuoxuanliu.backend.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;


public class NettyServer {

    private final int port;

    public NettyServer(int port) {
        this.port = port;
    }


    public void start() {

        //创建两个线程组boosGroup和workerGroup,含有的子线程NioEventLoop的个数默认为cpu核数的两倍
        //boosGroup只是处理链接请求,真正的和客户端业务处理,会交给workerGroup完成
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务器的启动对象
            ServerBootstrap bootstrap = new ServerBootstrap();
            //使用链式编程来配置参数
            //设置两个线程组
            bootstrap.group(boosGroup, workerGroup)
                    //使用NioSctpServerChannel作为服务器的通道实现
                    .channel(NioServerSocketChannel.class)
                    //初始化服务器链接队列大小，服务端处理客户端链接请求是顺序处理的，所以同一时间只能处理一个客户端链接
                    //多个客户端同时来的时候，服务端将不能处理的客户端链接请求放在队列中等待处理
                    .option(ChannelOption.SO_BACKLOG, 512)
                    //创建通道初始化对象，设置初始化参数
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new IdleStateHandler(120, 120, 0, TimeUnit.SECONDS));
                            ch.pipeline().addLast(new MyIdleStateHandler());
                            //websocket协议本身是基于http协议的，所以这边也要使用http解编码器
                            ch.pipeline().addLast(new HttpServerCodec());
                            //以块的方式来写的处理器
                            ch.pipeline().addLast(new ChunkedWriteHandler());
                            ch.pipeline().addLast(new HttpObjectAggregator(8192));
                            ch.pipeline().addLast(new MessageHandler());//添加测试的聊天消息处理类
                            ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws/admin", null, true, 65536 * 10));
                        }
                    });
            System.out.println("netty server start..");
            //绑定一个端口并且同步，生成一个ChannelFuture异步对象，通过isDone()等方法判断异步事件的执行情况
            //启动服务器(并绑定端口)，bind是异步操作，sync方法是等待异步操作执行完毕
            ChannelFuture cf = bootstrap.bind(this.port).sync();
            //给cf注册监听器,监听我们关心的事件
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (cf.isSuccess()) {
                        System.out.println("监听端口成功");
                    } else {
                        System.out.println("监听端口失败");
                    }
                }
            });
            //对通道关闭进行监听，closeFuture是异步操作,监听通道关闭
            //通过sync方法同步等待通道关闭处理完毕，这里会阻塞等待通道关闭
            cf.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

}