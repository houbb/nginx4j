package com.github.houbb.nginx4j.config.param.impl.dispatch;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxCommonConfigParam;
import com.github.houbb.nginx4j.config.param.AbstractNginxParamLifecycleDispatch;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
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
public class NginxParamHandleProxyCookiePath extends AbstractNginxParamLifecycleDispatch {

    private static final Log logger = LogFactory.getLog(NginxParamHandleProxyCookiePath.class);


    @Override
    public void doBeforeDispatch(NginxCommonConfigParam configParam, NginxRequestDispatchContext context) {

        List<String> values = configParam.getValues();
        if(CollectionUtil.isEmpty(values) || values.size() < 2) {
            throw new Nginx4jException("proxy_cookie_path 必须包含2个参数");
        }

        FullHttpRequest request = context.getRequest();

        // 原始
        String regex = values.get(0);
        String path = values.get(1);

        HttpHeaders headers = request.headers();
        String cookieHeader = headers.get(HttpHeaderNames.COOKIE);
        if (cookieHeader != null) {
            String modifiedCookieHeader = cookieHeader.replaceAll(regex, path);
            headers.set(HttpHeaderNames.COOKIE, modifiedCookieHeader);
        }

        logger.info(">>>>>>>>>>>> doBeforeDispatch proxy_cookie_path replace regex={} => path={}", regex, path);
    }

    @Override
    public void doAfterDispatch(NginxCommonConfigParam configParam, NginxRequestDispatchContext context) {

    }

    @Override
    protected String getKey(NginxCommonConfigParam configParam, NginxRequestDispatchContext context) {
        return "proxy_cookie_path";
    }
}
