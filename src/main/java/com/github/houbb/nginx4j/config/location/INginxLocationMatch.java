package com.github.houbb.nginx4j.config.location;

import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.config.NginxUserServerLocationConfig;
import io.netty.handler.codec.http.FullHttpRequest;

public interface INginxLocationMatch {

    /**
     * 判断条件是否符合土蚕
     * @param config 配置
     * @param request 请求
     * @param nginxConfig 配置
     * @return 结果
     *
     */
    boolean matchConfig(final NginxUserServerLocationConfig config,
                        final FullHttpRequest request,
                        final NginxConfig nginxConfig);

}
