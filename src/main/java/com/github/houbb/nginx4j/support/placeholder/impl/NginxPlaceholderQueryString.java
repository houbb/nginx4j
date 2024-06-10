package com.github.houbb.nginx4j.support.placeholder.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.support.placeholder.AbstractNginxPlaceholderRequest;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

/**
 * 同 `$args`。
 *
 *
 * @author 老马啸西风
 * @since 0.19.0
 */
public class NginxPlaceholderQueryString extends AbstractNginxPlaceholderRequest {

    private static final Log logger = LogFactory.getLog(NginxPlaceholderQueryString.class);

    @Override
    protected Object extractBeforeDispatch(FullHttpRequest request, NginxRequestDispatchContext context) {
        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
        StringBuilder args = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : decoder.parameters().entrySet()) {
            for (String value : entry.getValue()) {
                if (args.length() > 0) {
                    args.append("&");
                }
                args.append(entry.getKey()).append("=").append(value);
            }
        }
        return args.toString();
    }

    @Override
    protected String getKeyBeforeDispatch(FullHttpRequest request, NginxRequestDispatchContext context) {
        return "$query_string";
    }

}
