package com.github.houbb.nginx4j.constant;

public class NginxConst {

    /**
     * 大文件 8MB
     */
    public static final long BIG_FILE_SIZE = 8 * 1024 * 1024;

    /**
     * CHUNK 大小
     */
    public static final int CHUNK_SIZE = 1024 * 1024;

    /**
     * 默认 server
     *
     * @since 0.12.0
     */
    public static final String DEFAULT_SERVER = "default_server";

    /**
     * 头信息-host
     *
     * @since 0.12.0
     */
    public static final String HEADER_HOST = "Host";

    /**
     * 占位符前缀
     *
     * @since 0.17.0
     */
    public static final String PLACEHOLDER_PREFIX = "$";

}
