package com.github.houbb.nginx4j.support.rewrite;

import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.config.NginxUserServerLocationConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 重写的结果
 *
 * @since 0.23.0
 */
public class NginxRewriteDirectiveResult {

    /**
     * 是否命中重写
     */
    private boolean matchRewrite = false;

    /**
     * 配置的重写值列表
     */
    private NginxCommonConfigEntry rewriteConfig;

    /**
     * 重写标识
     */
    private String rewriteFlag = "last";

    private String beforeUrl;

    private String afterUrl;

    // 当前配置
    private NginxUserServerLocationConfig currentLocationConfig;

    public NginxUserServerLocationConfig getCurrentLocationConfig() {
        return currentLocationConfig;
    }

    public void setCurrentLocationConfig(NginxUserServerLocationConfig currentLocationConfig) {
        this.currentLocationConfig = currentLocationConfig;
    }

    public boolean isMatchRewrite() {
        return matchRewrite;
    }

    public void setMatchRewrite(boolean matchRewrite) {
        this.matchRewrite = matchRewrite;
    }

    public NginxCommonConfigEntry getRewriteConfig() {
        return rewriteConfig;
    }

    public void setRewriteConfig(NginxCommonConfigEntry rewriteConfig) {
        this.rewriteConfig = rewriteConfig;
    }

    public String getRewriteFlag() {
        return rewriteFlag;
    }

    public void setRewriteFlag(String rewriteFlag) {
        this.rewriteFlag = rewriteFlag;
    }

    public String getBeforeUrl() {
        return beforeUrl;
    }

    public void setBeforeUrl(String beforeUrl) {
        this.beforeUrl = beforeUrl;
    }

    public String getAfterUrl() {
        return afterUrl;
    }

    public void setAfterUrl(String afterUrl) {
        this.afterUrl = afterUrl;
    }

    @Override
    public String toString() {
        return "NginxRewriteDirectiveResult{" +
                "matchRewrite=" + matchRewrite +
                ", rewriteConfig=" + rewriteConfig +
                ", rewriteFlag='" + rewriteFlag + '\'' +
                ", beforeUrl='" + beforeUrl + '\'' +
                ", afterUrl='" + afterUrl + '\'' +
                '}';
    }

}
