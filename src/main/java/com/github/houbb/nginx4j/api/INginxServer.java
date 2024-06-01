package com.github.houbb.nginx4j.api;

import com.github.houbb.nginx4j.config.NginxConfig;

public interface INginxServer {

    /**
     * 初始化
     * @param nginxConfig 配置
     */
    void init(final NginxConfig nginxConfig);

    void start();

    /**
     * 资源销毁
     * @since 0.12.0
     */
    void destroy();

}
