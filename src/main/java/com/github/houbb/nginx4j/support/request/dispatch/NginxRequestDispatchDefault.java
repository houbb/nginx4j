package com.github.houbb.nginx4j.support.request.dispatch;

import com.github.houbb.heaven.support.tuple.impl.Pair;
import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.ArrayPrimitiveUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.server.NginxServerSocket;
import com.github.houbb.nginx4j.util.InnerMimeUtil;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
            // 如果是文件夹
            if(targetFile.isDirectory()) {
                log.info("[Nginx] file={} is directory, list all files", targetFile.getAbsolutePath());
                return buildDirResp(targetFile, requestInfoBo, nginxConfig);
            }

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

    /**
     * 构建文件夹结果
     * @param targetFile 目标文件
     * @param request 请求
     * @param nginxConfig 配置
     * @return 结果
     * @since 0.5.0
     */
    protected FullHttpResponse buildDirResp(File targetFile, final FullHttpRequest request, final NginxConfig nginxConfig) {
        try {
            String html = generateFileListHTML(targetFile, request, nginxConfig);

            byte[] fileContent = html.getBytes(nginxConfig.getCharset());
            FullHttpResponse response = buildCommentResp(fileContent, HttpResponseStatus.OK, request, nginxConfig);
            setContentType(response, "text/html;");
            return response;
        } catch (Exception e) {
            throw new Nginx4jException(e);
        }
    }

    protected String generateFileListHTML(File directory, final FullHttpRequest request, final NginxConfig nginxConfig) {
        // 确保传入的是一个目录
        if (!directory.isDirectory()) {
            return "Error: The specified path is not a directory.";
        }

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><head><title>File List</title></head><body>");
        htmlBuilder.append("<h1>File List</h1>");
        htmlBuilder.append("<ul>");

        File[] fileList = directory.listFiles();

        for (File file : fileList) {
            String fileName = file.getName();
            String fileLink = getFileLink(file, request, nginxConfig);
            htmlBuilder.append("<li><a href=\"").append(fileLink).append("\">").append(fileName).append("</a></li>");
        }

        htmlBuilder.append("</ul></body></html>");
        return htmlBuilder.toString();
    }

    protected String getFileLink(File file, final FullHttpRequest request, final NginxConfig nginxConfig) {
        String fileName = file.getName();
        return FileUtil.buildFullPath(request.uri(), fileName);
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
