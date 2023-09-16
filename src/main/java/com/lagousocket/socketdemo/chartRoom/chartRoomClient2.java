package com.lagousocket.socketdemo.chartRoom;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * 〈一句话功能简述〉<br>
 * 〈聊天室客户端〉
 *
 * @author 商玉
 * @create 2021/12/15
 * @since 1.0.0
 */
public class chartRoomClient2 {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(bossGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new StringDecoder());
                        socketChannel.pipeline().addLast(new StringEncoder());
                        socketChannel.pipeline().addLast(new ChartRoomNettyClient());
                    }
                });
        ChannelFuture connect = bootstrap.connect("127.0.0.1", 9999);
        Channel channel = connect.channel();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            channel.writeAndFlush(s);
        }
        connect.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();

    }
}
