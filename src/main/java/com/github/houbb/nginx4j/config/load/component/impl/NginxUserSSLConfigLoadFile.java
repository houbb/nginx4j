package com.github.houbb.nginx4j.config.load.component.impl;

import com.github.houbb.nginx4j.config.NginxUserSSLConfig;
import com.github.houbb.nginx4j.config.load.component.INginxUserSSLConfigLoad;
import com.github.odiszapc.nginxparser.NgxConfig;

/**
 * @since 0.18.0
 */
public class NginxUserSSLConfigLoadFile implements INginxUserSSLConfigLoad {

    private final NgxConfig conf;

    public NginxUserSSLConfigLoadFile(NgxConfig conf) {
        this.conf = conf;
    }

    @Override
    public NginxUserSSLConfig load() {
        NginxUserSSLConfig config = new NginxUserSSLConfig();

        return config;
    }


}
