package com.github.houbb.nginx4j.config;

import com.github.houbb.nginx4j.constant.NginxConst;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户配置
 *
 * @since 0.12.0
 */
public class NginxUserConfig {

    // 全局配置
    private String httpPid;


    /**
     * 全部的 server 配置列表
     *
     * @since 0.12.0
     */
    private List<NginxUserServerConfig> serverConfigList;

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
    private NginxUserServerConfig defaultUserServerConfig;

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

    public List<NginxUserServerConfig> getServerConfigList() {
        return serverConfigList;
    }

    public void setServerConfigList(List<NginxUserServerConfig> serverConfigList) {
        this.serverConfigList = serverConfigList;
    }

    public NginxUserServerConfig getDefaultUserServerConfig() {
        return defaultUserServerConfig;
    }

    public void setDefaultUserServerConfig(NginxUserServerConfig defaultUserServerConfig) {
        this.defaultUserServerConfig = defaultUserServerConfig;
    }


    public Set<Integer> getServerPortSet() {
        return serverPortSet;
    }

    public void setServerPortSet(Set<Integer> serverPortSet) {
        this.serverPortSet = serverPortSet;
    }

    public String getHttpPid() {
        return httpPid;
    }

    public void setHttpPid(String httpPid) {
        this.httpPid = httpPid;
    }
}
