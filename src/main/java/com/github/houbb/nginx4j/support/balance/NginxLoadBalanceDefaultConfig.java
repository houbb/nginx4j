package com.github.houbb.nginx4j.support.balance;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.load.balance.support.server.IServer;
import com.github.houbb.load.balance.support.server.impl.Server;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.*;
import com.github.houbb.nginx4j.constant.NginxHttpEnum;
import com.github.houbb.nginx4j.exception.Nginx4jErrorCode;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认负载均衡配置
 *
 * @since 0.27.0
 * @author 老马啸西风
 */
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
                config.setProxyPassUrl(url);
                String upstreamName = getUpstreamName(url, dispatchContext);
                config.setUpstreamName(upstreamName);
                // 获取对应的配置信息
                final NginxUserUpstreamConfig upstreamConfig = getUpstreamConfig(upstreamName, url, dispatchContext);

                List<IServer> serverList = buildServerList(upstreamConfig, url, dispatchContext);
                config.setUpstreamServerList(serverList);

                // 设置对应的哈希策略 一般第一行为策略？
                NginxCommonConfigEntry upstreamStrategy = getUpstreamStrategyConfig(upstreamConfig);
                if(upstreamStrategy != null) {
                    config.setUpstreamProxyStrategy(upstreamStrategy.getName());
                    config.setUpstreamProxyStrategyValue(upstreamStrategy.getValue());
                }

                break;
            }
        }

        return config;
    }

    //获取 upstream 对应的策略配置
    private NginxCommonConfigEntry getUpstreamStrategyConfig(final NginxUserUpstreamConfig upstreamConfig) {
        List<NginxCommonConfigEntry> upstreamConfigEntryList = upstreamConfig.getConfigEntryList();
        if(CollectionUtil.isEmpty(upstreamConfigEntryList)) {
            return null;
        }

        // 遍历找到第一个不是 server 开头的配置
        for(NginxCommonConfigEntry entry : upstreamConfigEntryList) {
            if(entry.getName().startsWith("server")) {
                continue;
            }

            log.info("getUpstreamStrategyConfig={}", entry);
            return entry;
        }

        return null;
    }

    private List<IServer> buildServerList(final NginxUserUpstreamConfig upstreamConfig,
                                          String url,
                                          NginxRequestDispatchContext dispatchContext) {
        List<IServer> list = new ArrayList<>();

        // 协议
        Integer defaultPort = NginxHttpEnum.getDefaultPortByUrl(url);
        String urlSuffix = getUrlSuffix(url);

        if(upstreamConfig == null) {
            // proxy_pass http://backend.example.com:8080;
            String[] urlSuffixSplits = urlSuffix.split(":");
            if(urlSuffixSplits.length > 1) {
                defaultPort = Integer.parseInt(urlSuffixSplits[1]);
            }
            IServer server = Server.newInstance().host(urlSuffixSplits[0]).port(defaultPort).weight(1);
            list.add(server);
        } else {
            // 如果配置存在
            List<NginxUserUpstreamConfigItem> configItemList = upstreamConfig.getConfigItemList();
            if(CollectionUtil.isEmpty(configItemList)) {
                log.error("UPSTREAM_LIST_IS_EMPTY, upstreamConfig={}", upstreamConfig);
                throw new Nginx4jException(Nginx4jErrorCode.UPSTREAM_LIST_IS_EMPTY);
            }

            //server backend1.example.com:8080 weight=3;
            for(NginxUserUpstreamConfigItem configItem : configItemList) {
                String name = configItem.getName();
                if(!"server".equals(name)) {
                    continue;
                }

                List<String> values = configItem.getValues();
                String serverValue = values.get(0);
                String[] serverValueSplits = serverValue.split(":");
                int port = defaultPort;
                if(serverValueSplits.length > 1) {
                    port = Integer.parseInt(serverValueSplits[1]);
                }

                int weight = 1;
                if(values.size() > 1) {
                    String weightValue = values.get(1);
                    weight = Integer.parseInt(weightValue.split("=")[1]);
                }

                IServer server = Server.newInstance()
                        .host(serverValueSplits[0])
                        .port(port)
                        .weight(weight);

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
        String urlSuffix = getUrlSuffix(target);

        if(StringUtil.isEmpty(urlSuffix)) {
            return urlSuffix;
        }

        // 结果
        return urlSuffix.split(":")[0];
    }

    private String getUrlSuffix(String target) {
        if(StringUtil.isEmpty(target)) {
            throw new Nginx4jException(Nginx4jErrorCode.PROXY_PASS_URL_NEED);
        }

        // 检查目标是否是上游服务器组
        // 目标字符串不能包含协议头，并且必须是上游服务器组集合的一部分
        if(target.startsWith("http://")) {
            return target.substring("http://".length());
        } else if(target.startsWith("https://")) {
            return target.substring("https://".length());
        }

        throw new Nginx4jException(Nginx4jErrorCode.PROXY_PASS_MUST_START_WTH_HTTP);
    }


}
