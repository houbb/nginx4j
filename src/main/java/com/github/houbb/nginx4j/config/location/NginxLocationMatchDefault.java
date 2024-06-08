package com.github.houbb.nginx4j.config.location;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.config.NginxUserServerLocationConfig;
import com.github.houbb.nginx4j.constant.NginxLocationPathTypeEnum;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;

public class NginxLocationMatchDefault implements INginxLocationMatch {

    private static final Log logger = LogFactory.getLog(NginxLocationMatchDefault.class);

    @Override
    public boolean matchConfig(NginxUserServerLocationConfig config, FullHttpRequest request, NginxConfig nginxConfig) {
        final String requestUri = request.uri();
        final NginxLocationPathTypeEnum pathTypeEnum = config.getTypeEnum();

        List<String> values = config.getValues();
        if(values.size() == 2) {
            // 处理
            String configType = values.get(0);
            String configUrl = values.get(1);

            // 精准匹配
            if(NginxLocationPathTypeEnum.EXACT.equals(pathTypeEnum) && configUrl.equals(requestUri)) {
                logger.info("命中 EXACT 配置 requestUri={}, pathTypeEnum={}, configUrl={}", requestUri, pathTypeEnum, configUrl);
                return true;
            }

            // 前缀匹配
            if(NginxLocationPathTypeEnum.PREFIX.equals(pathTypeEnum) && requestUri.startsWith(configUrl)) {
                logger.info("命中 PREFIX 配置 requestUri={}, pathTypeEnum={}, configUrl={}", requestUri, pathTypeEnum, configUrl);
                return true;
            }

            // REGEX
            if(NginxLocationPathTypeEnum.REGEX.equals(pathTypeEnum) && requestUri.matches(configUrl)) {
                logger.info("命中 REGEX 配置 requestUri={}, pathTypeEnum={}, configUrl={}", requestUri, pathTypeEnum, configUrl);
            }
        } else if(values.size() == 1) {
            String value = values.get(0);

            // 这里只有前缀匹配
            if(requestUri.startsWith(value)) {
                logger.info("命中普通前缀配置 requestUri={}, value={}", requestUri, value);

                return true;
            }
        }

        return false;
    }

}
