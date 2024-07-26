package com.github.houbb.nginx4j.support.balance;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.load.balance.support.server.IServer;
import com.github.houbb.load.balance.support.server.impl.Server;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.*;
import com.github.houbb.nginx4j.exception.Nginx4jErrorCode;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

import java.util.ArrayList;
import java.util.List;

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
                config.setNeedProxyPass(true);

                final String url = entry.getValue();
                String upstreamName = getUpstreamName(url, dispatchContext);
                config.setUpstreamName(upstreamName);
                // 获取对应的配置信息
                final NginxUserUpstreamConfig upstreamConfig = getUpstreamConfig(upstreamName, url, dispatchContext);

                List<IServer> serverList = buildServerList(upstreamConfig, url, dispatchContext);
                config.setUpstreamServerList(serverList);


                break;
            }
        }

        return config;
    }

    private List<IServer> buildServerList(final NginxUserUpstreamConfig upstreamConfig,
                                          String url,
                                          NginxRequestDispatchContext dispatchContext) {
        List<IServer> list = new ArrayList<>();

        if(upstreamConfig == null) {
            IServer server = Server.newInstance().url(url);
            list.add(server);
        } else {
            // 如果配置存在
            List<NginxUserUpstreamConfigItem> configItemList = upstreamConfig.getConfigItemList();
            if(CollectionUtil.isEmpty(configItemList)) {
                log.error("UPSTREAM_LIST_IS_EMPTY, upstreamConfig={}", upstreamConfig);
                throw new Nginx4jException(Nginx4jErrorCode.UPSTREAM_LIST_IS_EMPTY);
            }

            for(NginxUserUpstreamConfigItem configItem : configItemList) {
                IServer server = Server.newInstance()
                        .url(configItem.getServer())
                        .weight(Integer.parseInt(configItem.getWeight()));

                list.add(server);
            }
        }

        return list;
    }

    private NginxUserUpstreamConfig getUpstreamConfig(String upstreamName, String url, NginxRequestDispatchContext dispatchContext) {
        if(StringUtil.isEmpty(upstreamName)) {
            return null;
        }

        NginxUserConfig nginxUserConfig =  dispatchContext.getNginxConfig().getNginxUserConfig();

        List<NginxUserUpstreamConfig> upstreamConfigs = nginxUserConfig.getUpstreamConfigs();
        if(CollectionUtil.isEmpty(upstreamConfigs)) {
            log.info("upstreamConfigs is empty, match config is null");
            return null;
        }

        // 遍历
        for(NginxUserUpstreamConfig upstreamConfig : upstreamConfigs) {
            if(upstreamName.equals(upstreamConfig.getUpstream())) {
                return upstreamConfig;
            }
        }

        log.info("upstreamConfigs match config not found!");
        return null;
    }



    /**
     * 获取对应的 upstream 名称
     * @param target 目标url
     * @param dispatchContext 上下文
     * @return 结果
     */
    private String getUpstreamName(String target, NginxRequestDispatchContext dispatchContext) {
        if(StringUtil.isEmpty(target)) {
            throw new Nginx4jException(Nginx4jErrorCode.PROXY_PASS_URL_NEED);
        }

        // 检查目标是否是上游服务器组
        // 目标字符串不能包含协议头，并且必须是上游服务器组集合的一部分
        if(target.startsWith("http://")) {
            String suffix = target.substring("http://".length());
            return suffix;
        } else if(target.startsWith("https://")) {
            String suffix = target.substring("https://".length());
            return suffix;
        }

        throw new Nginx4jException(Nginx4jErrorCode.PROXY_PASS_MUST_START_WTH_HTTP);
    }

}
