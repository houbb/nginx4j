package com.github.houbb.nginx4j.config.param.impl.write;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxCommonConfigParam;
import com.github.houbb.nginx4j.config.param.AbstractNginxParamLifecycleWrite;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 参数处理类 响应头处理
 *
 * @since 0.20.0
 * @author 老马啸西风
 */
public class NginxParamHandleProxyCookieDomain extends AbstractNginxParamLifecycleWrite {

    private static final Log logger = LogFactory.getLog(NginxParamHandleProxyCookieDomain.class);

    @Override
    public void doBeforeWrite(NginxCommonConfigParam configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context) {
        if(!(object instanceof HttpResponse)) {
            return;
        }


        List<String> values = configParam.getValues();
        if(CollectionUtil.isEmpty(values) || values.size() < 2) {
            return;
        }


        // 原始
        String upstreamDomain = values.get(0);
        // 目标
        String targetDomain = values.get(1);

        HttpResponse response = (HttpResponse) object;
        HttpHeaders headers = response.headers();
        String setCookieHeader = headers.get(HttpHeaderNames.SET_COOKIE);

        if (setCookieHeader != null) {
            Set<Cookie> cookies = ServerCookieDecoder.STRICT.decode(setCookieHeader);

            Set<Cookie> modifiedCookies = cookies.stream().map(cookie -> {
                if (upstreamDomain.equals(cookie.domain())) {
                    Cookie newCookie = new DefaultCookie(cookie.name(), cookie.value());
                    newCookie.setDomain(targetDomain);
                    newCookie.setPath(cookie.path());
                    newCookie.setMaxAge(cookie.maxAge());
                    newCookie.setSecure(cookie.isSecure());
                    newCookie.setHttpOnly(cookie.isHttpOnly());
                    return newCookie;
                }
                return cookie;
            }).collect(Collectors.toSet());

            List<String> encodedCookies = ServerCookieEncoder.STRICT.encode(modifiedCookies);
            headers.set(HttpHeaderNames.SET_COOKIE, encodedCookies);
        }

        logger.info(">>>>>>>>>>>> doBeforeWrite proxy_hide_header upstreamDomain={} => targetDomain={}", upstreamDomain, targetDomain);
    }

    @Override
    public void doAfterWrite(NginxCommonConfigParam configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context) {

    }

    @Override
    protected String getKey(NginxCommonConfigParam configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context) {
        return "proxy_cookie_domain";
    }

}
