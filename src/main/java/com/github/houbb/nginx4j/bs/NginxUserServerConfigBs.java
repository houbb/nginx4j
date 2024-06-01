package com.github.houbb.nginx4j.bs;

import com.github.houbb.nginx4j.config.NginxGzipConfig;
import com.github.houbb.nginx4j.config.NginxSendFileConfig;
import com.github.houbb.nginx4j.config.NginxUserConfig;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;

import java.util.Arrays;
import java.util.List;

/**
 * @since 0.12.0
 */
public class NginxUserServerConfigBs {

    /**
     * 文件编码
     */
    private String charset = "UTF-8";

    /**
     * http 服务监听端口
     */
    private int httpServerListen = 8080;

    /**
     * 监听的 header=host 的信息
     */
    private String httpServerHost = "localhost";

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
     * 压缩类别
     */
    private NginxGzipConfig nginxGzipConfig = new NginxGzipConfig();

    /**
     * @since 0.9.0
     */
    private NginxSendFileConfig nginxSendFileConfig = new NginxSendFileConfig();

    public static NginxUserServerConfigBs newInstance() {
        return new NginxUserServerConfigBs();
    }

    public String httpServerHost() {
        return httpServerHost;
    }

    public NginxUserServerConfigBs httpServerHost(String httpServerHost) {
        this.httpServerHost = httpServerHost;
        return this;
    }

    public NginxUserServerConfigBs charset(String charset) {
        this.charset = charset;
        return this;
    }

    public NginxUserServerConfigBs httpServerListen(int httpServerListen) {
        this.httpServerListen = httpServerListen;
        return this;
    }

    public NginxUserServerConfigBs httpServerRoot(String httpServerRoot) {
        this.httpServerRoot = httpServerRoot;
        return this;
    }

    public NginxUserServerConfigBs httpServerIndexList(List<String> httpServerIndexList) {
        this.httpServerIndexList = httpServerIndexList;
        return this;
    }

    public NginxUserServerConfigBs nginxGzipConfig(NginxGzipConfig nginxGzipConfig) {
        this.nginxGzipConfig = nginxGzipConfig;
        return this;
    }

    public NginxUserServerConfigBs nginxSendFileConfig(NginxSendFileConfig nginxSendFileConfig) {
        this.nginxSendFileConfig = nginxSendFileConfig;
        return this;
    }

    public NginxUserServerConfig build() {
        NginxUserServerConfig config = new NginxUserServerConfig();
        config.setCharset(charset);
        config.setNginxGzipConfig(nginxGzipConfig);
        config.setNginxSendFileConfig(nginxSendFileConfig);
        config.setHttpServerIndexList(httpServerIndexList);
        config.setHttpServerListen(httpServerListen);
        config.setHttpServerRoot(httpServerRoot);
        config.setHttpServerHost(httpServerHost);

        return config;
    }
}
