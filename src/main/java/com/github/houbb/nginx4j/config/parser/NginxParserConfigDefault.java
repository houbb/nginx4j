package com.github.houbb.nginx4j.config.parser;

import com.github.odiszapc.nginxparser.NgxConfig;

import java.io.IOException;

public class NginxParserConfigDefault extends NginxParserBlockDefault implements INginxParserConfig {

    /**
     * 配置文件路径
     */
    private final String configFile;

    public NginxParserConfigDefault(String configFile) {
        this.configFile = configFile;
    }

    @Override
    public void load() {
        try {
            super.innerNgxConfig = NgxConfig.read(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
