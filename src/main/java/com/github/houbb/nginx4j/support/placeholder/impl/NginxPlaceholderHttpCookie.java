package com.github.houbb.nginx4j.support.placeholder.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.support.placeholder.AbstractNginxPlaceholder;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;

import java.util.Set;

/**
 * 占位符处理类
 * @since 0.17.0
 *
 * @author 老马啸西风
 */
public class NginxPlaceholderHttpCookie extends AbstractNginxPlaceholder {

    private static final Log logger = LogFactory.getLog(NginxPlaceholderHttpCookie.class);


    @Override
    protected Object extract(FullHttpRequest request, NginxRequestDispatchContext context) {
        String cookieHeader = request.headers().get(HttpHeaderNames.COOKIE);
        if (cookieHeader != null) {
            Set<Cookie> cookies = ServerCookieDecoder.LAX.decode(cookieHeader);
            StringBuilder cookieString = new StringBuilder();
            for (Cookie cookie : cookies) {
                if (cookieString.length() > 0) {
                    cookieString.append("; ");
                }
                cookieString.append(cookie.name()).append("=").append(cookie.value());
            }
            return cookieString.toString();
        }
        return "";
    }

    @Override
    protected String getKey(FullHttpRequest request, NginxRequestDispatchContext context) {
        return "$http_cookie";
    }

}
