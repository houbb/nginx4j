package com.github.houbb.nginx4j.config;

import java.util.List;

/**
 * 用户配置
 *
 * @since 0.12.0
 */
public class NginxUserServerConfig {

    /**
     * 文件编码
     */
    private String charset;

    /**
     * http 服务监听端口
     */
    private int httpServerListen;

    /**
     * 监听的 header=host 的信息
     */
    private String httpServerHost;

    /**
     * 根路径
     */
    private String httpServerRoot;

    /**
     * 欢迎路径
     */
    private List<String> httpServerIndexList;

    /**
     * @since 0.8.0
     */
    private NginxGzipConfig nginxGzipConfig;

    /**
     * 零拷贝配置
     *
     * @since 0.9.0
     */
    private NginxSendFileConfig nginxSendFileConfig;

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getHttpServerHost() {
        return httpServerHost;
    }

    public void setHttpServerHost(String httpServerHost) {
        this.httpServerHost = httpServerHost;
    }

    public int getHttpServerListen() {
        return httpServerListen;
    }

    public void setHttpServerListen(int httpServerListen) {
        this.httpServerListen = httpServerListen;
    }

    public String getHttpServerRoot() {
        return httpServerRoot;
    }

    public void setHttpServerRoot(String httpServerRoot) {
        this.httpServerRoot = httpServerRoot;
    }

    public List<String> getHttpServerIndexList() {
        return httpServerIndexList;
    }

    public void setHttpServerIndexList(List<String> httpServerIndexList) {
        this.httpServerIndexList = httpServerIndexList;
    }

    public NginxGzipConfig getNginxGzipConfig() {
        return nginxGzipConfig;
    }

    public void setNginxGzipConfig(NginxGzipConfig nginxGzipConfig) {
        this.nginxGzipConfig = nginxGzipConfig;
    }

    public NginxSendFileConfig getNginxSendFileConfig() {
        return nginxSendFileConfig;
    }

    public void setNginxSendFileConfig(NginxSendFileConfig nginxSendFileConfig) {
        this.nginxSendFileConfig = nginxSendFileConfig;
    }

    @Override
    public String toString() {
        return "NginxUserServerConfig{" +
                "charset='" + charset + '\'' +
                ", httpServerListen=" + httpServerListen +
                ", httpServerHost='" + httpServerHost + '\'' +
                ", httpServerRoot='" + httpServerRoot + '\'' +
                ", httpServerIndexList=" + httpServerIndexList +
                ", nginxGzipConfig=" + nginxGzipConfig +
                ", nginxSendFileConfig=" + nginxSendFileConfig +
                '}';
    }

}
