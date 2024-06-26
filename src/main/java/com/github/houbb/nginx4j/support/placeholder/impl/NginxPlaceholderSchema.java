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
public class NginxPlaceholderSchema extends AbstractNginxPlaceholderRequest {

    private static final Log logger = LogFactory.getLog(NginxPlaceholderSchema.class);


    @Override
    protected Object extractBeforeDispatch(FullHttpRequest request, NginxRequestDispatchContext context) {
        //TODO: 这个要判断当前的 SSL 是否启用，暂时默认 http
        return "http";
    }

    @Override
    protected String getKeyBeforeDispatch(FullHttpRequest request, NginxRequestDispatchContext context) {
        return "$schema";
    }

}
