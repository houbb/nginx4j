package com.github.houbb.nginx4j.bs;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxUserConfig;
import com.github.houbb.nginx4j.config.NginxUserEventsConfig;
import com.github.houbb.nginx4j.config.NginxUserMainConfig;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;
import com.github.houbb.nginx4j.constant.NginxUserConfigDefaultConst;
import com.github.houbb.nginx4j.support.handler.NginxNettyServerHandler;

import java.util.*;

/**
 * @since 0.12.0
 */
public class NginxUserConfigBs {

    private static final Log logger = LogFactory.getLog(NginxNettyServerHandler.class);

    /**
     * 默认用户的服务端配置
     * @since 0.14.0
     */
    private NginxUserServerConfig defaultUserServerConfig = NginxUserServerConfigBs.newInstance().build();

    public static NginxUserConfigBs newInstance() {
        return new NginxUserConfigBs();
    }

    /**
     * @since 0.18.0
     */
    private NginxUserMainConfig mainConfig = new NginxUserMainConfig();

    /**
     * @since 0.18.0
     */
    private NginxUserEventsConfig eventsConfig = new NginxUserEventsConfig();

    /**
     * 全部的 server 配置列表
     *
     * @since 0.12.0
     */
    private final List<NginxUserServerConfig> serverConfigList = new ArrayList<>();

    private final Set<Integer> serverPortSet = new HashSet<>();

    public NginxUserConfigBs mainConfig(NginxUserMainConfig mainConfig) {
        this.mainConfig = mainConfig;
        return this;
    }

    public NginxUserConfigBs eventsConfig(NginxUserEventsConfig eventsConfig) {
        this.eventsConfig = eventsConfig;
        return this;
    }

    public NginxUserConfigBs defaultUserServerConfig(NginxUserServerConfig defaultUserServerConfig) {
        this.defaultUserServerConfig = defaultUserServerConfig;
        return this;
    }

    public NginxUserConfigBs addServerConfig(NginxUserServerConfig serverConfig) {
        serverConfigList.add(serverConfig);

        serverPortSet.add(serverConfig.getListen());

        return this;
    }

    public NginxUserConfig build() {
        NginxUserConfig config = new NginxUserConfig();
        config.setServerPortSet(serverPortSet);
        config.setServerConfigs(serverConfigList);
        config.setDefaultServerConfig(defaultUserServerConfig);
        config.setMainConfig(mainConfig);
        config.setEventsConfig(eventsConfig);
        return config;
    }

}
