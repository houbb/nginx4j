package com.github.houbb.nginx4j.support.request.dispatch.http;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatch;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import com.github.houbb.nginx4j.support.returns.NginxReturnResult;
import com.github.houbb.nginx4j.util.InnerRespUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

/**
 * @since 0.24.0
 */
public class NginxRequestDispatchReturn implements NginxRequestDispatch {

    private static final Log logger = LogFactory.getLog(NginxRequestDispatchReturn.class);

    private FullHttpResponse buildFullHttpResponse(FullHttpRequest request,
                                                     final NginxConfig nginxConfig,
                                                     NginxRequestDispatchContext context) {
        logger.info("[Nginx] NginxRequestDispatchReturn request for http={}", request);

        final NginxReturnResult nginxReturnResult = context.getNginxReturnResult();
        HttpResponseStatus responseStatus = HttpResponseStatus.valueOf(nginxReturnResult.getCode(),
                nginxReturnResult.getValue());
        FullHttpResponse response = InnerRespUtil.buildCommonResp(null, responseStatus, request);

        //301
        if(301 == nginxReturnResult.getCode()) {
            response.headers().set(HttpHeaderNames.LOCATION, nginxReturnResult.getValue());
        }

        //TODO: 还有许多，是不是需要特殊处理？


        return response;
    }

    @Override
    public void dispatch(NginxRequestDispatchContext context) {
        // 是吸纳？
    }

}
