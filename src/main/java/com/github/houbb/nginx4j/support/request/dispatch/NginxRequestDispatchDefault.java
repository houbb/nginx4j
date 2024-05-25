package com.github.houbb.nginx4j.support.request.dispatch;

import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.support.server.NginxServerSocket;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;


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


        final String basicPath = nginxConfig.getHttpServerRoot();
        final String path = requestInfoBo.uri();

        boolean isRootPath = isRootPath(requestInfoBo, nginxConfig);
        // 根路径
        if(isRootPath) {
            log.info("[Nginx] current req meet root path");
            String indexContent = nginxConfig.getNginxIndexContent().getContent(nginxConfig);
            return buildCommentResp(indexContent, HttpResponseStatus.OK, requestInfoBo, nginxConfig);
        }

        // other
        String fullPath = FileUtil.buildFullPath(basicPath, path);
        // 是否存在
        if(FileUtil.exists(fullPath)) {
            // TODO: 后续这里的文件类型要根据具体的文件变化
            String fileContent = FileUtil.getFileContent(fullPath);
            return buildCommentResp(fileContent, HttpResponseStatus.OK, requestInfoBo, nginxConfig);
        }  else {
            return buildCommentResp(null, HttpResponseStatus.NOT_FOUND, requestInfoBo, nginxConfig);
        }
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
     * @param rawText 原始内容
     * @param status 结果枚举
     * @param request 请求内容
     * @param nginxConfig 配置
     * @return 结果
     */
    protected FullHttpResponse buildCommentResp(String rawText,
                                            final HttpResponseStatus status,
                                            final FullHttpRequest request,
                                            final NginxConfig nginxConfig) {
        String defaultContent = status.toString();
        if(StringUtil.isNotEmpty(rawText)) {
            defaultContent = rawText;
        }

        // 构造响应
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                status, Unpooled.copiedBuffer(defaultContent, CharsetUtil.UTF_8));
        // 头信息
        // TODO: 根据文件变化
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        //如果request中有KEEP ALIVE信息
        if (HttpUtil.isKeepAlive(request)) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }

        return response;
    }

}
