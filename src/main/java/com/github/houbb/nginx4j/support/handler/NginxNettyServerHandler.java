package com.github.houbb.nginx4j.support.handler;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;
import com.github.houbb.nginx4j.config.NginxUserServerLocationConfig;
import com.github.houbb.nginx4j.config.location.INginxLocationMatch;
import com.github.houbb.nginx4j.constant.NginxConst;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatch;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * netty 处理类
 * @author 老马啸西风
 * @since 0.2.0
 */
public class NginxNettyServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Log logger = LogFactory.getLog(NginxNettyServerHandler.class);

    private final NginxConfig nginxConfig;

    /**
     * @since 0.19.0
     */
    private static final Map<ChannelId, AtomicInteger> connectionRequestCount = new ConcurrentHashMap<>();

    public NginxNettyServerHandler(NginxConfig nginxConfig) {
        this.nginxConfig = nginxConfig;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 初始化连接请求计数
        connectionRequestCount.put(ctx.channel().id(), new AtomicInteger(0));
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 清理连接请求计数
        connectionRequestCount.remove(ctx.channel().id());
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        final String id = ctx.channel().id().asLongText();
        logger.info("[Nginx] channelRead writeAndFlush start request={}, id={}", request, id);

        // 分发
        NginxUserServerConfig nginxUserServerConfig = getNginxUserServerConfig(request);
        NginxUserServerLocationConfig currentLocationConfig = getCurrentServerLocation(nginxUserServerConfig, request);

        final NginxRequestDispatch requestDispatch = nginxConfig.getNginxRequestDispatch();
        NginxRequestDispatchContext context = new NginxRequestDispatchContext();
        context.setCtx(ctx);
        context.setNginxConfig(nginxConfig);
        context.setRequest(request);
        context.setCurrentNginxUserServerConfig(nginxUserServerConfig);
        context.setCurrentUserServerLocationConfig(currentLocationConfig);
        context.setConnectionRequestCount(connectionRequestCount);

        requestDispatch.dispatch(context);

        logger.info("[Nginx] channelRead writeAndFlush DONE id={}", id);
    }

    private NginxUserServerConfig getNginxUserServerConfig(FullHttpRequest request) {
        String hostName = getHostName(request);

        return getNginxUserServerConfig(hostName);
    }

    /**
     * 获取当前的服务端地址
     * @param request 请求
     * @param nginxUserServerConfig 配置
     * @return 结果
     * @since 0.16.0
     */
    private NginxUserServerLocationConfig getCurrentServerLocation(NginxUserServerConfig nginxUserServerConfig,
                                                                   FullHttpRequest request) {
        List<NginxUserServerLocationConfig> configList = nginxUserServerConfig.getLocations();
        if(CollectionUtil.isNotEmpty(configList)) {
            final INginxLocationMatch nginxLocationMatch = nginxConfig.getNginxLocationMatch();

            for(NginxUserServerLocationConfig config : configList) {
                // 是否匹配
                if(nginxLocationMatch.matchConfig(config, request, nginxConfig)) {
                    return config;
                }
            }
        }

        // 默认值
        logger.info("未命中任何 location 配置，使用默认配置");
        return nginxUserServerConfig.getDefaultLocation();
    }

    /**
     * 按照 hostName 匹配
     *
     * TODO: 这个匹配策略可以单独独立出来，后续可以拓展。
     * 比如最佳的 URL 匹配等等。
     *
     * @param hostName hostName
     * @return 结果
     */
    public NginxUserServerConfig getNginxUserServerConfig(String hostName) {
        final Map<String, List<NginxUserServerConfig>> serverConfigMap = nginxConfig.getNginxUserConfig().getCurrentServerConfigMap();
        List<NginxUserServerConfig> serverConfigList = serverConfigMap.get(hostName);
        // 返回自定义
        if(CollectionUtil.isNotEmpty(serverConfigList)) {
            return serverConfigList.get(0);
        }

        // 默认的配置
        List<NginxUserServerConfig> currentDefineserverConfigList = serverConfigMap.get(NginxConst.DEFAULT_SERVER);
        if(CollectionUtil.isNotEmpty(currentDefineserverConfigList)) {
            return currentDefineserverConfigList.get(0);
        }

        // 全局默认
        return nginxConfig.getNginxUserConfig().getDefaultServerConfig();
    }

    private String getHostName(FullHttpRequest request) {
        return request.headers().get(NginxConst.HEADER_HOST);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("[Nginx] exceptionCaught", cause);
//        ctx.close();
    }

}
