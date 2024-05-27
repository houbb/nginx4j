package com.github.houbb.nginx4j.support.request.dispatch.http.old;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.constant.NginxConst;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import com.github.houbb.nginx4j.support.request.dispatch.http.AbstractNginxRequestDispatch;
import com.github.houbb.nginx4j.support.request.dispatch.http.AbstractNginxRequestDispatchFullResp;
import com.github.houbb.nginx4j.util.InnerMimeUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

/**
 * 文件范围查询
 *
 * @since 0.7.0
 */
@Deprecated
public class NginxRequestDispatchFileRangeOld extends AbstractNginxRequestDispatch {

    private static final Log logger = LogFactory.getLog(AbstractNginxRequestDispatchFullResp.class);

    @Override
    public void doDispatch(NginxRequestDispatchContext context) {
        final HttpRequest request = context.getRequest();
        final File file = context.getFile();
        final ChannelHandlerContext ctx = context.getCtx();

        // 解析Range头
        String rangeHeader = request.headers().get("Range");
        logger.info("[Nginx] fileRange start rangeHeader={}", rangeHeader);

        long fileLength = file.length(); // 假设file是你要发送的File对象
        long[] range = parseRange(rangeHeader, fileLength);
        long start = range[0];
        long end = range[1];

        // 构造HTTP响应
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1,
                start < 0 ? HttpResponseStatus.OK : HttpResponseStatus.PARTIAL_CONTENT);
        // 设置Content-Type
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, InnerMimeUtil.getContentTypeWithCharset(file, context.getNginxConfig().getCharset()));

        if (start >= 0) {
            // 设置Content-Range
            if (end < 0) {
                end = fileLength - 1;
            }
            response.headers().set(HttpHeaderNames.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileLength);

            // 设置Content-Length
            int contentLength = (int) (end - start + 1);
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, contentLength);

            // 发送响应头
            ctx.write(response);

            try (FileChannel fileChannel = FileChannel.open(file.toPath(), StandardOpenOption.READ)) {
                fileChannel.position(start); // 设置文件通道的起始位置

                ByteBuffer buffer = ByteBuffer.allocate(NginxConst.CHUNK_SIZE);
                while (end >= start) {
                    // 读取文件到ByteBuffer
                    int bytesRead = fileChannel.read(buffer);
                    if (bytesRead == -1) { // 文件读取完毕
                        break;
                    }
                    buffer.flip(); // 切换到读模式
                    ctx.write(new DefaultHttpContent(Unpooled.wrappedBuffer(buffer)));
                    buffer.compact(); // 保留未读取的数据，并为下次读取腾出空间
                    start += bytesRead; // 更新下一个读取的起始位置
                }
                ctx.flush(); // 确保所有数据都被发送

                // 发送结束标记
                ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT)
                        .addListener(ChannelFutureListener.CLOSE); // 如果连接断开，则关闭
            } catch (IOException e) {
                logger.error("[Nginx] NginxRequestDispatchFileRange meet ex", e);
                throw new RuntimeException(e);
            }

        }
    }

    protected long[] parseRange(String rangeHeader, long totalLength) {
        // 简单解析Range头，返回[start, end]
        // Range头格式为: "bytes=startIndex-endIndex"
        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            String range = rangeHeader.substring("bytes=".length());
            String[] parts = range.split("-");
            long start = parts[0].isEmpty() ? totalLength - 1 : Long.parseLong(parts[0]);
            long end = parts.length > 1 ? Long.parseLong(parts[1]) : totalLength - 1;
            return new long[]{start, end};
        }
        return new long[]{-1, -1}; // 表示无效的范围请求
    }

}
