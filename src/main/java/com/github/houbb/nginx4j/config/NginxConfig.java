package com.github.houbb.nginx4j.config;

import com.github.houbb.nginx4j.config.location.INginxLocationMatch;
import com.github.houbb.nginx4j.config.param.INginxParamManager;
import com.github.houbb.nginx4j.support.index.NginxIndexFile;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatch;

public class NginxConfig {

    /**
     * 前缀请求
     */
    private String httpServerPrefix;

    /**
     * 用户自定义配置
     */
    private NginxUserConfig nginxUserConfig;

    private NginxIndexFile nginxIndexFile;

    private NginxRequestDispatch nginxRequestDispatch;

    /**
     * 参数匹配条件
     * @since 0.16.0
     */
    private INginxLocationMatch nginxLocationMatch;

    /**
     * 参数管理类
     *
     * @since 0.16.0
     */
    private INginxParamManager nginxParamManager;

    public INginxParamManager getNginxParamManager() {
        return nginxParamManager;
    }

    public void setNginxParamManager(INginxParamManager nginxParamManager) {
        this.nginxParamManager = nginxParamManager;
    }

    public INginxLocationMatch getNginxLocationMatch() {
        return nginxLocationMatch;
    }

    public void setNginxLocationMatch(INginxLocationMatch nginxLocationMatch) {
        this.nginxLocationMatch = nginxLocationMatch;
    }

    public String getHttpServerPrefix() {
        return httpServerPrefix;
    }

    public void setHttpServerPrefix(String httpServerPrefix) {
        this.httpServerPrefix = httpServerPrefix;
    }

    public NginxUserConfig getNginxUserConfig() {
        return nginxUserConfig;
    }

    public void setNginxUserConfig(NginxUserConfig nginxUserConfig) {
        this.nginxUserConfig = nginxUserConfig;
    }

    public NginxIndexFile getNginxIndexFile() {
        return nginxIndexFile;
    }

    public void setNginxIndexFile(NginxIndexFile nginxIndexFile) {
        this.nginxIndexFile = nginxIndexFile;
    }

    public NginxRequestDispatch getNginxRequestDispatch() {
        return nginxRequestDispatch;
    }

    public void setNginxRequestDispatch(NginxRequestDispatch nginxRequestDispatch) {
        this.nginxRequestDispatch = nginxRequestDispatch;
    }
}
