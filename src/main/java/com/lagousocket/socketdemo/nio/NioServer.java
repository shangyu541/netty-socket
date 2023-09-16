package com.lagousocket.socketdemo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * 〈一句话功能简述〉<br>
 * 〈服务端〉
 *
 * @author 商玉
 * @create 2021/12/9
 * @since 1.0.0
 */
public class NioServer {

    /**
     * 服务端实现步骤
     * <p>
     * 打开一个服务端通道，
     * 绑定对应的端口号
     * 通道默认是阻塞的，需要设置为非阻塞
     * 检查是否有客户端连接 有客户端连接会返回对应的通道
     * 获取客户端传递过来的数据，并把数据放在byteBuffer这个缓冲区中
     * 给客户端回写数据
     * 释放资源
     *
     * @param args
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel open = ServerSocketChannel.open();
        //绑定对应的端口号
        open.bind(new InetSocketAddress(9998));
        //通道默认是阻塞的，需要设置为非阻塞
        open.configureBlocking(false);
        System.out.println("服务端启动成功-------------");
        while (true) {
            SocketChannel accept = open.accept();
            if (accept == null) {
                System.out.println("没有客户端连接");
                Thread.sleep(2000);
                continue;
            }

            //获取客户端传递过来的数据，并把数据放在byteBuffer这个缓冲区中
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            //    返回值：
            //   正数：表示本次督导的有效字节个数
            //    0：表示本次没有读到有效字节
            //    -1：表示读到了末尾
            int read = accept.read(allocate);
            System.out.println("客户端消息："+
                    new String(allocate.array(),0,read, StandardCharsets.UTF_8)
                    );
            accept.write(ByteBuffer.wrap("没钱".getBytes("UTF-8")));
            accept.close();
        }

    }

}
