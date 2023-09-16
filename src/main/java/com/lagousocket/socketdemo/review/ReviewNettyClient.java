package com.lagousocket.socketdemo.review;

import com.lagousocket.socketdemo.nettyClient.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 〈一句话功能简述〉<br>
 * 〈netty客户端〉
 *
 * @author 商玉
 * @create 2021/12/15
 * @since 1.0.0
 */
public class ReviewNettyClient {

    /**
     * 客户端部署流程
     * 创建线程组
     * 创建客户端启动助手
     * 设置线程足
     * 设置客户端通道实现nio
     * 创建一个通道初始化对象
     * 向管道中添加自定义业务处理handler
     * 启动客户端，等待连接服务器，同时将异步改为同步
     * 关闭通道和连接池
     *
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossLoopGroup=new NioEventLoopGroup();
        Bootstrap serverBootstrap = new Bootstrap();
        serverBootstrap.group(bossLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyClientHandler());
                    }
                });
        ChannelFuture sync = serverBootstrap.connect("127.0.0.1",9999);
        sync.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()){
                    System.out.println("绑定成功");
                }else {
                    System.out.println("绑定失败");
                }
            }
        });
        sync.channel().closeFuture().sync();
        bossLoopGroup.shutdownGracefully();
    }
}
