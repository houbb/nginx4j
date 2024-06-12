package com.github.houbb.nginx4j.support.condition;

import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.constant.NginxConst;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.placeholder.INginxPlaceholderManager;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

import java.util.List;
import java.util.Map;

/**
 * @since 0.21.0
 */
public class NginxIfDefault implements NginxIf {

    /**
     * @param configParam 配置
     * @param dispatchContext 上下文
     * @return 结果
     */
    @Override
    public boolean match(NginxCommonConfigEntry configParam, NginxRequestDispatchContext dispatchContext) {
        final NginxIfOperatorManager nginxIfOperatorManager = new NginxIfOperatorManager();

        return nginxIfOperatorManager.match(configParam, dispatchContext);
    }

}
