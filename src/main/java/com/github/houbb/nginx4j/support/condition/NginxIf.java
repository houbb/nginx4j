package com.github.houbb.nginx4j.support.condition;

import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

/**
 * @since 0.21.0
 */
public interface NginxIf {

    /**
     * 是否匹配
     * @param configParam 配置
     * @param dispatchContext 上下文
     * @return 是否
     */
    boolean match(NginxCommonConfigEntry configParam,
                  NginxRequestDispatchContext dispatchContext);

}
