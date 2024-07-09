package com.github.houbb.nginx4j.util;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;
import com.github.houbb.nginx4j.config.NginxUserServerLocationConfig;
import com.github.houbb.nginx4j.config.location.INginxLocationMatch;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;

/**
 * @since 0.23.0
 */
public class InnerLocationConfigUtil {

    private static final Log logger = LogFactory.getLog(InnerLocationConfigUtil.class);

    /**
     * 获取当前的服务端地址
     * @param nginxConfig 配置信息
     * @param request 请求
     * @param nginxUserServerConfig 配置
     * @return 结果
     * @since 0.16.0
     */
    public static NginxUserServerLocationConfig getCurrentServerLocation(final NginxConfig nginxConfig,
                                                                         NginxUserServerConfig nginxUserServerConfig,
                                                                         FullHttpRequest request) {
        List<NginxUserServerLocationConfig> configList = nginxUserServerConfig.getLocations();
        if(CollectionUtil.isNotEmpty(configList)) {
            final INginxLocationMatch nginxLocationMatch = nginxConfig.getNginxLocationMatch();

            for(NginxUserServerLocationConfig config : configList) {
                // 是否匹配
                if(nginxLocationMatch.matchConfig(config, request, nginxConfig)) {
                    return config;
                }
            }
        }

        // 默认值
        logger.info("未命中任何 location 配置，使用默认配置");
        return nginxUserServerConfig.getDefaultLocation();
    }

}
