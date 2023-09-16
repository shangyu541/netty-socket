package com.lagousocket.socketdemo.nettyClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 〈一句话功能简述〉<br>
 * 〈netty客户端〉
 *
 * @author 商玉
 * @create 2021/12/14
 * @since 1.0.0
 */
public class NettyClient {
    /**
     * 客户端实现步骤
     * 创建线程组
     * 创建客户端启动助手
     * 设置线程组
     * 设置客户端通道实现nio
     * 创建一个通道初始化对象
     * 想管道中添加自定义业务处理handler
     * 启动客户端，等待连接服务端，同时将异步改为同步
     * 关闭通道和关闭连接池
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        //    创建线程组
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        //    创建客户端启动助手
        Bootstrap serverBootstrap = new Bootstrap();
        //    设置线程组
        serverBootstrap.group(eventExecutors)
                //设置服务端通道实现为nio
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyClientHandler());
                    }
                });
        //    启动客户端，等待连接服务器，同时将异步改为同步
        ChannelFuture connect = serverBootstrap.connect("127.0.0.1", 9999);
        connect.channel().closeFuture().sync();
        //关闭通道和关闭连接池
        eventExecutors.shutdownGracefully();
    }
}
