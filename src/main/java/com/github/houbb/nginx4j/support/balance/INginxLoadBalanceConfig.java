package com.github.houbb.nginx4j.support.balance;

import com.github.houbb.nginx4j.config.NginxUserServerLocationConfig;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

public interface INginxLoadBalanceConfig {

    /**
     * 构建负载均衡的列表
     * @param dispatchContext 配置
     * @return 结果
     */
    NginxLoadBalanceConfig buildBalanceConfig(final NginxRequestDispatchContext dispatchContext);


}
