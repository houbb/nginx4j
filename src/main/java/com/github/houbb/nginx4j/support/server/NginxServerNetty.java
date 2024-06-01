package com.github.houbb.nginx4j.support.server;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.api.INginxServer;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.handler.NginxNettyServerHandler;
import com.github.houbb.nginx4j.util.InnerNetUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * netty 实现
 *
 * @author 老马啸西风
 * @since 0.2.0
 */
public class NginxServerNetty implements INginxServer {

    private static final Log log = LogFactory.getLog(NginxServerNetty.class);


    private NginxConfig nginxConfig;

    @Override
    public void init(NginxConfig nginxConfig) {
        this.nginxConfig = nginxConfig;
    }

    @Override
    public void start() {
        Set<Integer> httpServerPortSet = nginxConfig.getNginxUserConfig().getServerPortSet();

        // 需要验证这里是否支持多个？
        for(Integer port : httpServerPortSet) {
            log.info("[Server] 开始初始化 port={}", port);

            // 服务器监听的端口号
            String host = InnerNetUtil.getHost();

            EventLoopGroup bossGroup = new NioEventLoopGroup();
            //worker 线程池的数量默认为 CPU 核心数的两倍
            EventLoopGroup workerGroup = new NioEventLoopGroup();

            try {
                final String httpServerPrefix = String.format("http://%s:%s/", host, port);
                nginxConfig.setHttpServerPrefix(httpServerPrefix);

                ServerBootstrap serverBootstrap = new ServerBootstrap();
                serverBootstrap.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline p = ch.pipeline();

                                p.addLast(new HttpRequestDecoder()); // 请求消息解码器
                                p.addLast(new HttpObjectAggregator(65536)); // 目的是将多个消息转换为单一的request或者response对象
                                p.addLast(new HttpResponseEncoder()); // 响应解码器
                                p.addLast(new ChunkedWriteHandler()); // 目的是支持异步大文件传输

                                // 设置读写超时
                                p.addLast(new ReadTimeoutHandler(30, TimeUnit.SECONDS));
                                p.addLast(new WriteTimeoutHandler(30, TimeUnit.SECONDS));
                                // 设置空闲检测
                                p.addLast(new IdleStateHandler(60, 30, 0, TimeUnit.SECONDS));

                                // 业务逻辑
                                p.addLast(new NginxNettyServerHandler(nginxConfig));
                            }
                        })
                        .option(ChannelOption.SO_BACKLOG, 128)
                        .childOption(ChannelOption.SO_KEEPALIVE, true);

                // Bind and start to accept incoming connections.
                ChannelFuture future = serverBootstrap.bind(port).sync();

                log.info("[Nginx4j] listen on {}", httpServerPrefix);

                // Wait until the server socket is closed.
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                log.error("[Nginx4j] start meet ex", e);
                throw new Nginx4jException(e);
            } finally {
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();

                log.info("[Nginx4j] shutdownGracefully", host, port);
            }
        }
    }

    @Override
    public void destroy() {

    }

}
