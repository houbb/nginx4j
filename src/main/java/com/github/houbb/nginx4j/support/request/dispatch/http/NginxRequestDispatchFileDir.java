package com.github.houbb.nginx4j.support.request.dispatch.http;

import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.heaven.util.util.ArrayUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.config.NginxUserConfig;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import com.github.houbb.nginx4j.util.InnerRespUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.io.File;

public class NginxRequestDispatchFileDir extends AbstractNginxRequestDispatchFullResp {

    private static final Log logger = LogFactory.getLog(AbstractNginxRequestDispatchFullResp.class);

    @Override
    protected FullHttpResponse buildFullHttpResponse(FullHttpRequest request,
                                                     final NginxConfig nginxConfig,
                                                     NginxRequestDispatchContext context) {
        logger.info("[Nginx] meet file dir http={}", request);
        return buildDirResp(context);
    }

    /**
     * 构建文件夹结果
     * @param context 上下文
     * @return 结果
     * @since 0.5.0
     */
    protected FullHttpResponse buildDirResp(NginxRequestDispatchContext context) {
        try {
            final FullHttpRequest request = context.getRequest();
            final NginxUserServerConfig userServerConfig = context.getCurrentNginxUserServerConfig();

            String html = generateFileListHTML(context);
            final String charset = userServerConfig.getCharset();

            byte[] fileContent = html.getBytes(charset);
            FullHttpResponse response = InnerRespUtil.buildCommonResp(fileContent, HttpResponseStatus.OK, request);
            InnerRespUtil.setContentType(response, "text/html; charset=" + charset);
            return response;
        } catch (Exception e) {
            logger.error("[Nginx] buildDirResp meet ex", e);
            throw new Nginx4jException(e);
        }
    }

    protected String generateFileListHTML(NginxRequestDispatchContext context) {
        final File directory = context.getFile();
        final FullHttpRequest request = context.getRequest();

        // 确保传入的是一个目录
        if (!directory.isDirectory()) {
            return "Error: The specified path is not a directory.";
        }

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><head><title>File List</title></head><body>");
        htmlBuilder.append("<h1>File List</h1>");
        htmlBuilder.append("<ul>");

        File[] fileList = directory.listFiles();
        if(ArrayUtil.isNotEmpty(fileList)) {
            for (File file : fileList) {
                String fileName = file.getName();
                String fileLink = getFileLink(file, request);
                htmlBuilder.append("<li><a href=\"").append(fileLink).append("\">").append(fileName).append("</a></li>");
            }
        }

        htmlBuilder.append("</ul></body></html>");
        return htmlBuilder.toString();
    }

    protected String getFileLink(File file, final FullHttpRequest request) {
        String fileName = file.getName();
        return FileUtil.buildFullPath(request.uri(), fileName);
    }

}
