package com.lagousocket.socketdemo.review;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 商玉
 * @create 2021/12/13
 * @since 1.0.0
 */
public class ReviewNioServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel open = ServerSocketChannel.open();
        open.bind(new InetSocketAddress(9998));
        open.configureBlocking(false);
        while (true){
            SocketChannel accept = open.accept();
            if (accept==null){
                System.out.println("没有客户端");
                Thread.sleep(2000);
                continue;
            }
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            int read = accept.read(allocate);
            System.out.println(new String(allocate.array(),0,read, StandardCharsets.UTF_8));
            accept.write(ByteBuffer.wrap("没钱，滚".getBytes(StandardCharsets.UTF_8)));
            accept.close();
        }

    }


}
