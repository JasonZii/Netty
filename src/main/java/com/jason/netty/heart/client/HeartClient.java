package com.jason.netty.heart.client;

import com.jason.netty.heart.server.HeartServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import sun.util.resources.CalendarData_th;

import java.util.concurrent.TimeUnit;

/**
 * @Author : jasonzii @Author
 * @Description : 心跳demo,重连demo
 * @CreateDate : 18.2.11  12:14
 */
public class HeartClient {

    public void connect(final int port, String host) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture future = null;
        try {
            Bootstrap b = new Bootstrap();
                b.group(group)
                 .channel(NioSocketChannel.class)
                 .option(ChannelOption.TCP_NODELAY,true)
                 .handler(new LoggingHandler(LogLevel.INFO))
                 .handler(new ChannelInitializer<SocketChannel>() {
                     protected void initChannel(SocketChannel ch) throws Exception {
                         ChannelPipeline p = ch.pipeline();
                         p.addLast("ping",new IdleStateHandler(0,4,0, TimeUnit.SECONDS));
                         ch.pipeline().addLast("decoder",new StringDecoder());
                         ch.pipeline().addLast("encoder",new StringEncoder());
                         ch.pipeline().addLast(new HeartClientHandler());
                     }
                 });

                future = b.connect(host,port).sync();
                future.channel().closeFuture().sync();

        }finally {
//            group.shutdownGracefully();
            if(null != future){
                if(future.channel() != null && future.channel().isOpen()){
                    future.channel().close();
                }
            }
            System.out.println("准备重连");
            connect(port,host);
            System.out.println("重连成功");
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if(args != null && args.length > 0){
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e){
                //采用默认值
            }
        }
        new HeartClient().connect(port,"127.0.0.1");
    }
}
