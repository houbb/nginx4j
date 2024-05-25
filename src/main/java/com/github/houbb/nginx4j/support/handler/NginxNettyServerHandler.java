package com.github.houbb.nginx4j.support.handler;

import com.github.houbb.heaven.util.io.StreamUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.request.convert.NginxRequestConvertor;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatch;
import com.github.houbb.nginx4j.support.request.dto.NginxRequestInfoBo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class NginxNettyServerHandler extends ChannelInboundHandlerAdapter {

    private static final Log logger = LogFactory.getLog(NginxNettyServerHandler.class);

    private final NginxConfig nginxConfig;

    public NginxNettyServerHandler(NginxConfig nginxConfig) {
        this.nginxConfig = nginxConfig;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String requestString = new String(bytes, nginxConfig.getCharset());
        logger.info("[Nginx] channelRead requestString={}", requestString);

        // 请求体
        final NginxRequestConvertor requestConvertor = nginxConfig.getNginxRequestConvertor();
        NginxRequestInfoBo nginxRequestInfoBo = requestConvertor.convert(requestString, nginxConfig);

        // 分发
        final NginxRequestDispatch requestDispatch = nginxConfig.getNginxRequestDispatch();
        String respText = requestDispatch.dispatch(nginxRequestInfoBo, nginxConfig);

        ByteBuf responseBuf = Unpooled.copiedBuffer(respText.getBytes());
        ctx.writeAndFlush(responseBuf)
                .addListener(ChannelFutureListener.CLOSE); // Close the channel after sending the response
        logger.info("[Nginx] channelRead writeAndFlush DONE");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("[Nginx] exceptionCaught", cause);
        ctx.close();
    }

    private byte[] tryGetIndexContent() throws IOException {
        try {
            List<String> indexHtmlList = nginxConfig.getHttpServerIndexList();

            String basicPath = nginxConfig.getHttpServerRoot();
            for(String indexHtml : indexHtmlList) {
                String fullPath = basicPath + basicPath + indexHtml;
                File file = new File(fullPath);
                if(file.exists()) {
                    logger.info("[Nginx4j] meet indexPath={}", fullPath);
                    return Files.readAllBytes(file.toPath());
                }
            }

            // 默认
            return StreamUtil.getFileBytes("index.html");
        } catch (IOException e) {
            logger.error("[Nginx4j] tryGetIndexContent meet ex", e);
            throw new Nginx4jException(e);
        }
    }

}
