package com.github.houbb.nginx4j.support.request.dispatch.http;

import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import com.github.houbb.nginx4j.util.InnerMimeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * 文件压缩
 *
 * @since 0.8.0
 * @author 老马啸西风
 */
public class NginxRequestDispatchFileCompress extends AbstractNginxRequestDispatchFullResp {

    private static final Log logger = LogFactory.getLog(AbstractNginxRequestDispatchFullResp.class);

    @Override
    protected FullHttpResponse buildFullHttpResponse(FullHttpRequest request,
                                                     final NginxConfig nginxConfig,
                                                     NginxRequestDispatchContext context) {
        final File targetFile = context.getFile();
        logger.info("[Nginx] match compress file, path={}", targetFile.getAbsolutePath());


        // 压缩内容
        byte[] compressData = getCompressData(context);
        // 创建一个带有GZIP压缩内容的ByteBuf
        ByteBuf compressedContent = Unpooled.copiedBuffer(compressData);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, compressedContent);

        // 设置压缩相关的响应头
        response.headers().set(HttpHeaderNames.CONTENT_ENCODING, HttpHeaderValues.GZIP);
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, InnerMimeUtil.getContentTypeWithCharset(targetFile, context.getNginxConfig().getCharset()));

        // 检查请求是否接受GZIP编码
        if (request.headers().contains(HttpHeaderNames.ACCEPT_ENCODING) &&
                request.headers().get(HttpHeaderNames.ACCEPT_ENCODING).contains(HttpHeaderValues.GZIP)) {

            // 添加Vary头，告知存在多个版本的响应
            response.headers().set(HttpHeaderNames.VARY, HttpHeaderNames.ACCEPT_ENCODING);
        }

        return response;
    }

    public static byte[] getCompressData(NginxRequestDispatchContext context) {
        final File targetFile = context.getFile();

        byte[] inputData = FileUtil.getFileBytes(targetFile);
        // 使用try-with-resources语句自动关闭资源
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            // 写入要压缩的数据
            gzipOutputStream.write(inputData);
            // 强制刷新输出流以确保所有数据都被压缩和写入
            gzipOutputStream.finish();
            // 获取压缩后的数据
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            logger.error("[Nginx] getCompressData failed", e);
            throw new Nginx4jException(e);
        }
    }

}
