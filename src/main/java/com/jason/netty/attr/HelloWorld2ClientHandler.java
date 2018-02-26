package com.jason.netty.attr;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;

import java.util.Date;

import static com.jason.netty.attr.AttributeMapConstant.NETTY_CHANNEL_KEY;

/**
 * @Author : jasonzii @Author
 * @Description :
 * @CreateDate : 18.2.26  11:50
 */
public class HelloWorld2ClientHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Attribute<NettyChannel> attr = ctx.channel().attr(NETTY_CHANNEL_KEY);
        NettyChannel nChannel = attr.get();
        if(nChannel == null){
            NettyChannel newNChannel = new NettyChannel("HelloWorld2Client",new Date());
            nChannel = attr.setIfAbsent(newNChannel);
        }else{
            System.out.println("channelActive attributeMap 中是有值的");
            System.out.println(nChannel.getName() + "======" + nChannel.getCreateDate());
        }
        System.out.println("HelloWorld2clientHandler active");
        ctx.fireChannelActive();

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Attribute<NettyChannel> attr = ctx.channel().attr(NETTY_CHANNEL_KEY);
        NettyChannel nChannel = attr.get();
        if(nChannel == null){
            NettyChannel newNChannel = new NettyChannel("HelloWorld0Client",new Date());
            nChannel = attr.setIfAbsent(newNChannel);
        }else{
            System.out.println("channelActive attributeMap 中是有值的");
            System.out.println(nChannel.getName() + "======" + nChannel.getCreateDate());
        }
        System.out.println("HelloWorldclientHandler read Message:" + msg);
        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
