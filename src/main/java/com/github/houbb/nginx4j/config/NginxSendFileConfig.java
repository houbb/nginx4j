package com.github.houbb.nginx4j.config;

/**
 *      gzip on;
 *      gzip_vary on;
 *      gzip_proxied any;
 *      gzip_comp_level 5;
 *      gzip_min_length 256;
 *      gzip_types text/plain text/css application/json application/javascript text/xml application/xml application/xml+rss text/javascript;
 */
public class NginxSendFileConfig {

    /**
     * 是否启用
     * on 启用
     */
    private String sendFile = "on";

    public String getSendFile() {
        return sendFile;
    }

    public NginxSendFileConfig sendfile(String sendfile) {
        this.sendFile = sendfile;
        return this;
    }

}
