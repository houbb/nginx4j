package com.github.houbb.nginx4j.config.load.component;

import com.github.houbb.nginx4j.config.NginxUserUpstreamConfig;

/**
 * @since 0.18.0
 */
public interface INginxUserUpstreamConfigLoad {

    NginxUserUpstreamConfig load();

}
