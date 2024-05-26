package com.github.houbb.nginx4j.bs;

import com.github.houbb.nginx4j.config.NginxGzipConfig;

import java.util.Arrays;

public class Nginx4jBsTest {

    public static void main(String[] args) {
        NginxGzipConfig gzipConfig = new NginxGzipConfig();
        gzipConfig.setGzip("on");
        gzipConfig.setGzipMinLength(256);
        gzipConfig.setGzipTypes(Arrays.asList(
                "text/plain",
                "text/css",
                "text/javascript",
                "application/json",
                "application/javascript",
                "application/xml+rss"
        ));

        Nginx4jBs.newInstance()
                .nginxGzipConfig(gzipConfig)
                .init()
                .start();
    }

}
