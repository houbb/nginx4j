package com.github.houbb.nginx4j.support.placeholder.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.support.placeholder.AbstractNginxPlaceholderRequest;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 用于 HTTP 基本认证的用户名。
 *
 * @author 老马啸西风
 * @since 0.19.0
 */
public class NginxPlaceholderRemoteUser extends AbstractNginxPlaceholderRequest {

    private static final Log logger = LogFactory.getLog(NginxPlaceholderRemoteUser.class);


    @Override
    protected Object extractBeforeDispatch(FullHttpRequest request, NginxRequestDispatchContext context) {
        // 获取 Authorization 头
        String authorization = request.headers().get(HttpHeaderNames.AUTHORIZATION);

        String remoteUser = "";
        if (authorization != null && authorization.startsWith("Basic ")) {
            // 提取 Base64 编码部分
            String base64Credentials = authorization.substring(6);
            // 解码 Base64 字符串
            String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
            // 分割用户名和密码
            String[] values = credentials.split(":", 2);
            if (values.length == 2) {
                remoteUser = values[0]; // 获取用户名
            }
        }

        return remoteUser;
    }

    @Override
    protected String getKeyBeforeDispatch(FullHttpRequest request, NginxRequestDispatchContext context) {
        return "$remote_user";
    }

}
