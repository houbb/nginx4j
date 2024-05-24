package com.github.houbb.nginx4j.bs;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.nginx4j.api.INginxServer;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.server.NginxServerSocket;

import java.util.Arrays;
import java.util.List;

public class Nginx4jBs {

    /**
     * http 服务监听端口
     */
    private int httpServerListen = 8080;

    /**
     * 根路径
     * D:\github\nginx4j\nginx4j
     */
    private String httpServerRoot = "D:\\data\\nginx4j\\";

    /**
     * 欢迎路径
     */
    private List<String> httpServerIndexList = Arrays.asList("index.html", "index.html");


    private NginxConfig nginxConfig;

    private INginxServer nginxServer = new NginxServerSocket();

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
        this.nginxConfig = new NginxConfig();
        this.nginxConfig.setHttpServerRoot(httpServerRoot);
        this.nginxConfig.setHttpServerListen(httpServerListen);
        this.nginxConfig.setHttpServerIndexList(httpServerIndexList);
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
