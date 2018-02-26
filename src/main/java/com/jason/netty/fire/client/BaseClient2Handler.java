package com.jason.netty.fire.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class BaseClient2Handler extends ChannelInboundHandlerAdapter {

    private byte[] req;

    public BaseClient2Handler() {
       // req = ("jasonzii lalalala").getBytes();
        req = ("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa&&_" +
                "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb&&_" +
                "ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc&&_" +
                "ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd&&_" +
                System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //定义一个空的ByteBuf
        ByteBuf message = null;

       /* for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }*/

        //设置ByteBuf所占的字符个数
        message = Unpooled.buffer(req.length);
        //把字符数组写入ByteBuf
        message.writeBytes(req);
        ctx.writeAndFlush(message);

        System.out.println("第二个Handler处理器");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
