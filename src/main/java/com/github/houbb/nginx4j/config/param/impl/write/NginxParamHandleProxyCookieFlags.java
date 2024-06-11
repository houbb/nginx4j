package com.github.houbb.nginx4j.config.param.impl.write;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.DateUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.config.param.AbstractNginxParamLifecycleWrite;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 参数处理类 响应头处理
 *
 * @since 0.20.0
 * @author 老马啸西风
 */
public class NginxParamHandleProxyCookieFlags extends AbstractNginxParamLifecycleWrite {

    private static final Log logger = LogFactory.getLog(NginxParamHandleProxyCookieFlags.class);

    @Override
    public void doBeforeWrite(NginxCommonConfigEntry configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context) {
        if(!(object instanceof HttpResponse)) {
            return;
        }


        List<String> values = configParam.getValues();
        if(CollectionUtil.isEmpty(values) || values.size() < 2) {
            return;
        }

        HttpResponse response = (HttpResponse) object;
        HttpHeaders headers = response.headers();
        String cookieHeader = headers.get(HttpHeaderNames.COOKIE);

        final String cookieName = values.get(0);

        if (cookieHeader != null) {
            Set<Cookie> cookies = ServerCookieDecoder.STRICT.decode(cookieHeader);

            Set<Cookie> modifiedCookies = cookies.stream().map(cookie -> {
                // 相同的名字
                if (cookieName.equals(cookie.name())) {
                    // HttpOnly Secure
                    for(int i = 1; i < values.size(); i++) {
                        String value = values.get(i);
                        if("HttpOnly".equals(value)) {
                            cookie.setHttpOnly(true);
                        }
                        if("Secure".equals(value)) {
                            cookie.setSecure(true);
                        }

                        // 拆分
                        if(!value.contains("=")) {
                            return cookie;
                        }

                        String[] items = value.split("=");
                        String itemKey = items[0];
                        String itemVal = items[1];

//                        if("SameSite".equals(itemKey) && "Strict".equals(itemVal)) {
//                        }

                        if("Max-Age".equals(itemKey)) {
                            cookie.setMaxAge(Long.parseLong(itemVal));
                        }
                        if("Expires".equals(itemKey)) {
                            Date expireDate = calcDate(itemVal);
                            long maxAge = expireDate.getTime() - System.currentTimeMillis();
                            cookie.setMaxAge(maxAge);
                        }

                        if("Domain".equals(itemKey)) {
                            cookie.setDomain(itemVal);
                        }

                        if("Path".equals(itemKey)) {
                            cookie.setPath(itemVal);
                        }
                    }
                }
                return cookie;
            }).collect(Collectors.toSet());

            List<String> encodedCookies = ServerCookieEncoder.STRICT.encode(modifiedCookies);
            headers.set(HttpHeaderNames.COOKIE, encodedCookies);
        }

        logger.info(">>>>>>>>>>>> doBeforeWrite proxy_cookie_flags values={}", values);
    }

    private Date calcDate(String itemKey) {
        return DateUtil.parseDate(itemKey, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     *         # 添加 HttpOnly 和 Secure 标志
     *         proxy_cookie_flags session_cookie HttpOnly Secure;
     *
     *         # 设置 SameSite 标志为 Strict
     *         proxy_cookie_flags mycookie SameSite=Strict;
     *
     *         # 设置 Max-Age 为 1 小时
     *         proxy_cookie_flags persistent_cookie Max-Age=3600;
     *
     *         # 设置 Expires 属性
     *         proxy_cookie_flags old_cookie Expires=Wed, 21 Oct 2026 07:28:00 GMT;
     *
     *         # 设置 Domain 属性
     *         proxy_cookie_flags global_cookie Domain=example.com;
     *
     *         # 设置 Path 属性
     *         proxy_cookie_flags local_cookie Path=/subpath;
     *
     * @param name
     * @param value
     * @param httpOnly
     * @param secure 是否
     * @return 结果
     * @since 0.20.0
     */
    private static Cookie createCookie(String name, String value, boolean httpOnly, boolean secure) {
        Cookie cookie = new DefaultCookie(name, value);
        cookie.setHttpOnly(httpOnly);
        cookie.setSecure(secure);
        return cookie;
    }

    /**
     * 获取 cookie 的值
     * @param request 请求
     * @param cookieName 名称
     * @return 结果
     * @since 0.20.0
     */
    public String getCookieValue(FullHttpRequest request, String cookieName) {
        HttpHeaders headers = request.headers();
        String cookieString = headers.get(HttpHeaderNames.COOKIE);

        if (cookieString != null) {
            Set<Cookie> cookies = ServerCookieDecoder.STRICT.decode(cookieString);
            for (Cookie cookie : cookies) {
                if (cookie.name().equals(cookieName)) {
                    return cookie.value();
                }
            }
        }

        return null; // 如果找不到对应的 cookie
    }

    @Override
    public void doAfterWrite(NginxCommonConfigEntry configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context) {

    }

    @Override
    protected String getKey(NginxCommonConfigEntry configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context) {
        return "proxy_cookie_flags";
    }

}
