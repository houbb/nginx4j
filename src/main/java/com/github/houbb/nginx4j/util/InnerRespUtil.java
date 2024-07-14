package com.github.houbb.nginx4j.util;

import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.ArrayPrimitiveUtil;
import com.github.houbb.nginx4j.support.errorpage.INginxErrorPageManage;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;

import java.io.File;

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
     * @param context 上下文
     * @return 结果
     */
    public static FullHttpResponse buildCommonResp(byte[] bytes,
                                                   final HttpResponseStatus status,
                                                   final FullHttpRequest request,
                                                   NginxRequestDispatchContext context) {
        // 错误页编码，这里暂时不修改逻辑。
        // 编码 其实可以把 file 的实现直接放在这里，但是会调整以前的实现，暂时保持不变。
        File errorHtmlFile = null;
        String code = String.valueOf(status.code());
        // 获取文件
        INginxErrorPageManage errorPageManage = context.getNginxConfig().getNginxErrorPageManage();
        String errorHtmlPath = errorPageManage.getPath(code);
        if(StringUtil.isNotEmpty(errorHtmlPath)) {
            errorHtmlFile = InnerFileUtil.getTargetFile(errorHtmlPath, context);
            // 设置文件内容
            bytes = FileUtil.getFileBytes(errorHtmlFile);
        }

        byte[] defaultContent = new byte[]{};
        if(ArrayPrimitiveUtil.isNotEmpty(bytes)) {
            defaultContent = bytes;
        }

        // 构造响应
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                status, Unpooled.copiedBuffer(defaultContent));
        if(errorHtmlFile != null) {
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, InnerMimeUtil.getContentTypeWithCharset(errorHtmlFile,
                    context.getCurrentNginxUserServerConfig().getCharset()));
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, errorHtmlFile.length());
        } else {
            // 头信息
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;");
        }

        //如果request中有KEEP ALIVE信息
        if (HttpUtil.isKeepAlive(request)) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }

        //301/302
        if(HttpResponseStatus.MOVED_PERMANENTLY.equals(status)
            || HttpResponseStatus.FOUND.equals(status)) {
            response.headers().set(HttpHeaderNames.LOCATION, request.uri());
        }

        return response;
    }

    public static void setContentType(HttpResponse response,
                                  String contentType) {
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType);
    }

}
