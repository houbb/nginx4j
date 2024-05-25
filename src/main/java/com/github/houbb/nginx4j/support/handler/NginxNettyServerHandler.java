package com.github.houbb.nginx4j.support.handler;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatch;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;

/**
 * netty 处理类
 * @author 老马啸西风
 * @since 0.2.0
 */
public class NginxNettyServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Log logger = LogFactory.getLog(NginxNettyServerHandler.class);

    private final NginxConfig nginxConfig;

    public NginxNettyServerHandler(NginxConfig nginxConfig) {
        this.nginxConfig = nginxConfig;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        logger.info("[Nginx] channelRead writeAndFlush start request={}", request);

        // 分发
        final NginxRequestDispatch requestDispatch = nginxConfig.getNginxRequestDispatch();
        FullHttpResponse response = requestDispatch.dispatch(request, nginxConfig);

        // 结果响应
        ChannelFuture lastContentFuture = ctx.writeAndFlush(response);
        //如果不支持keep-Alive，服务器端主动关闭请求
        if (!HttpUtil.isKeepAlive(request)) {
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }
        logger.info("[Nginx] channelRead writeAndFlush DONE response={}", response);
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
