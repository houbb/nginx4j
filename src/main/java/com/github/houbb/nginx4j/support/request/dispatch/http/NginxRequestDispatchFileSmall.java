package com.github.houbb.nginx4j.support.request.dispatch.http;

import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import com.github.houbb.nginx4j.util.InnerMimeUtil;
import com.github.houbb.nginx4j.util.InnerRespUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.io.File;

/**
 * 小文件
 *
 * @since 0.6.0
 */
public class NginxRequestDispatchFileSmall extends AbstractNginxRequestDispatchFullResp {

    private static final Log logger = LogFactory.getLog(AbstractNginxRequestDispatchFullResp.class);

    @Override
    protected FullHttpResponse buildFullHttpResponse(FullHttpRequest request,
                                                     final NginxConfig nginxConfig,
                                                     NginxRequestDispatchContext context) {
        final File targetFile = context.getFile();
        logger.info("[Nginx] match small file, path={}", targetFile.getAbsolutePath());

        byte[] fileContent = FileUtil.getFileBytes(targetFile);
        FullHttpResponse response = InnerRespUtil.buildCommentResp(fileContent, HttpResponseStatus.OK, request, nginxConfig);

        // 设置文件类别
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, InnerMimeUtil.getContentTypeWithCharset(targetFile, context.getNginxConfig().getCharset()));

        return response;
    }

}
