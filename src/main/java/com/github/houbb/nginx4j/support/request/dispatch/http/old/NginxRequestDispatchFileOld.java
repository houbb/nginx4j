package com.github.houbb.nginx4j.support.request.dispatch.http.old;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.constant.EnableStatusEnum;
import com.github.houbb.nginx4j.constant.NginxConst;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import com.github.houbb.nginx4j.support.request.dispatch.http.AbstractNginxRequestDispatch;
import com.github.houbb.nginx4j.support.request.dispatch.http.AbstractNginxRequestDispatchFullResp;
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
 * @since 0.6.0
 */
@Deprecated
public class NginxRequestDispatchFileOld extends AbstractNginxRequestDispatch {

    private static final Log logger = LogFactory.getLog(AbstractNginxRequestDispatchFullResp.class);

    @Override
    public void doDispatch(NginxRequestDispatchContext context) {
        final FullHttpRequest request = context.getRequest();
        final File targetFile = context.getFile();
        final String bigFilePath = targetFile.getAbsolutePath();
        final long fileLength = targetFile.length();
        final NginxConfig nginxConfig = context.getNginxConfig();
        final ChannelHandlerContext ctx = context.getCtx();

        logger.info("[Nginx] match file, path={}", bigFilePath);

        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        // 文件比较大，直接下载处理
        if(fileLength > NginxConst.BIG_FILE_SIZE) {
            response.headers().set(HttpHeaderNames.CONTENT_DISPOSITION, "attachment; filename=\"" + targetFile.getName() + "\"");
        }
        // 如果请求中有KEEP ALIVE信息
        if (HttpUtil.isKeepAlive(request)) {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, InnerMimeUtil.getContentTypeWithCharset(targetFile, context.getNginxConfig().getCharset()));
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, fileLength);

        //gzip
        boolean gzipFlag = InnerGzipUtil.isMatchGzip(context);
        if(gzipFlag) {
            File compressFile = InnerGzipUtil.prepareGzip(context, response);
            context.setFile(compressFile);
        }

        // 写基本信息
        ctx.write(response);

        if(EnableStatusEnum.isEnable(nginxConfig.getNginxSendFileConfig().getSendFile())) {
            //zero-copy
            dispatchByZeroCopy(context);
        } else {
            // 普通
            dispatchByRandomAccessFile(context);
        }

        // 最后处理
        if(gzipFlag) {
            InnerGzipUtil.afterGzip(context, response);
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
        long totalLength = targetFile.length();

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(targetFile, "r");
            FileChannel fileChannel = randomAccessFile.getChannel();

            // 使用DefaultFileRegion进行零拷贝传输
            DefaultFileRegion fileRegion = new DefaultFileRegion(fileChannel, 0, totalLength);
            ChannelFuture transferFuture = ctx.writeAndFlush(fileRegion);

            // 监听传输完成事件
            transferFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) {
                    try {
                        if (future.isSuccess()) {
                            // 传输完毕，发送最后一个空内容，标志传输结束
                            ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
                            // 如果不支持keep-Alive，服务器端主动关闭请求
                            if (!HttpUtil.isKeepAlive(context.getRequest())) {
                                lastContentFuture.addListener(ChannelFutureListener.CLOSE);
                            }
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
            logger.info("[Nginx] file process >>>>>>>>>>> {}", totalLength);

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
        long totalLength = targetFile.length();
        long totalRead = 0;

        try(RandomAccessFile randomAccessFile = new RandomAccessFile(targetFile, "r")) {
            ByteBuffer buffer = ByteBuffer.allocate(NginxConst.CHUNK_SIZE);
            while (true) {
                int bytesRead = randomAccessFile.read(buffer.array());
                if (bytesRead == -1) { // 文件读取完毕
                    break;
                }
                buffer.limit(bytesRead);
                // 写入分块数据
                ctx.write(new DefaultHttpContent(Unpooled.wrappedBuffer(buffer)));
                buffer.clear(); // 清空缓冲区以供下次使用

                // process 可以考虑加一个 listener
                totalRead += bytesRead;
                logger.info("[Nginx] file process >>>>>>>>>>> {}/{}", totalRead, totalLength);
            }

            // 结果响应
            ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            //如果不支持keep-Alive，服务器端主动关闭请求
            if (!HttpUtil.isKeepAlive(context.getRequest())) {
                lastContentFuture.addListener(ChannelFutureListener.CLOSE);
            }
        } catch (Exception e) {
            logger.error("[Nginx] file meet ex", e);
            throw new Nginx4jException(e);
        }
    }

}
