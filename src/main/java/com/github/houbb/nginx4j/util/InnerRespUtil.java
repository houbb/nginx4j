package com.github.houbb.nginx4j.util;

import com.github.houbb.heaven.util.util.ArrayPrimitiveUtil;
import com.github.houbb.nginx4j.config.NginxConfig;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;

/**
 * @since 0.6.0
 */
public class InnerRespUtil {

    /**
     * String format = "HTTP/1.1 200 OK\r\n" +
     *                 "Content-Type: text/plain\r\n" +
     *                 "\r\n" +
     *                 "%s";
     *
     * @param bytes 原始内容
     * @param status 结果枚举
     * @param request 请求内容
     * @param nginxConfig 配置
     * @return 结果
     */
    public static FullHttpResponse buildCommentResp(byte[] bytes,
                                                final HttpResponseStatus status,
                                                final FullHttpRequest request,
                                                final NginxConfig nginxConfig) {
        byte[] defaultContent = new byte[]{};
        if(ArrayPrimitiveUtil.isNotEmpty(bytes)) {
            defaultContent = bytes;
        }

        // 构造响应
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                status, Unpooled.copiedBuffer(defaultContent));
        // 头信息
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;");

        //如果request中有KEEP ALIVE信息
        if (HttpUtil.isKeepAlive(request)) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }

        return response;
    }

    public static void setContentType(HttpResponse response,
                                  String contentType) {
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType);
    }

}
