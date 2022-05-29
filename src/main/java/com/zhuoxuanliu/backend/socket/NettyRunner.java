package com.zhuoxuanliu.backend.socket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class NettyRunner implements CommandLineRunner {

    //netty服务端口，配置文件中为1254
    @Value("${netty.port}")
    Integer nettyPort;

    @Override
    public void run(String... args) throws Exception {
        try {
            NettyServer nettyServer = new NettyServer(nettyPort);
            new Thread(nettyServer::start).start();
        } catch (Exception e) {
            System.out.println("NettyServerError:" + e.getMessage());
        }
    }
}
