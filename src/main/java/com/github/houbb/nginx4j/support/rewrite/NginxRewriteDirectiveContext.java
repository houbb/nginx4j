package com.github.houbb.nginx4j.support.rewrite;

import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;
import com.github.houbb.nginx4j.config.NginxUserServerLocationConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 重写的结果
 *
 * @since 0.23.0
 */
public class NginxRewriteDirectiveContext {

    private NginxConfig nginxConfig;

    // 分发
    private NginxUserServerConfig nginxUserServerConfig;

    // 当前配置
    private NginxUserServerLocationConfig currentLocationConfig;

    /**
     * 上下文
     */
    private ChannelHandlerContext ctx;

    /**
     * 请求
     */
    private FullHttpRequest request;

    public NginxConfig getNginxConfig() {
        return nginxConfig;
    }

    public void setNginxConfig(NginxConfig nginxConfig) {
        this.nginxConfig = nginxConfig;
    }

    public NginxUserServerConfig getNginxUserServerConfig() {
        return nginxUserServerConfig;
    }

    public void setNginxUserServerConfig(NginxUserServerConfig nginxUserServerConfig) {
        this.nginxUserServerConfig = nginxUserServerConfig;
    }

    public NginxUserServerLocationConfig getCurrentLocationConfig() {
        return currentLocationConfig;
    }

    public void setCurrentLocationConfig(NginxUserServerLocationConfig currentLocationConfig) {
        this.currentLocationConfig = currentLocationConfig;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public FullHttpRequest getRequest() {
        return request;
    }

    public void setRequest(FullHttpRequest request) {
        this.request = request;
    }
}
