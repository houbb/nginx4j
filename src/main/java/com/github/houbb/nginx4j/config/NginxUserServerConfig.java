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
    private String httpServerName;

    /**
     * 根路径
     */
    private String httpServerRoot;

    /**
     * 欢迎路径
     */
    private List<String> httpServerIndexList;

    /**
     * 是否启用
     * on 启用
     * @since 0.13.0
     */
    private String sendFile;

    /**
     * 是否启用
     * on 启用
     */
    private String gzip;

    /**
     * 最小长度
     *
     * 1mb
     */
    private long gzipMinLength;

    /**
     * 压缩累呗
     */
    private List<String> gzipTypes;

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getHttpServerName() {
        return httpServerName;
    }

    public void setHttpServerName(String httpServerName) {
        this.httpServerName = httpServerName;
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

    public String getSendFile() {
        return sendFile;
    }

    public void setSendFile(String sendFile) {
        this.sendFile = sendFile;
    }

    public String getGzip() {
        return gzip;
    }

    public void setGzip(String gzip) {
        this.gzip = gzip;
    }

    public long getGzipMinLength() {
        return gzipMinLength;
    }

    public void setGzipMinLength(long gzipMinLength) {
        this.gzipMinLength = gzipMinLength;
    }

    public List<String> getGzipTypes() {
        return gzipTypes;
    }

    public void setGzipTypes(List<String> gzipTypes) {
        this.gzipTypes = gzipTypes;
    }
}
