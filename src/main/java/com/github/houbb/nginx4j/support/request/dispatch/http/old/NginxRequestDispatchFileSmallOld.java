package com.github.houbb.nginx4j.support.request.dispatch.http.old;

import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.config.NginxSendFileConfig;
import com.github.houbb.nginx4j.constant.EnableStatusEnum;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import com.github.houbb.nginx4j.support.request.dispatch.http.AbstractNginxRequestDispatchFullResp;
import com.github.houbb.nginx4j.util.InnerMimeUtil;
import com.github.houbb.nginx4j.util.InnerRespUtil;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

/**
 * 小文件
 *
 * @since 0.6.0
 * @author 老马啸西风
 */
@Deprecated
public class NginxRequestDispatchFileSmallOld extends AbstractNginxRequestDispatchFullResp {

    private static final Log logger = LogFactory.getLog(AbstractNginxRequestDispatchFullResp.class);

    @Override
    public void dispatch(NginxRequestDispatchContext context) {
        final NginxConfig nginxConfig = context.getNginxConfig();
        final NginxSendFileConfig nginxSendFileConfig = nginxConfig.getNginxSendFileConfig();

        // 如果是启用 zero-copy
        if(EnableStatusEnum.isEnable(nginxSendFileConfig.getSendFile())) {
            this.dispatchZeroCopy(nginxConfig, context);
        } else {
            super.dispatch(context);
        }
    }

    /**
     * 零拷贝分发
     * @param nginxConfig 配置
     * @param context 上下文
     */
    protected void dispatchZeroCopy(final NginxConfig nginxConfig,
                                    final NginxRequestDispatchContext context) {
        final File targetFile = context.getFile();
        logger.info("[Nginx] match smallFile zero-copy, path={}", targetFile.getAbsolutePath());

        try (FileChannel fileChannel = FileChannel.open(targetFile.toPath(), StandardOpenOption.READ)) {
            final ChannelHandlerContext ctx = context.getCtx();
            final HttpRequest request = context.getRequest();
            final long fileSize = fileChannel.size();

            FileRegion fileRegion = new DefaultFileRegion(fileChannel, 0, fileSize);

            // 构造响应
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);

            // 设置内容类型和字符集
            String contentType = InnerMimeUtil.getContentTypeWithCharset(targetFile, nginxConfig.getCharset());
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType);

            // 如果请求中有KEEP ALIVE信息
            if (HttpUtil.isKeepAlive(request)) {
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }

            // 设置内容长度
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, fileRegion.transferred());

            // 发送响应头
            ctx.write(response);

            // 发送文件内容
            ctx.write(fileRegion, ctx.newProgressivePromise())
                    .addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) {
                            if (future.isSuccess()) {
                                // 零拷贝传输完成
                                logger.info("[Nginx] dispatchZeroCopy 零拷贝传输完成");
                            } else {
                                // 处理传输错误
                                logger.error("[Nginx] dispatchZeroCopy 处理传输错误");
                            }
                        }
                    });

            // 发送结束信号
            ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT)
                    .addListener(ChannelFutureListener.CLOSE);
        } catch (IOException e) {
            // 处理异常，例如发送错误响应
            logger.error("[Nginx] dispatchZeroCopy meet ex", e);
            throw new Nginx4jException(e);
        }
    }

    @Override
    protected FullHttpResponse buildFullHttpResponse(FullHttpRequest request,
                                                     final NginxConfig nginxConfig,
                                                     NginxRequestDispatchContext context) {
        final File targetFile = context.getFile();
        logger.info("[Nginx] match small file, path={}", targetFile.getAbsolutePath());

        byte[] fileContent = FileUtil.getFileBytes(targetFile);
        FullHttpResponse response = InnerRespUtil.buildCommonResp(fileContent, HttpResponseStatus.OK, request, nginxConfig);

        // 设置文件类别
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, InnerMimeUtil.getContentTypeWithCharset(targetFile, context.getNginxConfig().getCharset()));

        return response;
    }

}
