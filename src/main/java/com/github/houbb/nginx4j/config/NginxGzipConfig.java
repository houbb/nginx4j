package com.github.houbb.nginx4j.config;

import java.util.Arrays;
import java.util.List;

/**
 *      gzip on;
 *      gzip_vary on;
 *      gzip_proxied any;
 *      gzip_comp_level 5;
 *      gzip_min_length 256;
 *      gzip_types text/plain text/css application/json application/javascript text/xml application/xml application/xml+rss text/javascript;
 */
public class NginxGzipConfig {

    /**
     * 是否启用
     * on 启用
     */
    private String gzip = "off";

    /**
     * 最小长度
     *
     * 1mb
     */
    private long gzipMinLength = 1024 * 1024;

    /**
     * 压缩累呗
     */
    private List<String> gzipTypes = Arrays.asList(
            "text/plain",
            "text/css",
            "text/javascript",
            "application/json",
            "application/javascript",
            "application/xml+rss"
    );

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
