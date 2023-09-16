package com.lagousocket.socketdemo.review;

import com.lagousocket.socketdemo.nettyServer.NettyServerHandle;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 〈一句话功能简述〉<br>
 * 〈netty服务端复习〉
 *
 * @author 商玉
 * @create 2021/12/15
 * @since 1.0.0
 */
public class ReviewNettyServer {

    /**
     * 服务端部署步骤：
     * 创建bossGroup线程组： 处理网络事件 - 连接事件
     * 创建workGroup线程组： 读写事件
     * 创建服务端启动助手
     * 设置线程组
     * 设置服务端通道实现为nio
     * 参数设置
     * 创建一个通道初始化对象
     * 向pipeline中添加自定义业务对象处理handler
     * 启动服务端并绑定端口，同时将异步改为同步
     * 关闭通道和关闭连接池
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,128)
                .childOption(ChannelOption.SO_KEEPALIVE,Boolean.TRUE)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyServerHandle());
                    }
                });
        ChannelFuture sync = serverBootstrap.bind(9999);
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
        sync.channel().closeFuture().sync();
        workGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
