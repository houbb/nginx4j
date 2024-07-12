package com.github.houbb.nginx4j.support.request.dispatch.http;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.config.NginxUserConfig;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;
import com.github.houbb.nginx4j.constant.EnableStatusEnum;
import com.github.houbb.nginx4j.constant.NginxConst;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import com.github.houbb.nginx4j.util.InnerGzipUtil;
import com.github.houbb.nginx4j.util.InnerMimeUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.codec.http.*;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 文件
 *
 * @since 0.10.0
 */
public class AbstractNginxRequestDispatchFile extends AbstractNginxRequestDispatch {

    private static final Log logger = LogFactory.getLog(AbstractNginxRequestDispatchFullResp.class);

    /**
     * 获取长度
     * @param context 上下文
     * @return 结果
     */
    protected long getActualLength(final NginxRequestDispatchContext context) {
        final File targetFile = context.getFile();
        return targetFile.length();
    }

    /**
     * 获取开始位置
     * @param context 上下文
     * @return 结果
     */
    protected long getActualStart(final NginxRequestDispatchContext context) {
        return 0L;
    }

    protected void fillContext(final NginxRequestDispatchContext context) {
        long actualLength = getActualLength(context);
        long actualStart = getActualStart(context);

        context.setActualStart(actualStart);
        context.setActualFileLength(actualLength);
    }

    /**
     * 填充响应头
     * @param context 上下文
     * @param request 请求
     * @param response 响应
     * @since 0.10.0
     */
    protected void fillRespHeaders(final NginxRequestDispatchContext context,
                                   final HttpRequest request,
                                   final HttpResponse response) {
        final File targetFile = context.getFile();
        final long fileLength = context.getActualFileLength();

        // 文件比较大，直接下载处理
        if(fileLength > NginxConst.BIG_FILE_SIZE) {
            logger.warn("[Nginx] fileLength={} > BIG_FILE_SIZE={}", fileLength, NginxConst.BIG_FILE_SIZE);
            response.headers().set(HttpHeaderNames.CONTENT_DISPOSITION, "attachment; filename=\"" + targetFile.getName() + "\"");
        }

        // 如果请求中有KEEP ALIVE信息
        if (HttpUtil.isKeepAlive(request)) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }

        final NginxUserServerConfig nginxUserServerConfig = context.getCurrentNginxUserServerConfig();
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, InnerMimeUtil.getContentTypeWithCharset(targetFile, nginxUserServerConfig.getCharset()));
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, fileLength);
    }

    protected HttpResponse buildHttpResponse(NginxRequestDispatchContext context) {
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        return response;
    }



    /**
     * 是否需要压缩处理
     * @param context 上下文
     * @return 结果
     */
    protected boolean isZipEnable(NginxRequestDispatchContext context) {
        return InnerGzipUtil.isMatchGzip(context);
    }

    /**
     * gzip 的提前预处理
     * @param context  上下文
     * @param response 响应
     */
    protected void beforeZip(NginxRequestDispatchContext context, HttpResponse response) {
        File compressFile = InnerGzipUtil.prepareGzip(context, response);
        context.setFile(compressFile);
    }

    /**
     * gzip 的提前预处理
     * @param context  上下文
     * @param response 响应
     */
    protected void afterZip(NginxRequestDispatchContext context, HttpResponse response) {
        InnerGzipUtil.afterGzip(context, response);
    }

    protected boolean isZeroCopyEnable(NginxRequestDispatchContext context) {
        final NginxUserServerConfig nginxUserServerConfig = context.getCurrentNginxUserServerConfig();

        return EnableStatusEnum.isEnable(nginxUserServerConfig.getSendFile());
    }

    protected void writeAndFlushOnComplete(final ChannelHandlerContext ctx,
                                           final NginxRequestDispatchContext context) {
        // 传输完毕，发送最后一个空内容，标志传输结束
        ChannelFuture lastContentFuture = super.writeAndFlush(ctx, LastHttpContent.EMPTY_LAST_CONTENT, context);
        // 如果不支持keep-Alive，服务器端主动关闭请求
        if (!HttpUtil.isKeepAlive(context.getRequest())) {
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void doDispatch(NginxRequestDispatchContext context) {
        final FullHttpRequest request = context.getRequest();
        final File targetFile = context.getFile();
        final ChannelHandlerContext ctx = context.getCtx();

        logger.info("[Nginx] start dispatch, path={}", targetFile.getAbsolutePath());
        // 长度+开始等基本信息
        fillContext(context);

        // 响应
        HttpResponse response = null;
        if(context.getNginxReturnResult() != null) {
            response = buildHttpResponseForReturn(request, context);
            FullHttpResponse fullHttpResponse = (FullHttpResponse) response;
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, fullHttpResponse.content().readableBytes());
            // 结果响应
            ChannelFuture lastContentFuture = super.writeAndFlush(ctx, response, context);
            //如果不支持keep-Alive，服务器端主动关闭请求
            if (!HttpUtil.isKeepAlive(request)) {
                lastContentFuture.addListener(ChannelFutureListener.CLOSE);
            }

            return;
        } else {
            response = buildHttpResponse(context);
        }


        // 添加请求头
        fillRespHeaders(context, request, response);

        //gzip
        boolean zipFlag = isZipEnable(context);
        try {
            if(zipFlag) {
                beforeZip(context, response);
            }

            // 写基本信息
            super.write(ctx, response, context);

            // 零拷贝
            boolean isZeroCopyEnable = isZeroCopyEnable(context);
            if(isZeroCopyEnable) {
                //zero-copy
                dispatchByZeroCopy(context);
            } else {
                // 普通
                dispatchByRandomAccessFile(context);
            }
        } finally {
            // 最后处理
            if(zipFlag) {
                afterZip(context, response);
            }

            // 完成
            super.beforeComplete(ctx, response, context);
        }
    }

    /**
     * Netty 之 FileRegion 文件传输: https://www.jianshu.com/p/447c2431ac32
     *
     * @param context 上下文
     */
    protected void dispatchByZeroCopy(NginxRequestDispatchContext context) {
        final ChannelHandlerContext ctx = context.getCtx();
        final File targetFile = context.getFile();

        // 分块传输文件内容
        final long actualStart = context.getActualStart();
        final long actualFileLength = context.getActualFileLength();

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(targetFile, "r");
            FileChannel fileChannel = randomAccessFile.getChannel();

            // 使用DefaultFileRegion进行零拷贝传输
            DefaultFileRegion fileRegion = new DefaultFileRegion(fileChannel, actualStart, actualFileLength);
            ChannelFuture transferFuture = super.writeAndFlush(ctx, fileRegion, context);

            // 监听传输完成事件
            transferFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) {
                    try {
                        if (future.isSuccess()) {
                            writeAndFlushOnComplete(ctx, context);
                        } else {
                            // 处理传输失败
                            logger.error("[Nginx] file transfer failed", future.cause());
                            throw new Nginx4jException(future.cause());
                        }
                    } finally {
                        // 确保在所有操作完成之后再关闭文件通道和RandomAccessFile
                        try {
                            fileChannel.close();
                            randomAccessFile.close();
                        } catch (Exception e) {
                            logger.error("[Nginx] error closing file channel", e);
                        }
                    }
                }
            });

            // 记录传输进度（如果需要，可以通过监听器或其他方式实现）
            logger.info("[Nginx] file process >>>>>>>>>>> {}", actualFileLength);

        } catch (Exception e) {
            logger.error("[Nginx] file meet ex", e);
            throw new Nginx4jException(e);
        }
    }

    // 分块传输文件内容

    /**
     * 分块传输-普通方式
     * @param context 上下文
     */
    protected void dispatchByRandomAccessFile(NginxRequestDispatchContext context) {
        final ChannelHandlerContext ctx = context.getCtx();
        final File targetFile = context.getFile();

        // 分块传输文件内容
        long actualFileLength = context.getActualFileLength();
        // 分块传输文件内容
        final long actualStart = context.getActualStart();

        long totalRead = 0;

        try(RandomAccessFile randomAccessFile = new RandomAccessFile(targetFile, "r")) {
            // 开始位置
            randomAccessFile.seek(actualStart);

            ByteBuffer buffer = ByteBuffer.allocate(NginxConst.CHUNK_SIZE);
            while (totalRead <= actualFileLength) {
                int bytesRead = randomAccessFile.read(buffer.array());
                if (bytesRead == -1) { // 文件读取完毕
                    logger.info("[Nginx] file read done.");
                    break;
                }

                buffer.limit(bytesRead);

                // 写入分块数据
                DefaultHttpContent defaultHttpContent = new DefaultHttpContent(Unpooled.wrappedBuffer(buffer));
                super.write(ctx, defaultHttpContent, context);
                buffer.clear(); // 清空缓冲区以供下次使用

                // process 可以考虑加一个 listener
                totalRead += bytesRead;
                logger.info("[Nginx] file process >>>>>>>>>>> {}/{}", totalRead, actualFileLength);
            }

            // 最后的处理
            writeAndFlushOnComplete(ctx, context);
        } catch (Exception e) {
            logger.error("[Nginx] file meet ex", e);
            throw new Nginx4jException(e);
        }
    }

}
