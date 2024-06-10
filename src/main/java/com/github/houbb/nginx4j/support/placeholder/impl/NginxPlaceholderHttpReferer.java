package com.github.houbb.nginx4j.support.placeholder.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.support.placeholder.AbstractNginxPlaceholderRequest;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;

/**
 * 客户端的 Referer 头
 *
 * @author 老马啸西风
 *
 * @since 0.19.0
 */
public class NginxPlaceholderHttpReferer extends AbstractNginxPlaceholderRequest {

    private static final Log logger = LogFactory.getLog(NginxPlaceholderHttpReferer.class);


    @Override
    protected Object extractBeforeDispatch(FullHttpRequest request, NginxRequestDispatchContext context) {
        return request.headers().get(HttpHeaderNames.REFERER);
    }

    @Override
    protected String getKeyBeforeDispatch(FullHttpRequest request, NginxRequestDispatchContext context) {
        return "$http_referer";
    }

}
