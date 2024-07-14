package com.github.houbb.nginx4j.support.request.dispatch.http;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

public abstract class AbstractNginxRequestDispatchFullResp extends AbstractNginxRequestDispatch {

    private static final Log logger = LogFactory.getLog(AbstractNginxRequestDispatchFullResp.class);

    protected abstract FullHttpResponse buildFullHttpResponse(final FullHttpRequest request,
                                                              final NginxConfig nginxConfig,
                                                              final NginxRequestDispatchContext context);

    public void doDispatch(final NginxRequestDispatchContext context) {
        final FullHttpRequest request = context.getRequest();
        final NginxConfig nginxConfig = context.getNginxConfig();

        // 响应
        FullHttpResponse response = buildFullHttpResponse(request, nginxConfig, context);
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        final ChannelHandlerContext ctx = context.getCtx();

        // 结果响应
        ChannelFuture lastContentFuture = super.writeAndFlush(ctx, response, context);
        //如果不支持keep-Alive，服务器端主动关闭请求
        if (!HttpUtil.isKeepAlive(request)) {
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }

        // 完成
        super.beforeComplete(ctx, response, context);
        logger.info("[Nginx] channelRead writeAndFlush DONE response={}", response);
    }

}
