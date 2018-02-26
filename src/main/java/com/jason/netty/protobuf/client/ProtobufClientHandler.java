package com.jason.netty.protobuf.client;

import com.jason.netty.protobuf.demo.PlayerModule;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import com.jason.netty.protobuf.demo.PlayerModule.PBPlayer.Builder;

/**
 * @Author : jasonzii @Author
 * @Description :
 * @CreateDate : 18.2.9  18:02
 */
public class ProtobufClientHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ctx.write(play());
        }
        ctx.flush();
    }

    private PlayerModule.PBPlayer play(){
        Builder builder = PlayerModule.PBPlayer.newBuilder();

        builder.setId(007).setAge(21).setName("jason").addSkills(1007);

//		List<String> Skills = new ArrayList<String>();
//		Skills.add("NanJing YuHuaTai");
//		Skills.add("BeiJing LiuLiChang");
//		builder.addAllSkills(null);
//		PBPlayer player = builder.build();

        return builder.build();
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("HelloclientHandler read Message:"+msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
