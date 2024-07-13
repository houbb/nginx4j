package com.github.houbb.nginx4j.config;

import com.github.houbb.nginx4j.config.location.INginxLocationMatch;
import com.github.houbb.nginx4j.config.param.INginxParamManager;
import com.github.houbb.nginx4j.support.condition.NginxIf;
import com.github.houbb.nginx4j.support.errorpage.INginxErrorPageManage;
import com.github.houbb.nginx4j.support.index.NginxIndexFile;
import com.github.houbb.nginx4j.support.map.NginxMapDirective;
import com.github.houbb.nginx4j.support.placeholder.INginxPlaceholderManager;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatch;

public class NginxConfig {

    /**
     * if 策略
     */
    private NginxIf nginxIf;

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

    /**
     * 占位符管理类
     * @since 0.17.0
     */
    private INginxPlaceholderManager nginxPlaceholderManager;

    private NginxMapDirective nginxMapDirective;

    /**
     * @since 0.25.0
     */
    private INginxErrorPageManage nginxErrorPageManage;

    public INginxErrorPageManage getNginxErrorPageManage() {
        return nginxErrorPageManage;
    }

    public void setNginxErrorPageManage(INginxErrorPageManage nginxErrorPageManage) {
        this.nginxErrorPageManage = nginxErrorPageManage;
    }

    public NginxMapDirective getNginxMapDirective() {
        return nginxMapDirective;
    }

    public void setNginxMapDirective(NginxMapDirective nginxMapDirective) {
        this.nginxMapDirective = nginxMapDirective;
    }

    public NginxIf getNginxIf() {
        return nginxIf;
    }

    public void setNginxIf(NginxIf nginxIf) {
        this.nginxIf = nginxIf;
    }

    public INginxPlaceholderManager getNginxPlaceholderManager() {
        return nginxPlaceholderManager;
    }

    public void setNginxPlaceholderManager(INginxPlaceholderManager nginxPlaceholderManager) {
        this.nginxPlaceholderManager = nginxPlaceholderManager;
    }

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
