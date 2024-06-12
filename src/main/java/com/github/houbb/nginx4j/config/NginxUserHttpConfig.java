package com.github.houbb.nginx4j.config;

import java.util.List;

/**
 * 用户 HTTP 配置
 *
 * @since 0.21.0
 */
public class NginxUserHttpConfig extends NginxCommonUserConfig {

    private List<NginxUserServerConfig> serverConfigList;

    public List<NginxUserServerConfig> getServerConfigList() {
        return serverConfigList;
    }

    public void setServerConfigList(List<NginxUserServerConfig> serverConfigList) {
        this.serverConfigList = serverConfigList;
    }
}
