package com.jason.netty.idle;

import io.netty.channel.ChannelHandler;

/**
 * @Author : jasonzii @Author
 * @Description : 客户端的ChannelHandler集合，由子类实现，这样的好处
 * 继承这个接口的所有子类可以很方便地获取 ChannelPipeline中的Handlers
 * 获取到handlers之后方便ChannelPipeline中的handler的初始化和在重连的时候也很方便
 * 地获取所有的handlers
 * @CreateDate : 18.2.20  19:14
 */
public interface ChannelHandlerHolder {

    ChannelHandler[] handlers();

}
