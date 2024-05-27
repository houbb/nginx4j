package com.github.houbb.nginx4j.bs;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.nginx4j.api.INginxServer;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.config.NginxGzipConfig;
import com.github.houbb.nginx4j.config.NginxSendFileConfig;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.index.NginxIndexFile;
import com.github.houbb.nginx4j.support.index.NginxIndexFileDefault;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatch;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchManager;
import com.github.houbb.nginx4j.support.server.NginxServerNetty;

import java.util.Arrays;
import java.util.List;

public class Nginx4jBs {

    /**
     * 文件编码
     */
    private String charset = "UTF-8";

    /**
     * http 服务监听端口
     */
    private int httpServerListen = 8080;

    /**
     * 根路径
     * D:\github\nginx4j\nginx4j
     */
    private String httpServerRoot = "D:\\data\\nginx4j";

    /**
     * 欢迎路径
     */
    private List<String> httpServerIndexList = Arrays.asList("index.html", "index.html");

    /**
     * 首页内容
     */
    private NginxIndexFile nginxIndexFile = new NginxIndexFileDefault();

    /**
     * 请求分发
     */
    private NginxRequestDispatch nginxRequestDispatch = new NginxRequestDispatchManager();


    private NginxConfig nginxConfig;

    /**
     * 压缩类别
     */
    private NginxGzipConfig nginxGzipConfig = new NginxGzipConfig();

    /**
     * @since 0.9.0
     */
    private NginxSendFileConfig nginxSendFileConfig = new NginxSendFileConfig();

    public Nginx4jBs nginxIndexFile(NginxIndexFile nginxIndexFile) {
        this.nginxIndexFile = nginxIndexFile;
        return this;
    }

    public Nginx4jBs nginxSendFileConfig(NginxSendFileConfig nginxSendFileConfig) {
        this.nginxSendFileConfig = nginxSendFileConfig;
        return this;
    }

    public Nginx4jBs nginxGzipConfig(NginxGzipConfig nginxGzipConfig) {
        this.nginxGzipConfig = nginxGzipConfig;
        return this;
    }

    private INginxServer nginxServer = new NginxServerNetty();

    public Nginx4jBs nginxIndexContent(NginxIndexFile nginxIndexFile) {
        this.nginxIndexFile = nginxIndexFile;
        return this;
    }

    public Nginx4jBs nginxRequestDispatch(NginxRequestDispatch nginxRequestDispatch) {
        this.nginxRequestDispatch = nginxRequestDispatch;
        return this;
    }

    public Nginx4jBs charset(String charset) {
        this.charset = charset;
        return this;
    }

    public Nginx4jBs nginxServer(INginxServer nginxServer) {
        this.nginxServer = nginxServer;
        return this;
    }

    public Nginx4jBs httpServerListen(int httpServerListen) {
        this.httpServerListen = httpServerListen;
        return this;
    }

    public Nginx4jBs httpServerRoot(String httpServerRoot) {
        this.httpServerRoot = httpServerRoot;
        return this;
    }

    public Nginx4jBs httpServerIndexList(List<String> httpServerIndexList) {
        this.httpServerIndexList = httpServerIndexList;
        return this;
    }

    public Nginx4jBs nginxConfig(NginxConfig nginxConfig) {
        this.nginxConfig = nginxConfig;
        return this;
    }

    public void setNginxServer(INginxServer nginxServer) {
        ArgUtil.notNull(nginxServer, "nginxServer");

        this.nginxServer = nginxServer;
    }

    public static Nginx4jBs newInstance() {
        return new Nginx4jBs();
    }

    public Nginx4jBs init() {
        nginxConfig = new NginxConfig();
        nginxConfig.setCharset(charset);
        nginxConfig.setHttpServerRoot(httpServerRoot);
        nginxConfig.setHttpServerListen(httpServerListen);
        nginxConfig.setHttpServerIndexList(httpServerIndexList);
        nginxConfig.setNginxRequestDispatch(nginxRequestDispatch);
        nginxConfig.setNginxGzipConfig(nginxGzipConfig);
        nginxConfig.setNginxSendFileConfig(nginxSendFileConfig);
        nginxConfig.setNginxIndexFile(nginxIndexFile);

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
