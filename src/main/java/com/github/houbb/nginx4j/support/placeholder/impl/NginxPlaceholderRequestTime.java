package com.github.houbb.nginx4j.support.placeholder.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.support.placeholder.AbstractNginxPlaceholderRequest;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 请求耗时
 *
 * @since 0.19.0
 *
 * @author 老马啸西风
 */
public class NginxPlaceholderRequestTime extends AbstractNginxPlaceholderRequest {

    private static final Log logger = LogFactory.getLog(NginxPlaceholderRequestTime.class);

    @Override
    protected Object extractBeforeComplete(NginxRequestDispatchContext context) {
        return context.getRequestEndTime() - context.getRequestStartTime();
    }

    @Override
    protected String getKeyBeforeComplete(NginxRequestDispatchContext context) {
        return "$request_time";
    }

}
