package com.github.houbb.nginx4j.support.request.dispatch.http;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpUtil;

public abstract class AbstractNginxRequestDispatchFullResp extends AbstractNginxRequestDispatch {

    private static final Log logger = LogFactory.getLog(AbstractNginxRequestDispatchFullResp.class);

    protected abstract FullHttpResponse buildFullHttpResponse(final FullHttpRequest request,
                                                              final NginxConfig nginxConfig,
                                                              final NginxRequestDispatchContext context);

    public void doDispatch(final NginxRequestDispatchContext context) {
        final FullHttpRequest request = context.getRequest();
        final NginxConfig nginxConfig = context.getNginxConfig();
        FullHttpResponse response = buildFullHttpResponse(request, nginxConfig, context);
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());


        final ChannelHandlerContext ctx = context.getCtx();

        // 结果响应
        ChannelFuture lastContentFuture = ctx.writeAndFlush(response);
        //如果不支持keep-Alive，服务器端主动关闭请求
        if (!HttpUtil.isKeepAlive(request)) {
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }
        logger.info("[Nginx] channelRead writeAndFlush DONE response={}", response);
    }

}
