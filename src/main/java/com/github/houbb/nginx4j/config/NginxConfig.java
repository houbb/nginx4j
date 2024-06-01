package com.github.houbb.nginx4j.config;

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
