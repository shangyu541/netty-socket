package com.lagousocket.socketdemo.nettyClient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 商玉
 * @create 2021/12/14
 * @since 1.0.0
 */
public class NettyClientHandler implements ChannelInboundHandler {

    @Override
    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    /**
     * 通道就绪事件
     * @param channelHandlerContext
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        /**
         * 将数据写道channel通道中，当前channelhandler的下一个channelhandler开始处理（出站）
         */
        channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("netty客户端", StandardCharsets.UTF_8));

        /**
         * 粘包
         */
        //for (int i = 0; i <10 ; i++) {
        //    channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("netty客户端"+i, StandardCharsets.UTF_8));
        //}

        /**
         * 拆包
         */
        //byte[] bytes = new byte[102400];
        //Arrays.fill(bytes, (byte) 10);
        //for (int i = 0; i <10 ; i++) {
        //    channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer(bytes));
        //}

    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        ByteBuf byteBuf= (ByteBuf) o;
        System.out.println("服务端发来消息："+byteBuf.toString(StandardCharsets.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {

    }
}
