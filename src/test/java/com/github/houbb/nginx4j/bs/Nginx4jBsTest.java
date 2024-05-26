package com.github.houbb.nginx4j.bs;

import com.github.houbb.nginx4j.config.NginxGzipConfig;

public class Nginx4jBsTest {

    public static void main(String[] args) {
        NginxGzipConfig gzipConfig = new NginxGzipConfig();
        gzipConfig.setGzip("on");
        gzipConfig.setGzipMinLength(256);

        Nginx4jBs.newInstance()
                .nginxGzipConfig(gzipConfig)
                .init()
                .start();
    }

}
