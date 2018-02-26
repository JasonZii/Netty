package com.jason.netty.heart.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Author : jasonzii @Author
 * @Description :
 * @CreateDate : 18.2.11  12:14
 */
public class HeartServerHandler extends ChannelInboundHandlerAdapter{

    private int loss_connect_time = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if(event.state() == IdleState.READER_IDLE){
                loss_connect_time++;
                System.out.println("5 秒没有接收到客户端信息了");
                if(loss_connect_time > 2){
                    System.out.println("关闭这个不活跃的channel");
                    ctx.channel().close();
                }
            }
        }else{
            super.userEventTriggered(ctx,evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server channelRead...");
        System.out.println(ctx.channel().remoteAddress() + "-> server :" + msg.toString() );
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
