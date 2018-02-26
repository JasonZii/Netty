package com.jason.netty.heart.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @Author : jasonzii @Author
 * @Description : 心跳demo
 * @CreateDate : 18.2.11  12:14
 */
public class HeartServer {

    private int port;

    public HeartServer(int port) {
        this.port = port;
    }

    public void start(){
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
                    bootstrap.group(boss,worker)
                        .channel(NioServerSocketChannel.class)
                        .option(ChannelOption.SO_BACKLOG,128)
                        .childOption(ChannelOption.SO_KEEPALIVE,true)
                        .handler(new LoggingHandler(LogLevel.INFO))
                        .localAddress(new InetSocketAddress(port))
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline().addLast(new IdleStateHandler(5,0,0, TimeUnit.SECONDS));
                                ch.pipeline().addLast("decoder",new StringDecoder());
                                ch.pipeline().addLast("encoder",new StringEncoder());
                                ch.pipeline().addLast(new HeartServerHandler());
                            }
                        });
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("Server start listen at" + port);

            future.channel().closeFuture().sync();
        }catch (Exception e){
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if(args.length > 0){
            port = Integer.parseInt(args[0]);
        }else{
            port = 8080;
        }
        new HeartServer(port).start();
    }
}
