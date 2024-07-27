package com.github.houbb.nginx4j.support.request.dispatch.http;

import com.github.houbb.load.balance.support.server.IServer;
import com.github.houbb.load.balance.util.LoadBalanceHelper;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.support.balance.NginxLoadBalanceConfig;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.util.List;

/**
 * netty 实现反向代理
 *
 * @since 0.27.0
 * @author 老马啸西风
 */
public class NginxRequestDispatchProxyPass extends AbstractNginxRequestDispatch {

    private static final Log logger = LogFactory.getLog(NginxRequestDispatchProxyPass.class);

    @Override
    public void doDispatch(NginxRequestDispatchContext context) {
        // 原始的请求
        final FullHttpRequest request = context.getRequest();
        final ChannelHandlerContext ctx = context.getCtx();

        // 创建一个新的 FullHttpRequest 转发到目标服务器
        FullHttpRequest forwardedRequest = new DefaultFullHttpRequest(
                request.protocolVersion(), request.method(), request.uri(), request.content().retainedDuplicate());
        forwardedRequest.headers().set(request.headers());

        final NginxLoadBalanceConfig nginxLoadBalanceConfig = context.getBalanceConfig();

        // 创建一个新的 Bootstrap 进行 HTTP 请求
        Bootstrap b = new Bootstrap();
        b.group(ctx.channel().eventLoop())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new HttpClientCodec());
                        ch.pipeline().addLast(new HttpObjectAggregator(65536));
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<FullHttpResponse>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext clientCtx, FullHttpResponse response) throws Exception {
                                // 将目标服务器的响应写回到客户端
                                FullHttpResponse clientResponse = new DefaultFullHttpResponse(
                                        response.protocolVersion(), response.status(), response.content().retainedDuplicate());

                                clientResponse.headers().set(response.headers());

                                ctx.writeAndFlush(clientResponse).addListener(ChannelFutureListener.CLOSE);
                                clientCtx.close();
                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                logger.error("exceptionCaught meet ex", cause);
                                ctx.close();
                            }
                        });
                    }
                });

        // 连接到目标服务器并发送请求
        final IServer server = getActualServer(nginxLoadBalanceConfig);
        b.connect(server.host(), server.port()).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                future.channel().writeAndFlush(forwardedRequest);
            } else {
                ctx.close();
            }
        });
    }

    /**
     * 在 HTTP 和 HTTPS 通信中，默认端口号如下：
     *
     * - **HTTP**: 默认端口是 **80**。
     * - **HTTPS**: 默认端口是 **443**。
     *
     * 当在浏览器中输入 URL 时，如果没有指定端口，浏览器会默认使用上述端口。比如：
     *
     * - `http://example.com` 会使用端口 80。
     * - `https://example.com` 会使用端口 443。
     *
     * @return 服务器地址
     */
    private IServer getActualServer(final NginxLoadBalanceConfig nginxLoadBalanceConfig) {
        List<IServer> serverList = nginxLoadBalanceConfig.getUpstreamServerList();
        return LoadBalanceHelper.weightRoundRobbin(serverList);
    }

}
