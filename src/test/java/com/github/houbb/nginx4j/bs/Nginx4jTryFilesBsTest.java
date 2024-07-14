package com.github.houbb.nginx4j.bs;

import com.github.houbb.nginx4j.config.NginxUserConfig;
import com.github.houbb.nginx4j.config.load.NginxUserConfigLoaders;

/**
 * @since 0.26.0
 */
public class Nginx4jTryFilesBsTest {

    public static void main(String[] args) {
        final String configPath = "D:\\github\\nginx4j\\nginx4j\\src\\test\\resources\\nginx-try-files.conf";
        NginxUserConfig nginxUserConfig = NginxUserConfigLoaders.configComponentFile(configPath).load();

        Nginx4jBs.newInstance()
                .nginxUserConfig(nginxUserConfig)
                .init()
                .start();
    }

}
