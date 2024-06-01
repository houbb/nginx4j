package com.github.houbb.nginx4j.support.handler;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;
import com.github.houbb.nginx4j.constant.NginxConst;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatch;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

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
        final String id = ctx.channel().id().asLongText();
        logger.info("[Nginx] channelRead writeAndFlush start request={}, id={}", request, id);

        // 分发
        NginxUserServerConfig nginxUserServerConfig = getNginxUserServerConfig(request);

        final NginxRequestDispatch requestDispatch = nginxConfig.getNginxRequestDispatch();
        NginxRequestDispatchContext context = new NginxRequestDispatchContext();
        context.setCtx(ctx);
        context.setNginxConfig(nginxConfig);
        context.setRequest(request);
        context.setCurrentNginxUserServerConfig(nginxUserServerConfig);

        requestDispatch.dispatch(context);

        logger.info("[Nginx] channelRead writeAndFlush DONE id={}", id);
    }

    private NginxUserServerConfig getNginxUserServerConfig(FullHttpRequest request) {
        String hostName = getHostName(request);

        return nginxConfig.getNginxUserConfig().getNginxUserServerConfig(hostName);
    }

    private String getHostName(FullHttpRequest request) {
        return request.headers().get(NginxConst.HEADER_HOST);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("[Nginx] exceptionCaught", cause);
//        ctx.close();
    }

}
