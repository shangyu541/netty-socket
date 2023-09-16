package com.lagousocket.socketdemo.nettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 〈一句话功能简述〉<br>
 * 〈服务端〉
 *
 * @author 商玉
 * @create 2021/12/14
 * @since 1.0.0
 */
public class nettyServer {

    /**
     * 服务端实现步骤：
     * 创建bossGroup线程组：处理网络事件-连接事件
     * 创建workerGroup线程组：处理网络事件-读写事件
     * 创建服务端启动助手
     * 设置bossGroup线程组和workerGroup线程组
     * 设置服务端通道实现为nio
     * 参数设置
     * 创建一个通道初始化对象
     * 想pipeline中添加自定义业务对象处理handler
     * 启动服务端并绑定端口，同时将异步改为同步
     * 关闭通道和关闭连接池
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        //创建bossgroup线程组  连接事件  线程默认数 2* 处理器线程数
        NioEventLoopGroup bossgroup = new NioEventLoopGroup(1);
        //创建workergroup线程组：  读写事件   2* 处理器线程数
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        //创建服务器端启动助手
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //设置线程组
        serverBootstrap.group(bossgroup, workGroup)
                //设置服务端通道实现
                .channel(NioServerSocketChannel.class)
                //设置线程队列中等待连接个数
                .option(ChannelOption.SO_BACKLOG, 128)
                //参数设置 设置活跃状态，child是设置workerGroup
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                //创建一个通道初始化对象
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //添加自定义handler
                        socketChannel.pipeline().addLast(new NettyServerHandle());
                    }
                });
        //启动服务端并绑定端口，同时将异步改为同步
        ChannelFuture sync = serverBootstrap.bind(9999).sync();
        sync.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()){
                    System.out.println("端口绑定成功");
                }else {
                    System.out.println("端口绑定失败");
                }
            }
        });
        System.out.println("服务器启动成功");
        //关闭通道（并不是真正意义上的关闭，而是监听通道关闭状态）和关闭连接
        sync.channel().closeFuture().sync();
        //断开连接，关闭线程
        bossgroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }
}
