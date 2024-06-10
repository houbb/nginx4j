package com.github.houbb.nginx4j.support.placeholder.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.support.placeholder.AbstractNginxPlaceholderRequest;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.handler.codec.http.HttpResponse;

/**
 * 响应的状态码
 *
 * @since 0.19.0
 *
 * @author 老马啸西风
 */
public class NginxPlaceholderResponseStatus extends AbstractNginxPlaceholderRequest {

    private static final Log logger = LogFactory.getLog(NginxPlaceholderResponseStatus.class);

    @Override
    protected Object extractBeforeWrite(NginxRequestDispatchContext context) {
        // 获取响应的状态码
        HttpResponse response = context.getHttpResponse();
        if(response != null) {
            return response.status().code();
        }

        // 不存在？
        return -1;
    }

    @Override
    protected String getKeyBeforeWrite(NginxRequestDispatchContext context) {
        return "$status";
    }

}
