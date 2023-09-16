package com.example.nettyrpc;

import com.example.nettyrpc.server.RpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class NettyRpcApplication implements CommandLineRunner {

    @Autowired
    RpcServer rpcServer;


    public static void main(String[] args) {
        SpringApplication.run(NettyRpcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                rpcServer.startServer("127.0.0.1",8899);
            }
        }).start();
    }
}
