package com.github.houbb.nginx4j.config.load.component.impl;

import com.github.houbb.nginx4j.config.NginxUserUpstreamConfig;
import com.github.houbb.nginx4j.config.load.component.INginxUserUpstreamConfigLoad;
import com.github.odiszapc.nginxparser.NgxConfig;

/**
 * @since 0.18.0
 */
public class NginxUserUpstreamConfigLoadFile implements INginxUserUpstreamConfigLoad {

    private final NgxConfig conf;

    public NginxUserUpstreamConfigLoadFile(NgxConfig conf) {
        this.conf = conf;
    }

    @Override
    public NginxUserUpstreamConfig load() {
        NginxUserUpstreamConfig config = new NginxUserUpstreamConfig();

        return config;
    }


}
