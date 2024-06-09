package com.github.houbb.nginx4j.support.placeholder.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.support.placeholder.AbstractNginxPlaceholder;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

/**
 * 占位符处理类
 * @since 0.17.0
 *
 * @author 老马啸西风
 */
public class NginxPlaceholderArgs extends AbstractNginxPlaceholder {

    private static final Log logger = LogFactory.getLog(NginxPlaceholderArgs.class);


    @Override
    protected Object extract(FullHttpRequest request, NginxRequestDispatchContext context) {
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
    protected String getKey(FullHttpRequest request, NginxRequestDispatchContext context) {
        return "$args";
    }

}
