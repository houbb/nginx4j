package com.github.houbb.nginx4j.bs;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.nginx4j.api.INginxServer;
import com.github.houbb.nginx4j.config.*;
import com.github.houbb.nginx4j.config.location.INginxLocationMatch;
import com.github.houbb.nginx4j.config.location.NginxLocationMatchDefault;
import com.github.houbb.nginx4j.config.param.INginxParamManager;
import com.github.houbb.nginx4j.config.param.NginxParamManagerBase;
import com.github.houbb.nginx4j.config.param.NginxParamManagerDefault;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.index.NginxIndexFile;
import com.github.houbb.nginx4j.support.index.NginxIndexFileDefault;
import com.github.houbb.nginx4j.support.placeholder.INginxPlaceholderManager;
import com.github.houbb.nginx4j.support.placeholder.NginxPlaceholderManagerDefault;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatch;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchManager;
import com.github.houbb.nginx4j.support.server.NginxServerNetty;

public class Nginx4jBs {

    /**
     * 首页内容
     */
    private NginxIndexFile nginxIndexFile = new NginxIndexFileDefault();

    /**
     * 请求分发
     */
    private NginxRequestDispatch nginxRequestDispatch = new NginxRequestDispatchManager();

    /**
     * 配置文件
     */
    private NginxUserConfig nginxUserConfig = NginxUserConfigBs.newInstance().build();

    /**
     * 服务实现策略
     */
    private INginxServer nginxServer = new NginxServerNetty();

    /**
     * 默认匹配策略
     * @since 0.16.0
     */
    private INginxLocationMatch nginxLocationMatch = new NginxLocationMatchDefault();

    /**
     * 默认的参数管理
     *
     * @since 0.16.0
     */
    private INginxParamManager nginxParamManager = new NginxParamManagerDefault();

    /**
     * 占位符管理类
     * @since 0.17.0
     */
    private INginxPlaceholderManager nginxPlaceholderManager = new NginxPlaceholderManagerDefault();

    private NginxConfig nginxConfig;

    public Nginx4jBs nginxPlaceholderManager(INginxPlaceholderManager nginxPlaceholderManager) {
        ArgUtil.notNull(nginxPlaceholderManager, "nginxPlaceholderManager");

        this.nginxPlaceholderManager = nginxPlaceholderManager;
        return this;
    }

    public Nginx4jBs nginxParamManager(INginxParamManager nginxParamManager) {
        ArgUtil.notNull(nginxParamManager, "nginxParamManager");

        this.nginxParamManager = nginxParamManager;
        return this;
    }

    public Nginx4jBs nginxLocationMatch(INginxLocationMatch nginxLocationMatch) {
        ArgUtil.notNull(nginxLocationMatch, "nginxLocationMatch");

        this.nginxLocationMatch = nginxLocationMatch;
        return this;
    }

    public Nginx4jBs nginxIndexFile(NginxIndexFile nginxIndexFile) {
        this.nginxIndexFile = nginxIndexFile;
        return this;
    }

    public Nginx4jBs nginxRequestDispatch(NginxRequestDispatch nginxRequestDispatch) {
        ArgUtil.notNull(nginxRequestDispatch, "nginxRequestDispatch");

        this.nginxRequestDispatch = nginxRequestDispatch;
        return this;
    }

    public Nginx4jBs nginxUserConfig(NginxUserConfig nginxUserConfig) {
        ArgUtil.notNull(nginxUserConfig, "nginxUserConfig");

        this.nginxUserConfig = nginxUserConfig;
        return this;
    }

    public void nginxServer(INginxServer nginxServer) {
        ArgUtil.notNull(nginxServer, "nginxServer");

        this.nginxServer = nginxServer;
    }

    public static Nginx4jBs newInstance() {
        return new Nginx4jBs();
    }

    public Nginx4jBs init() {
        this.nginxConfig = new NginxConfig();
        nginxConfig.setNginxRequestDispatch(nginxRequestDispatch);
        nginxConfig.setNginxIndexFile(nginxIndexFile);
        nginxConfig.setNginxUserConfig(nginxUserConfig);
        nginxConfig.setNginxLocationMatch(nginxLocationMatch);
        nginxConfig.setNginxParamManager(nginxParamManager);
        nginxConfig.setNginxPlaceholderManager(nginxPlaceholderManager);

        return this;
    }

    public Nginx4jBs start() {
        if(this.nginxConfig == null) {
            throw new Nginx4jException("nginxConfig not init!");
        }

        this.nginxServer.init(nginxConfig);
        this.nginxServer.start();

        return this;
    }

}
