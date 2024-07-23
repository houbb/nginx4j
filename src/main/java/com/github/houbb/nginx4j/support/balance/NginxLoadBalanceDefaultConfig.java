package com.github.houbb.nginx4j.support.balance;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.config.NginxUserServerLocationConfig;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

import java.util.List;
import java.util.Set;

public class NginxLoadBalanceDefaultConfig implements INginxLoadBalanceConfig {

    private static final Log log = LogFactory.getLog(NginxLoadBalanceDefaultConfig.class);

    @Override
    public NginxLoadBalanceConfig buildBalanceConfig(NginxRequestDispatchContext dispatchContext) {
        NginxLoadBalanceConfig config = new NginxLoadBalanceConfig();
        config.setNeedProxyPass(false);

        NginxUserServerLocationConfig nginxUserServerLocationConfig = dispatchContext.getCurrentUserServerLocationConfig();
        List<NginxCommonConfigEntry> configEntryList = nginxUserServerLocationConfig.getConfigEntryList();
        if(CollectionUtil.isEmpty(configEntryList)) {
            return config;
        }

        //1. serverList proxy_pass http://my_backend;
        for(NginxCommonConfigEntry entry : configEntryList) {
            if("proxy_pass".equals(entry.getName())) {
                // 处理
                List<String> values = entry.getValues();

                //TODO: 实现
                break;
            }
        }

        return config;
    }

    // 方法：判断 proxy_pass 的目标是 upstream 组还是具体的 URL
    private boolean isCheckProxyPassTarget(List<String> values, NginxRequestDispatchContext dispatchContext) {
        // 移除 proxy_pass 前的空格，并确保字符串是以 "proxy_pass" 开头
        if(values.size() <= 1) {
            throw new Nginx4jException("proxy_pass 必须指定后续的内容");
        }

        // 提取 proxy_pass 的目标
        String target = values.get(0);

        // 检查目标是否是上游服务器组
        // 目标字符串不能包含协议头，并且必须是上游服务器组集合的一部分
        if(target.startsWith("http://")) {
            String suffix = target.substring("http://".length());

        } else if(target.startsWith("https://")) {
            String suffix = target.substring("https://".length());

        }

        throw new Nginx4jException("proxy_pass 非法的 http 协议 " + target);
    }

}
