package com.github.houbb.nginx4j.support.placeholder.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.support.placeholder.AbstractNginxPlaceholderRequest;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;

/**
 * 客户端的 User-Agent 头。
 *
 * @author 老马啸西风
 * @since 0.19.0
 */
public class NginxPlaceholderHttpUserAgent extends AbstractNginxPlaceholderRequest {

    private static final Log logger = LogFactory.getLog(NginxPlaceholderHttpUserAgent.class);


    @Override
    protected Object extractBeforeDispatch(FullHttpRequest request, NginxRequestDispatchContext context) {
        return request.headers().get(HttpHeaderNames.USER_AGENT);
    }

    @Override
    protected String getKeyBeforeDispatch(FullHttpRequest request, NginxRequestDispatchContext context) {
        return "$http_user_agent";
    }

}
