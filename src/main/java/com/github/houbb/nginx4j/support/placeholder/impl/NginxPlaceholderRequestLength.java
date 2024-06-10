package com.github.houbb.nginx4j.support.placeholder.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.support.placeholder.AbstractNginxPlaceholderRequest;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 占位符处理类
 * @since 0.17.0
 *
 * @author 老马啸西风
 */
public class NginxPlaceholderRequestLength extends AbstractNginxPlaceholderRequest {

    private static final Log logger = LogFactory.getLog(NginxPlaceholderRequestLength.class);


    @Override
    protected Object extractBeforeDispatch(FullHttpRequest request, NginxRequestDispatchContext context) {
        return calculateRequestLength(request);
    }

    private int calculateRequestLength(FullHttpRequest request) {
        int requestLineLength = request.method().name().length() + request.uri().length() + 8; // 8 for " HTTP/1.1"
        int headersLength = 0;
        for (CharSequence name : request.headers().names()) {
            headersLength += name.length() + request.headers().get(name).length() + 4; // 4 for ": " and "\r\n"
        }
        int bodyLength = request.content().readableBytes();

        return requestLineLength + headersLength + 2 + bodyLength; // 2 for final "\r\n"
    }

    @Override
    protected String getKeyBeforeDispatch(FullHttpRequest request, NginxRequestDispatchContext context) {
        return "$request_length";
    }

}
