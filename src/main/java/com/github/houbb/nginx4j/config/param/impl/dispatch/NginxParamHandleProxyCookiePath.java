package com.github.houbb.nginx4j.config.param.impl.dispatch;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.config.param.AbstractNginxParamLifecycleDispatch;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.handler.codec.http.*;

import java.util.List;

/**
 * 参数处理类 响应头处理
 *
 * @since 0.20.0
 * @author 老马啸西风
 */
public class NginxParamHandleProxyCookiePath extends AbstractNginxParamLifecycleDispatch {

    private static final Log logger = LogFactory.getLog(NginxParamHandleProxyCookiePath.class);


    @Override
    public boolean doBeforeDispatch(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context) {

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

        return true;
    }

    @Override
    public boolean doAfterDispatch(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context) {
        return true;
    }

    @Override
    protected String getKey(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context) {
        return "proxy_cookie_path";
    }
}
