package com.jason.netty.protobuf.client;

import com.jason.netty.hello.client.HelloClientHandler;
import com.jason.netty.protobuf.demo.PlayerModule;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import com.jason.netty.protobuf.demo.PlayerModule;

/**
 * @Author : jasonzii @Author
 * @Description :
 * @CreateDate : 18.2.9  18:02
 */
public class ProtobufClient {

    public static void main(String[] args) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bs = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        protected void initChannel(Channel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new ProtobufVarint32FrameDecoder());
                            p.addLast(new ProtobufVarint32LengthFieldPrepender());
                            p.addLast(new ProtobufEncoder());
                            p.addLast(new ProtobufClientHandler());
                        }
                    });

            ChannelFuture futrue = bs.connect("127.0.0.1",10100);
            System.out.println("客户端：");
            futrue.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
