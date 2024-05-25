package com.github.houbb.nginx4j.support.handler;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.support.request.convert.NginxRequestConvertor;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatch;
import com.github.houbb.nginx4j.support.request.dto.NginxRequestInfoBo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * netty 处理类
 * @author 老马啸西风
 * @since 0.2.0
 */
public class NginxNettyServerHandler extends ChannelInboundHandlerAdapter {

    private static final Log logger = LogFactory.getLog(NginxNettyServerHandler.class);

    private final NginxConfig nginxConfig;

    public NginxNettyServerHandler(NginxConfig nginxConfig) {
        this.nginxConfig = nginxConfig;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String requestString = new String(bytes, nginxConfig.getCharset());
        logger.info("[Nginx] channelRead requestString={}", requestString);

        // 请求体
        final NginxRequestConvertor requestConvertor = nginxConfig.getNginxRequestConvertor();
        NginxRequestInfoBo nginxRequestInfoBo = requestConvertor.convert(requestString, nginxConfig);

        // 分发
        final NginxRequestDispatch requestDispatch = nginxConfig.getNginxRequestDispatch();
        String respText = requestDispatch.dispatch(nginxRequestInfoBo, nginxConfig);

        // 结果响应
        ByteBuf responseBuf = Unpooled.copiedBuffer(respText.getBytes());
        ctx.writeAndFlush(responseBuf)
                .addListener(ChannelFutureListener.CLOSE); // Close the channel after sending the response
        logger.info("[Nginx] channelRead writeAndFlush DONE");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("[Nginx] exceptionCaught", cause);
        ctx.close();
    }

}
