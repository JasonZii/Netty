package com.jason.netty.protobuf.server;

import com.jason.netty.protobuf.demo.PlayerModule;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author : jasonzii @Author
 * @Description :
 * @CreateDate : 18.2.9  17:44
 */
public class ProtobufServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //		System.out.println("Server accept client subsrcibe req :["+ msg.toString() + "]");
        System.out.println("11111111111111111111111111111");
       // System.out.println("Server accept client subsrcibe req :["+ msg + "]");
/*		byte[] b = msg.toString().getBytes();
		getBytes(b);*/
    }

/*	private void getBytes(byte[] by) throws Exception{

		PBPlayer player = PlayerModule.PBPlayer.parseFrom(by);

		System.out.println("playId:"+player.getPlayId());
		System.out.println("age:"+player.getAge());
		System.out.println("name:"+player.getName());
		System.out.println("skills:"+(Arrays.toString(player.getSkillsList().toArray())));


	}
	*/


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
