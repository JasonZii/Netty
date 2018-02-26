package com.jason.netty.fire.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author : jasonzii @Author
 * @Description : 使用fireChannelActive，传递handler。使用了Bytebuf
 * @CreateDate : 18.2.9  17:22
 */
public class Client {

    static final String HOST = System.getProperty("host","127.0.0.1");
    static final int POST = Integer.parseInt(System.getProperty("post","8080"));
    static final int SIZE = Integer.parseInt(System.getProperty("size","256"));

    public static void main(String[] args) throws Exception{

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("decoder",new StringDecoder());
                            ch.pipeline().addLast("encoder",new StringEncoder());
                            ch.pipeline().addLast(new BaseClient1Handler());
                            ch.pipeline().addLast(new BaseClient2Handler());
                        }
                    });

            ChannelFuture future = bootstrap.connect(HOST, POST).sync();
            future.channel().writeAndFlush("你好，服务器端");
            future.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }
}
