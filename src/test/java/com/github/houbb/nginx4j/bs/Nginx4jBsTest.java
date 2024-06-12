package com.github.houbb.nginx4j.bs;

import com.github.houbb.nginx4j.config.NginxUserConfig;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;
import com.github.houbb.nginx4j.config.load.NginxUserConfigLoaders;
import com.github.houbb.nginx4j.constant.NginxConst;

import java.util.Arrays;

public class Nginx4jBsTest {

    public static void main(String[] args) {
        final String configPath = "D:\\github\\nginx4j\\nginx4j\\src\\main\\resources\\nginx.conf";
        NginxUserConfig nginxUserConfig = NginxUserConfigLoaders.configComponentFile(configPath).load();

        Nginx4jBs.newInstance()
                .nginxUserConfig(nginxUserConfig)
                .init()
                .start();
    }

}
