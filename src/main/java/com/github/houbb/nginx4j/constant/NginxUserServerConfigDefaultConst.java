package com.github.houbb.nginx4j.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 默认
 * @since 0.13.0
 */
public class NginxUserServerConfigDefaultConst {

    /**
     * 文件编码
     */
    public static final String charset = "UTF-8";

    /**
     * http 服务监听端口
     */
    public static final int httpServerListen = 8080;

    /**
     * 监听的 header=host 的信息
     */
    public static final String httpServerName = "localhost";

    /**
     * 根路径
     * D:\github\nginx4j\nginx4j
     */
    public static final String httpServerRoot = "D:\\data\\nginx4j";

    /**
     * 欢迎路径
     */
    public static final List<String> httpServerIndexList = Arrays.asList("index.html", "index.html");

    /**
     * 是否启用
     * on 启用
     * @since 0.13.0
     */
    public static final String sendFile = "on";

    /**
     * 是否启用
     * on 启用
     */
    public static final String gzip = "off";

    /**
     * 最小长度
     *
     * 1mb
     */
    public static final long gzipMinLength = 1024 * 1024;

    /**
     * 压缩累呗
     */
    public static final List<String> gzipTypes = Arrays.asList(
            "text/plain",
            "text/css",
            "text/javascript",
            "application/json",
            "application/javascript",
            "application/xml+rss"
    );

}
