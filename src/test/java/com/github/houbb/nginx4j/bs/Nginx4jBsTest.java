package com.github.houbb.nginx4j.bs;

import com.github.houbb.nginx4j.config.NginxGzipConfig;
import com.github.houbb.nginx4j.config.NginxUserConfig;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;
import com.github.houbb.nginx4j.constant.NginxConst;

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

        NginxUserServerConfig serverConfig = NginxUserServerConfigBs.newInstance()
                .nginxGzipConfig(gzipConfig)
                .build();

        NginxUserConfig nginxUserConfig = NginxUserConfigBs.newInstance()
                .addServerConfig(8080, NginxConst.DEFAULT_SERVER, serverConfig)
                .build();

        Nginx4jBs.newInstance()
                .nginxUserConfig(nginxUserConfig)
                .init()
                .start();
    }

}
