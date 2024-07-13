package com.github.houbb.nginx4j.support.request.dispatch.http;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import com.github.houbb.nginx4j.util.InnerRespUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * @since 0.23.0
 */
public class NginxRequestDispatch302 extends AbstractNginxRequestDispatchFullResp {

    private static final Log logger = LogFactory.getLog(AbstractNginxRequestDispatchFullResp.class);

    @Override
    protected FullHttpResponse buildFullHttpResponse(FullHttpRequest request,
                                                     final NginxConfig nginxConfig,
                                                     NginxRequestDispatchContext context) {
        logger.warn("[Nginx] 302 request for http={}", request);
        FullHttpResponse response = InnerRespUtil.buildCommonResp(null, HttpResponseStatus.FOUND, request, context);
        return response;
    }

}
