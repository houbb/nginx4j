package com.github.houbb.nginx4j.support.placeholder.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.support.placeholder.AbstractNginxPlaceholder;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 占位符处理类
 * @since 0.17.0
 *
 * @author 老马啸西风
 */
public class NginxPlaceholderServerProtocol extends AbstractNginxPlaceholder {

    private static final Log logger = LogFactory.getLog(NginxPlaceholderServerProtocol.class);


    @Override
    protected Object extract(FullHttpRequest request, NginxRequestDispatchContext context) {
        return request.protocolVersion().text();
    }

    @Override
    protected String getKey(FullHttpRequest request, NginxRequestDispatchContext context) {
        return "$server_protocol";
    }

}
