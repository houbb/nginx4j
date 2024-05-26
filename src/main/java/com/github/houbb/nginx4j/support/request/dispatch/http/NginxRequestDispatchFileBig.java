package com.github.houbb.nginx4j.support.request.dispatch.http;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.constant.NginxConst;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import com.github.houbb.nginx4j.util.InnerMimeUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

/**
 * 大文件
 *
 * @since 0.6.0
 */
public class NginxRequestDispatchFileBig extends AbstractNginxRequestDispatch {

    private static final Log logger = LogFactory.getLog(AbstractNginxRequestDispatchFullResp.class);

    @Override
    public void doDispatch(NginxRequestDispatchContext context) {
        final FullHttpRequest request = context.getRequest();
        final File targetFile = context.getFile();
        final String bigFilePath = targetFile.getAbsolutePath();
        final long fileLength = targetFile.length();


        logger.info("[Nginx] match big file, path={}", bigFilePath);

        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.headers().set(HttpHeaderNames.CONTENT_DISPOSITION, "attachment; filename=\"" + targetFile.getName() + "\"");
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, InnerMimeUtil.getContentType(targetFile));
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, fileLength);

        final ChannelHandlerContext ctx = context.getCtx();
        ctx.write(response);

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
                logger.info("[Nginx] bigFile process >>>>>>>>>>> {}/{}", totalRead, totalLength);
            }

            // 发送结束标记
            ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT)
                    .addListener(ChannelFutureListener.CLOSE);
        } catch (Exception e) {
            logger.error("[Nginx] bigFile meet ex", e);
        }
    }

}
