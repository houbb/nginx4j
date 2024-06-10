package com.github.houbb.nginx4j.support.placeholder.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.support.placeholder.AbstractNginxPlaceholderRequest;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 完整的原始请求行，例如 `GET /index.html HTTP/1.1`。
 *
 * @author 老马啸西风
 * @since 0.19.0
 */
public class NginxPlaceholderRequest extends AbstractNginxPlaceholderRequest {

    private static final Log logger = LogFactory.getLog(NginxPlaceholderRequest.class);


    @Override
    protected Object extractBeforeDispatch(FullHttpRequest request, NginxRequestDispatchContext context) {
        // 获取请求的完整原始请求行
        String method = request.method().name();
        String uri = request.uri();
        String protocolVersion = request.protocolVersion().text();

        return method + " " + uri + " " + protocolVersion;
    }

    @Override
    protected String getKeyBeforeDispatch(FullHttpRequest request, NginxRequestDispatchContext context) {
        return "$request";
    }

}
