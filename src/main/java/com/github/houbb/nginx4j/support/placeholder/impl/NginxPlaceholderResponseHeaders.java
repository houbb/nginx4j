package com.github.houbb.nginx4j.support.placeholder.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.support.placeholder.AbstractNginxPlaceholderRequest;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.handler.codec.http.HttpResponse;

import java.util.Map;
import java.util.function.Consumer;

/**
 * `$sent_http_*`：发送给客户端的特定 HTTP 头部字段的值，例如 `$sent_http_content_type` 代表 `Content-Type` 头部字段的值。
 *
 * @since 0.19.0
 *
 * @author 老马啸西风
 */
public class NginxPlaceholderResponseHeaders extends AbstractNginxPlaceholderRequest {

    private static final Log logger = LogFactory.getLog(NginxPlaceholderResponseHeaders.class);


    @Override
    public void beforeWrite(NginxRequestDispatchContext context) {
        // 头信息翻转为 sent_http_*
        HttpResponse httpResponse = context.getHttpResponse();
        if(httpResponse != null) {
            httpResponse.headers().forEach(new Consumer<Map.Entry<String, String>>() {
                @Override
                public void accept(Map.Entry<String, String> entry) {
                    String headerName = entry.getKey();
                    String headerValue = entry.getValue();
                    String httpKey = buildSendHttpKey(headerName);

                    // 放入 map
                    doForPlaceholderMap(context, httpKey, headerValue, "BeforeWrite");
                }
            });

        }
    }

    private String buildSendHttpKey(String headerName) {
        String rawKey = "$sent_http_" + headerName.replace("-", "_");
        return rawKey.toLowerCase();
    }

}
