package com.github.houbb.nginx4j.support.request.dispatch;

import com.github.houbb.heaven.support.tuple.impl.Pair;
import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.ArrayPrimitiveUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.support.server.NginxServerSocket;
import com.github.houbb.nginx4j.util.InnerMimeUtil;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.io.File;


/**
 * 静态资源
 * @author 老马啸西风
 * @since 0.2.0
 */
public class NginxRequestDispatchDefault implements NginxRequestDispatch {

    private static final Log log = LogFactory.getLog(NginxServerSocket.class);

    /**
     * 内容的分发处理
     *
     * @param requestInfoBo 请求
     * @param nginxConfig   配置
     * @return 结果
     */
    public FullHttpResponse dispatch(final FullHttpRequest requestInfoBo, final NginxConfig nginxConfig) {
        // 消息解析不正确
        /*如果无法解码400*/
        if (!requestInfoBo.decoderResult().isSuccess()) {
            log.warn("[Nginx] base request for http={}", requestInfoBo);
            return buildCommentResp(null, HttpResponseStatus.BAD_REQUEST, requestInfoBo, nginxConfig);
        }

        // 文件
        File targetFile = getTargetFile(requestInfoBo, nginxConfig);
        // 是否存在
        if(targetFile.exists()) {
            byte[] fileContent = FileUtil.getFileBytes(targetFile);
            FullHttpResponse response = buildCommentResp(fileContent, HttpResponseStatus.OK, requestInfoBo, nginxConfig);

            // 设置文件类别
            String contentType = InnerMimeUtil.getContentType(targetFile);
            setContentType(response, contentType);

            return response;
        }  else {
            return buildCommentResp(null, HttpResponseStatus.NOT_FOUND, requestInfoBo, nginxConfig);
        }
    }

    protected File getTargetFile(final FullHttpRequest request, final NginxConfig nginxConfig) {
        boolean isRootPath = isRootPath(request, nginxConfig);
        // 根路径
        if(isRootPath) {
            log.info("[Nginx] current req meet root path");
            return nginxConfig.getNginxIndexContent().getIndexFile(nginxConfig);
        }

        final String basicPath = nginxConfig.getHttpServerRoot();
        final String path = request.uri();

        // other
        String fullPath = FileUtil.buildFullPath(basicPath, path);
        return new File(fullPath);
    }

    protected boolean isRootPath(final FullHttpRequest request, final NginxConfig nginxConfig) {
        final String path = request.uri();

        //root path
        if(StringUtil.isEmpty(path) || "/".equals(path)) {
            return true;
        }
        return false;
    }

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
    protected FullHttpResponse buildCommentResp(byte[] bytes,
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
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        //如果request中有KEEP ALIVE信息
        if (HttpUtil.isKeepAlive(request)) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }

        return response;
    }

    protected void setContentType(FullHttpResponse response,
                                  String contentType) {
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType);
    }

}
