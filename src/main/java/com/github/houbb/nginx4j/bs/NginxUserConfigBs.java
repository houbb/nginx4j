package com.github.houbb.nginx4j.bs;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.*;
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

    /**
     * 配置列表
     * @since 0.21.0
     */
    private List<NginxCommonConfigEntry> configEntryList = new ArrayList<>();



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

    /**
     * HTTP 配置
     * @since 0.21.0
     */
    private NginxUserHttpConfig httpConfig = new NginxUserHttpConfig();

    /**
     * map 指令的配置信息
     *
     * @since 0.22.0
     */
    private List<NginxUserMapConfig> mapConfigs = new ArrayList<>();

    public NginxUserConfigBs mapConfigs(List<NginxUserMapConfig> mapConfigs) {
        this.mapConfigs = mapConfigs;
        return this;
    }

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

    public NginxUserConfigBs configEntryList(List<NginxCommonConfigEntry> configEntryList) {
        this.configEntryList = configEntryList;
        return this;
    }

    public NginxUserConfigBs httpConfig(NginxUserHttpConfig httpConfig) {
        this.httpConfig = httpConfig;
        return this;
    }

    public NginxUserConfig build() {
        NginxUserConfig config = new NginxUserConfig();
        config.setServerPortSet(serverPortSet);
        config.setServerConfigs(serverConfigList);
        config.setDefaultServerConfig(defaultUserServerConfig);
        config.setMainConfig(mainConfig);
        config.setEventsConfig(eventsConfig);
        config.setConfigEntryList(configEntryList);
        config.setHttpConfig(httpConfig);
        config.setMapConfigs(mapConfigs);
        return config;
    }

}
