package com.lagousocket.socketdemo.review;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * 〈一句话功能简述〉<br>
 * 〈选择器〉
 *
 * @author 商玉
 * @create 2021/12/13
 * @since 1.0.0
 */
public class ReviewNioSelectorServer {

    /**
     * 服务器实现步骤：
     * 打开一个服务端通道
     * 绑定对应的端口号
     * 设置为非阻塞
     * 创建选择器
     * 将服务端通道注册到选择器上，并指定注册监听的事件为op_accept
     * 检查选择器是否有事件
     * 获取事件集合
     * 判断事件是否是客户端连接事件
     * 得到客户端通道，读取数据到缓冲区
     * 给客户端回写数据
     * 从集合中删除对应的事件，因为防止二次处理
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        Selector open = Selector.open();
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.bind(new InetSocketAddress(9998));
        socketChannel.register(open, SelectionKey.OP_ACCEPT);
        while (true) {
            int select = open.select(2000);
            if (select == 0) {
                System.out.println("没绑定");
                continue;
            }
            Set<SelectionKey> selectionKeys = open.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isAcceptable()) {
                    SocketChannel accept = socketChannel.accept();
                    System.out.println("客户端已连接。。。。。" + open);
                    //必须设置通道为非阻塞，因为selector需要轮询监听每个通道的事件
                    accept.configureBlocking(false);
                    //    指定监听事件为op_read  读就绪
                    accept.register(open, SelectionKey.OP_READ);
                }
                if (next.isReadable()) {
                    SocketChannel channel = (SocketChannel) next.channel();
                    ByteBuffer allocate = ByteBuffer.allocate(1024);
                    int read = channel.read(allocate);
                    System.out.println(new String(allocate.array(), 0, read, StandardCharsets.UTF_8));
                    channel.write(ByteBuffer.wrap("没钱，滚".getBytes(StandardCharsets.UTF_8)));
                    channel.close();
                }
            }
            iterator.remove();
        }
    }
}
