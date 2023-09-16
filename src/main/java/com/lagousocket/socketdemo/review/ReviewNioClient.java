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
public class ReviewNioClient {

    public static void main(String[] args) throws IOException {
        SocketChannel open = SocketChannel.open();
        open.connect(new InetSocketAddress("127.0.0.1",9998));
        open.write(ByteBuffer.wrap("狗比，给钱".getBytes(StandardCharsets.UTF_8)));
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        int read = open.read(allocate);
        System.out.println(new String(allocate.array(),0,read,StandardCharsets.UTF_8));
        open.close();
    }

}
