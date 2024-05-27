package com.github.houbb.nginx4j.util;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpRequest;

/**
 * @since 0.9.0
 */
public class InnerReqUtil {

    /**
     * 是否支持 gzip
     * @param request 请求
     * @return 结果
     */
    public static boolean isGzipSupport(HttpRequest request) {
        // 检查请求是否接受GZIP编码
        if (request.headers().contains(HttpHeaderNames.ACCEPT_ENCODING) &&
                request.headers().get(HttpHeaderNames.ACCEPT_ENCODING).contains(HttpHeaderValues.GZIP)) {
            return true;
        }
        return false;
    }

}
