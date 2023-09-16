package com.lagousocket.socketdemo.nio;

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
 * @create 2021/12/9
 * @since 1.0.0
 */
public class NioSelectorServer {
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
        //打开服务器
        ServerSocketChannel open = ServerSocketChannel.open();
        //绑定ip
        open.bind(new InetSocketAddress(9998));
        //设置非阻塞
        open.configureBlocking(false);
        //创建选择器
        Selector selector = Selector.open();
        //将服务器通道注册到选择器上，并指定注册监听的事件为就绪
        open.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务启动成功");
        while (true) {
            //检查选择器是否有事件
            int select = selector.select(2000);
            if (select == 0) {
                continue;
            }
            //获取事件集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isAcceptable()) {
                    SocketChannel accept = open.accept();
                    System.out.println("客户端已连接。。。。。" + open);
                    //必须设置通道为非阻塞，因为selector需要轮询监听每个通道的事件
                    accept.configureBlocking(false);
                    //    指定监听事件为op_read  读就绪
                    accept.register(selector, SelectionKey.OP_READ);
                }
                //判断是否客户端读就绪事件
                if (next.isReadable()) {
                    //    得到客户端通道，读取数据到缓冲区
                    SocketChannel channel = (SocketChannel) next.channel();
                    ByteBuffer allocate = ByteBuffer.allocate(1024);
                    int read = channel.read(allocate);
                    if (read > 0) {
                        System.out.println("客户端消息：" + new String(allocate.array(), 0, read, StandardCharsets.UTF_8));
                        //给客户端回写数据
                        channel.write(ByteBuffer.wrap("没钱".getBytes(StandardCharsets.UTF_8)));
                        open.close();

                    }
                }
                //从集合中删除对应的事件，因为防止二次处理
                iterator.remove();
            }
        }
    }
}
