package com.github.houbb.nginx4j.support.placeholder.impl;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.support.placeholder.AbstractNginxPlaceholderRequest;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponse;

/**
 * 传送给客户端的主体字节数，不包括响应头。
 *
 * @since 0.19.0
 *
 * @author 老马啸西风
 */
public class NginxPlaceholderResponseBodyBytesSent extends AbstractNginxPlaceholderRequest {

    private static final Log logger = LogFactory.getLog(NginxPlaceholderResponseBodyBytesSent.class);

    @Override
    protected Object extractBeforeWrite(NginxRequestDispatchContext context) {
        // 获取响应的长度
        HttpResponse response = context.getHttpResponse();
        if(response != null) {
            String contentLength = response.headers().get(HttpHeaderNames.CONTENT_LENGTH);
            if(StringUtil.isEmpty(contentLength)) {
                return -1;
            }

            return Long.valueOf(contentLength);
        }

        // 不存在？
        return -1;
    }

    @Override
    protected String getKeyBeforeWrite(NginxRequestDispatchContext context) {
        return "$body_bytes_sent";
    }

}
