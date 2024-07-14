package com.github.houbb.nginx4j.support.request.dispatch.http;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import com.github.houbb.nginx4j.support.returns.NginxReturnResult;
import com.github.houbb.nginx4j.util.InnerRespUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

public class NginxRequestDispatchHttpReturn extends AbstractNginxRequestDispatchFullResp {

    private static final Log logger = LogFactory.getLog(AbstractNginxRequestDispatchFullResp.class);

    @Override
    protected FullHttpResponse buildFullHttpResponse(FullHttpRequest request,
                                                     final NginxConfig nginxConfig,
                                                     NginxRequestDispatchContext context) {
        logger.warn("[Nginx] bad request for http={}", request);
        final NginxReturnResult nginxReturnResult = context.getNginxReturnResult();
        HttpResponseStatus responseStatus = HttpResponseStatus.valueOf(nginxReturnResult.getCode(),
                nginxReturnResult.getValue());
        FullHttpResponse response = InnerRespUtil.buildCommonResp(null, responseStatus, request, context);
        //TODO: 还有许多，是不是需要特殊处理？
        return response;
    }

}
