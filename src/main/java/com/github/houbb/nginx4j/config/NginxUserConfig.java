package com.github.houbb.nginx4j.config;

import com.github.houbb.nginx4j.constant.NginxConst;

import java.util.Map;
import java.util.Set;

/**
 * 用户配置
 *
 * @since 0.12.0
 */
public class NginxUserConfig {

    /**
     * 全部的 server 配置列表
     *
     * @since 0.12.0
     */
    private Map<String, NginxUserServerConfig> serverConfigMap;

    /**
     * 服务器端口
     */
    private Set<Integer> serverPortSet;

    public Set<Integer> getServerPortSet() {
        return serverPortSet;
    }

    public void setServerPortSet(Set<Integer> serverPortSet) {
        this.serverPortSet = serverPortSet;
    }

    public NginxUserServerConfig getNginxUserServerConfig(String hostName) {
        NginxUserServerConfig defineConfig = serverConfigMap.get(hostName);

        // 返回默认的
        if(defineConfig != null) {
            return defineConfig;
        }

        // 默认的配置
        return serverConfigMap.get(NginxConst.DEFAULT_SERVER);
    }

    public Map<String, NginxUserServerConfig> getServerConfigMap() {
        return serverConfigMap;
    }

    public void setServerConfigMap(Map<String, NginxUserServerConfig> serverConfigMap) {
        this.serverConfigMap = serverConfigMap;
    }
}
