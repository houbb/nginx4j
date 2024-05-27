package com.github.houbb.nginx4j.config;

import com.github.houbb.nginx4j.support.index.NginxIndexFile;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatch;

import java.util.List;

public class NginxConfig {

    /**
     * 前缀请求
     */
    private String httpServerPrefix;

    /**
     * 文件编码
     */
    private String charset;


    /**
     * http 服务监听端口
     */
    private int httpServerListen;

    /**
     * 根路径
     */
    private String httpServerRoot;

    /**
     * 欢迎路径
     */
    private List<String> httpServerIndexList;

    private NginxIndexFile nginxIndexFile;

    private NginxRequestDispatch nginxRequestDispatch;

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

    public NginxSendFileConfig getNginxSendFileConfig() {
        return nginxSendFileConfig;
    }

    public void setNginxSendFileConfig(NginxSendFileConfig nginxSendFileConfig) {
        this.nginxSendFileConfig = nginxSendFileConfig;
    }

    public NginxGzipConfig getNginxGzipConfig() {
        return nginxGzipConfig;
    }

    public void setNginxGzipConfig(NginxGzipConfig nginxGzipConfig) {
        this.nginxGzipConfig = nginxGzipConfig;
    }

    public NginxIndexFile getNginxIndexFile() {
        return nginxIndexFile;
    }

    public void setNginxIndexFile(NginxIndexFile nginxIndexFile) {
        this.nginxIndexFile = nginxIndexFile;
    }

    public String getHttpServerPrefix() {
        return httpServerPrefix;
    }

    public void setHttpServerPrefix(String httpServerPrefix) {
        this.httpServerPrefix = httpServerPrefix;
    }

    public NginxRequestDispatch getNginxRequestDispatch() {
        return nginxRequestDispatch;
    }

    public void setNginxRequestDispatch(NginxRequestDispatch nginxRequestDispatch) {
        this.nginxRequestDispatch = nginxRequestDispatch;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
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

    @Override
    public String toString() {
        return "NginxConfig{" +
                "httpServerPrefix='" + httpServerPrefix + '\'' +
                ", charset='" + charset + '\'' +
                ", httpServerListen=" + httpServerListen +
                ", httpServerRoot='" + httpServerRoot + '\'' +
                ", httpServerIndexList=" + httpServerIndexList +
                ", nginxIndexFile=" + nginxIndexFile +
                ", nginxRequestDispatch=" + nginxRequestDispatch +
                ", nginxGzipConfig=" + nginxGzipConfig +
                ", nginxSendFileConfig=" + nginxSendFileConfig +
                '}';
    }

}
