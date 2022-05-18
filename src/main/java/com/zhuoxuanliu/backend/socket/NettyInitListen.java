package com.zhuoxuanliu.backend.socket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class NettyInitListen implements CommandLineRunner {

    //netty服务端口，配置文件中为1254
    @Value("${netty.port}")
    Integer nettyPort;
    //springboot服务端口 8080
    @Value("${server-config.port}")
    Integer serverPort;

    @Override
    public void run(String... args) throws Exception {
        try {
            System.out.println("nettyServer starting ...");
            System.out.println("http://127.0.0.1:" + serverPort);
            new NettyServer(nettyPort).start();
        } catch (Exception e) {
            System.out.println("NettyServerError:" + e.getMessage());
        }
    }
}
