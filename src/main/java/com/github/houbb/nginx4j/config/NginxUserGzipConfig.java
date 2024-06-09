package com.github.houbb.nginx4j.config;

import java.util.List;

/**
 * 用户配置
 *
 * @since 0.18.0
 */
public class NginxUserGzipConfig extends NginxCommonUserConfig {

    // gzip 启用或禁用 gzip 压缩。
    private boolean gzip;

    // gzip_comp_level 设置 gzip 压缩级别（1-9），级别越高压缩率越高，但消耗 CPU 资源越多。
    private int gzipCompLevel;

    // gzip_types 指定需要进行 gzip 压缩的 MIME 类型。
    private List<String> gzipTypes;

    // gzip_min_length 设置启用 gzip 压缩的最小文件大小。
    private int gzipMinLength;

    // gzip_buffers 设置用于压缩的缓冲区数量和大小。
    private String gzipBuffers;

    // gzip_disable 禁用指定条件下的 gzip 压缩。
    private List<String> gzipDisable;

    // gzip_proxied 设置哪些代理响应进行 gzip 压缩。
    private String gzipProxied;

    // gzip_vary 启用或禁用 Vary 头字段，用于区分支持 gzip 的客户端。
    private boolean gzipVary;

    // gzip_http_version 设置启用 gzip 压缩的 HTTP 版本。
    private String gzipHttpVersion;

    // gzip_static 启用或禁用对静态文件的 gzip 预压缩。
    private boolean gzipStatic;

    // gzip_disable "MSIE [1-6]\." 禁用对指定浏览器版本的 gzip 压缩。
    private String gzipDisableMsie1To6;

    public boolean isGzip() {
        return gzip;
    }

    public void setGzip(boolean gzip) {
        this.gzip = gzip;
    }

    public int getGzipCompLevel() {
        return gzipCompLevel;
    }

    public void setGzipCompLevel(int gzipCompLevel) {
        this.gzipCompLevel = gzipCompLevel;
    }

    public List<String> getGzipTypes() {
        return gzipTypes;
    }

    public void setGzipTypes(List<String> gzipTypes) {
        this.gzipTypes = gzipTypes;
    }

    public int getGzipMinLength() {
        return gzipMinLength;
    }

    public void setGzipMinLength(int gzipMinLength) {
        this.gzipMinLength = gzipMinLength;
    }

    public String getGzipBuffers() {
        return gzipBuffers;
    }

    public void setGzipBuffers(String gzipBuffers) {
        this.gzipBuffers = gzipBuffers;
    }

    public List<String> getGzipDisable() {
        return gzipDisable;
    }

    public void setGzipDisable(List<String> gzipDisable) {
        this.gzipDisable = gzipDisable;
    }

    public String getGzipProxied() {
        return gzipProxied;
    }

    public void setGzipProxied(String gzipProxied) {
        this.gzipProxied = gzipProxied;
    }

    public boolean isGzipVary() {
        return gzipVary;
    }

    public void setGzipVary(boolean gzipVary) {
        this.gzipVary = gzipVary;
    }

    public String getGzipHttpVersion() {
        return gzipHttpVersion;
    }

    public void setGzipHttpVersion(String gzipHttpVersion) {
        this.gzipHttpVersion = gzipHttpVersion;
    }

    public boolean isGzipStatic() {
        return gzipStatic;
    }

    public void setGzipStatic(boolean gzipStatic) {
        this.gzipStatic = gzipStatic;
    }

    public String getGzipDisableMsie1To6() {
        return gzipDisableMsie1To6;
    }

    public void setGzipDisableMsie1To6(String gzipDisableMsie1To6) {
        this.gzipDisableMsie1To6 = gzipDisableMsie1To6;
    }
}
