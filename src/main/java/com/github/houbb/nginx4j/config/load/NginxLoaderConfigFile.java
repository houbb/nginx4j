package com.github.houbb.nginx4j.config.load;

import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.config.parser.INginxParserConfig;
import com.github.houbb.nginx4j.config.parser.NginxParserConfigDefault;

public  class NginxLoaderConfigFile extends AbstractNginxLoader {

    private final String filePath;

    public NginxLoaderConfigFile(String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected NginxConfig doLoad() {
        // 解析类
        INginxParserConfig parserConfig = new NginxParserConfigDefault(filePath);

        NginxConfig config = new NginxConfig();

        //TODO: 待实现

        return config;
    }

}
