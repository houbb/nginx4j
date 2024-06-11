package com.github.houbb.nginx4j.bs;

import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;
import com.github.houbb.nginx4j.config.NginxUserServerLocationConfig;
import com.github.houbb.nginx4j.constant.NginxUserServerConfigDefaultConst;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 0.12.0
 */
public class NginxUserServerConfigBs {

    /**
     * 配置列表
     *
     * @since 0.21.0
     */
    private List<NginxCommonConfigEntry> configEntryList = new ArrayList<>();

    /**
     * 文件编码
     */
    private String charset = NginxUserServerConfigDefaultConst.charset;

    /**
     * http 服务监听端口
     */
    private int httpServerListen = NginxUserServerConfigDefaultConst.httpServerListen;

    /**
     * 监听的 header=host 的信息
     */
    private String httpServerName = NginxUserServerConfigDefaultConst.httpServerName;

    /**
     * 根路径
     * D:\github\nginx4j\nginx4j
     */
    private String httpServerRoot = NginxUserServerConfigDefaultConst.httpServerRoot;

    /**
     * 欢迎路径
     */
    private List<String> httpServerIndexList = NginxUserServerConfigDefaultConst.httpServerIndexList;

    /**
     * 是否启用
     * on 启用
     * @since 0.13.0
     */
    private String sendFile = NginxUserServerConfigDefaultConst.sendFile;

    /**
     * 是否启用
     * on 启用
     */
    private String gzip = NginxUserServerConfigDefaultConst.gzip;

    /**
     * 最小长度
     *
     * 1mb
     */
    private long gzipMinLength = NginxUserServerConfigDefaultConst.gzipMinLength;

    /**
     * 压缩累呗
     */
    private List<String> gzipTypes = NginxUserServerConfigDefaultConst.gzipTypes;

    /**
     * @since 0.16.0
     */
    private List<NginxUserServerLocationConfig> locationConfigList = new ArrayList<>();

    /**
     * 默认配置
     * @since 0.16.0
     */
    private NginxUserServerLocationConfig defaultLocationConfig = new NginxUserServerLocationConfig();


    public static NginxUserServerConfigBs newInstance() {
        return new NginxUserServerConfigBs();
    }

    public NginxUserServerConfigBs configEntryList(List<NginxCommonConfigEntry> configEntryList) {
        this.configEntryList = configEntryList;
        return this;
    }

    public NginxUserServerConfigBs locationConfigList(List<NginxUserServerLocationConfig> locationConfigList) {
        this.locationConfigList = locationConfigList;
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

    public NginxUserServerConfigBs httpServerName(String httpServerName) {
        this.httpServerName = httpServerName;
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

    public NginxUserServerConfigBs sendFile(String sendFile) {
        this.sendFile = sendFile;
        return this;
    }

    public NginxUserServerConfigBs gzip(String gzip) {
        this.gzip = gzip;
        return this;
    }

    public NginxUserServerConfigBs gzipMinLength(long gzipMinLength) {
        this.gzipMinLength = gzipMinLength;
        return this;
    }

    public NginxUserServerConfigBs gzipTypes(List<String> gzipTypes) {
        this.gzipTypes = gzipTypes;
        return this;
    }

    public NginxUserServerConfigBs defaultLocationConfig(NginxUserServerLocationConfig defaultLocationConfig) {
        this.defaultLocationConfig = defaultLocationConfig;
        return this;
    }

    public NginxUserServerConfig build() {
        NginxUserServerConfig config = new NginxUserServerConfig();
        config.setCharset(charset);
        config.setIndexList(httpServerIndexList);
        config.setListen(httpServerListen);
        config.setRoot(httpServerRoot);
        config.setName(httpServerName);
        config.setSendFile(sendFile);
        config.setGzip(gzip);
        config.setGzipMinLength(gzipMinLength);
        config.setGzipTypes(gzipTypes);
        config.setLocations(locationConfigList);
        config.setDefaultLocation(defaultLocationConfig);
        config.setConfigEntryList(configEntryList);

        return config;
    }
}
