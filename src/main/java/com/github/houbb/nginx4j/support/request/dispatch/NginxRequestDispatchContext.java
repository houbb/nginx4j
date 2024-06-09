package com.github.houbb.nginx4j.support.request.dispatch;

import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;
import com.github.houbb.nginx4j.config.NginxUserServerLocationConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 分发上下文
 * @since 0.6.0
 */
public class NginxRequestDispatchContext {

    private ChannelHandlerContext ctx;

    private FullHttpRequest request;

    private NginxConfig nginxConfig;

    private File file;

    /**
     * 确切的文件长度
     * @since 0.10.0
     */
    private long actualFileLength;

    /**
     * 确切的开始位置
     * @since 0.10.0
     */
    private long actualStart;

    /**
     * 当前匹配的用户信息
     * @since 0.12.0
     */
    private NginxUserServerConfig currentNginxUserServerConfig;

    /**
     * 当前的配置信息
     *
     * @since 0.16.0
     */
    private NginxUserServerLocationConfig currentUserServerLocationConfig;

    /**
     * 占位符的集合
     *
     * @since 0.17.0
     */
    private Map<String, Object> placeholderMap = new HashMap<>();

    /**
     * 全局的限流值
     * @since 0.17.0
     */
    private long limitRate = 1000;

    public long getLimitRate() {
        return limitRate;
    }

    public void setLimitRate(long limitRate) {
        this.limitRate = limitRate;
    }

    public Map<String, Object> getPlaceholderMap() {
        return placeholderMap;
    }

    public void setPlaceholderMap(Map<String, Object> placeholderMap) {
        this.placeholderMap = placeholderMap;
    }

    public NginxUserServerLocationConfig getCurrentUserServerLocationConfig() {
        return currentUserServerLocationConfig;
    }

    public void setCurrentUserServerLocationConfig(NginxUserServerLocationConfig currentUserServerLocationConfig) {
        this.currentUserServerLocationConfig = currentUserServerLocationConfig;
    }

    public NginxUserServerConfig getCurrentNginxUserServerConfig() {
        return currentNginxUserServerConfig;
    }

    public void setCurrentNginxUserServerConfig(NginxUserServerConfig currentNginxUserServerConfig) {
        this.currentNginxUserServerConfig = currentNginxUserServerConfig;
    }

    public long getActualStart() {
        return actualStart;
    }

    public void setActualStart(long actualStart) {
        this.actualStart = actualStart;
    }

    public long getActualFileLength() {
        return actualFileLength;
    }

    public void setActualFileLength(long actualFileLength) {
        this.actualFileLength = actualFileLength;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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

    public NginxConfig getNginxConfig() {
        return nginxConfig;
    }

    public void setNginxConfig(NginxConfig nginxConfig) {
        this.nginxConfig = nginxConfig;
    }

    @Override
    public String toString() {
        return "NginxRequestDispatchContext{" +
                "ctx=" + ctx +
                ", request=" + request +
                ", nginxConfig=" + nginxConfig +
                ", file=" + file +
                ", actualFileLength=" + actualFileLength +
                ", actualStart=" + actualStart +
                ", currentNginxUserServerConfig=" + currentNginxUserServerConfig +
                ", currentUserServerLocationConfig=" + currentUserServerLocationConfig +
                '}';
    }

}
