package com.github.houbb.nginx4j.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户配置
 *
 * @since 0.12.0
 */
public class NginxUserConfig extends NginxCommonUserConfig {

    /**
     * 主配置
     * @since 0.18.0
     */
    private NginxUserMainConfig mainConfig;

    /**
     * events 配置
     * @since 0.18.0
     */
    private NginxUserEventsConfig eventsConfig;

    /**
     * HTTP 配置
     * @since 0.21.0
     */
    private NginxUserHttpConfig httpConfig;

    /**
     * 全部的 server 配置列表
     *
     * @since 0.12.0
     */
    private List<NginxUserServerConfig> serverConfigs;

    /**
     * 服务器端口
     */
    private Set<Integer> serverPortSet;

    /**
     * 当前的服务端口
     */
    private Integer currentServerPort;

    /**
     * 当前的 server 配置 map
     */
    private Map<String, List<NginxUserServerConfig>> currentServerConfigMap;

    /**
     * 默认的服务配置
     * @since 0.14.0
     */
    private NginxUserServerConfig defaultServerConfig;

    /**
     * map 指令的配置信息
     *
     * @since 0.22.0
     */
    private List<NginxUserMapConfig> mapConfigs;

    /**
     * upstream 配置列表
     *
     * @since 0.27.0
     */
    private List<NginxUserUpstreamConfig> upstreamConfigs;

    public List<NginxUserUpstreamConfig> getUpstreamConfigs() {
        return upstreamConfigs;
    }

    public void setUpstreamConfigs(List<NginxUserUpstreamConfig> upstreamConfigs) {
        this.upstreamConfigs = upstreamConfigs;
    }

    public List<NginxUserMapConfig> getMapConfigs() {
        return mapConfigs;
    }

    public void setMapConfigs(List<NginxUserMapConfig> mapConfigs) {
        this.mapConfigs = mapConfigs;
    }

    public NginxUserHttpConfig getHttpConfig() {
        return httpConfig;
    }

    public void setHttpConfig(NginxUserHttpConfig httpConfig) {
        this.httpConfig = httpConfig;
    }

    public NginxUserMainConfig getMainConfig() {
        return mainConfig;
    }

    public void setMainConfig(NginxUserMainConfig mainConfig) {
        this.mainConfig = mainConfig;
    }

    public NginxUserEventsConfig getEventsConfig() {
        return eventsConfig;
    }

    public void setEventsConfig(NginxUserEventsConfig eventsConfig) {
        this.eventsConfig = eventsConfig;
    }

    public Map<String, List<NginxUserServerConfig>> getCurrentServerConfigMap() {
        return currentServerConfigMap;
    }

    public void setCurrentServerConfigMap(Map<String, List<NginxUserServerConfig>> currentServerConfigMap) {
        this.currentServerConfigMap = currentServerConfigMap;
    }

    public Integer getCurrentServerPort() {
        return currentServerPort;
    }

    public void setCurrentServerPort(Integer currentServerPort) {
        this.currentServerPort = currentServerPort;
    }

    public List<NginxUserServerConfig> getServerConfigs() {
        return serverConfigs;
    }

    public void setServerConfigs(List<NginxUserServerConfig> serverConfigs) {
        this.serverConfigs = serverConfigs;
    }

    public NginxUserServerConfig getDefaultServerConfig() {
        return defaultServerConfig;
    }

    public void setDefaultServerConfig(NginxUserServerConfig defaultServerConfig) {
        this.defaultServerConfig = defaultServerConfig;
    }


    public Set<Integer> getServerPortSet() {
        return serverPortSet;
    }

    public void setServerPortSet(Set<Integer> serverPortSet) {
        this.serverPortSet = serverPortSet;
    }

}
