package com.lagousocket.socketdemo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * 〈一句话功能简述〉<br>
 * 〈客户端〉
 *
 * @author 商玉
 * @create 2021/12/9
 * @since 1.0.0
 */
public class NioClient {
    public static void main(String[] args) throws IOException {
        //打开通道
        SocketChannel open = SocketChannel.open();
    //    设置连接ip和端口号
        open.connect(new InetSocketAddress("127.0.0.1",9998));

        open.write(ByteBuffer.wrap("老板，该还钱了".getBytes(StandardCharsets.UTF_8)));

        ByteBuffer allocate = ByteBuffer.allocate(1024);
        int read = open.read(allocate);
        System.out.println("服务端消息："+new String(allocate.array(),0,read,StandardCharsets.UTF_8));
        open.close();
    }
}
