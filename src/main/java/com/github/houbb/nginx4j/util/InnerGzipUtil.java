package com.github.houbb.nginx4j.util;

import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.config.NginxGzipConfig;
import com.github.houbb.nginx4j.config.NginxUserConfig;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;
import com.github.houbb.nginx4j.constant.EnableStatusEnum;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * 内部压缩工具类
 *
 * @since 0.9.0
 */
public class InnerGzipUtil {

    private static final Log logger = LogFactory.getLog(InnerGzipUtil.class);

    /**
     * 是否需要压缩
     *
     *         gzip on;
     *         gzip_vary on;
     *         gzip_proxied any;
     *         gzip_comp_level 5;
     *         gzip_min_length 256;
     *         gzip_types text/plain text/css application/json application/javascript text/xml application/xml application/xml+rss text/javascript;
     *
     * @param targetFile 目标文件
     * @param request 请求
     * @param context 上下文
     * @return 结果
     */
    private static boolean isNeedCompress(final File targetFile,
                                   final FullHttpRequest request,
                                   final NginxRequestDispatchContext context) {
        final NginxUserServerConfig nginxUserServerConfig = context.getCurrentNginxUserServerConfig();
        final NginxGzipConfig gzipConfig = nginxUserServerConfig.getNginxGzipConfig();

        if(EnableStatusEnum.ON.getCode().equalsIgnoreCase(gzipConfig.getGzip())) {
            // 大小
            long configSize = gzipConfig.getGzipMinLength();
            long fileLength = targetFile.length();
            if(fileLength < configSize) {
                return false;
            }

            // 文件类别
            List<String> configContentTypeList = gzipConfig.getGzipTypes();
            String contentType = InnerMimeUtil.getContentType(targetFile);
            if(!configContentTypeList.contains(contentType)) {
                return false;
            }

            // 真
            return true;
        }


        return false;
    }

    /**
     * 是否启用 gzip
     * @param context 上下文
     * @return 结果
     * @since 0.9.0
     */
    public static boolean isMatchGzip(NginxRequestDispatchContext context) {
        final FullHttpRequest request = context.getRequest();
        final File targetFile = context.getFile();

        return InnerReqUtil.isGzipSupport(request) && isNeedCompress(targetFile, request, context);
    }

    public static File prepareGzip(NginxRequestDispatchContext context,
                               final HttpResponse response) {
        final ChannelHandlerContext ctx = context.getCtx();

        // 添加Gzip编码到响应头
        response.headers().set(HttpHeaderNames.CONTENT_ENCODING, HttpHeaderValues.GZIP);
        // 添加Vary头，告知存在多个版本的响应
        response.headers().set(HttpHeaderNames.VARY, HttpHeaderNames.ACCEPT_ENCODING);
        // 添加HttpContentCompressor处理器以自动压缩响应体
//        ctx.pipeline().addBefore(ctx.name(), "gzipEncoder", new HttpContentCompressor());

        logger.info("[Nginx] meet gzip");

        return compressFile(context.getFile());
    }

    public static File compressFile(File sourceFile) {
        String destFile = sourceFile.getAbsolutePath()+".gz";
        File resultFile = new File(destFile);
        FileUtil.createFile(destFile);
        try (FileInputStream fileInputStream = new FileInputStream(sourceFile);
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(Files.newOutputStream(Paths.get(destFile)))) {

            byte[] buffer = new byte[1024];
            int length;
            // 读取源文件并写入到gzip压缩流中
            while ((length = fileInputStream.read(buffer)) != -1) {
                gzipOutputStream.write(buffer, 0, length);
            }
            gzipOutputStream.finish(); // 完成压缩

            return resultFile;
        } catch (IOException e) {
            logger.error("[Nginx] compressFile meet ex", e);
            return resultFile;
        }
    }

    public static void afterGzip(NginxRequestDispatchContext context,
                             final HttpResponse response) {
        final ChannelHandlerContext ctx = context.getCtx();
        FileUtil.deleteFile(context.getFile());

        // 如果启用了压缩，发送完毕后移除HttpContentCompressor处理器
//        ctx.pipeline().remove("gzipEncoder");
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
