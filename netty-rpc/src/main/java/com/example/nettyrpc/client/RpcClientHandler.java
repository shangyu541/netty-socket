package com.example.nettyrpc.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author 商玉
 * @create 2021/12/24
 * @since 1.0.0
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<String> implements Callable {

    ChannelHandlerContext context;

    //发送的消息
    String requestMsg;

    //服务端的消息
    String responseMsg;

    public void setRequestMsg(String requestMsg){
        this.requestMsg=requestMsg;
    }


    @Override
    protected synchronized void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        responseMsg=s;
        //唤醒等待的线程
        notify();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context=ctx;
    }

    //发送消息到服务端
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(requestMsg);
        wait();
        return responseMsg;
    }
}
