package com.github.houbb.nginx4j.support.placeholder.impl;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.support.placeholder.AbstractNginxPlaceholderRequest;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 占位符处理类
 * @since 0.19.0
 *
 * @author 老马啸西风
 */
public class NginxPlaceholderXForwardedFor extends AbstractNginxPlaceholderRequest {

    private static final Log logger = LogFactory.getLog(NginxPlaceholderXForwardedFor.class);


    @Override
    protected Object extractBeforeDispatch(FullHttpRequest request, NginxRequestDispatchContext context) {
        String xForwardedFor = request.headers().get("X-Forwarded-For");
        if(StringUtil.isNotEmpty(xForwardedFor)) {
            return xForwardedFor;
        }

        return "";
    }

    @Override
    protected String getKeyBeforeDispatch(FullHttpRequest request, NginxRequestDispatchContext context) {
        return "$proxy_add_x_forwarded_for";
    }

}
