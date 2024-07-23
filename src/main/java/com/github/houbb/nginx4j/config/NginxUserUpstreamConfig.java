package com.github.houbb.nginx4j.config;

import java.util.List;

/**
 * 用户配置
 *
 * @since 0.27.0
 */
public class NginxUserUpstreamConfig extends NginxCommonUserConfig {

    // upstream 定义一个上游服务器组。
    private String upstream;

    private List<NginxUserUpstreamConfigItem> configItemList;

    public String getUpstream() {
        return upstream;
    }

    public void setUpstream(String upstream) {
        this.upstream = upstream;
    }

    public List<NginxUserUpstreamConfigItem> getConfigItemList() {
        return configItemList;
    }

    public void setConfigItemList(List<NginxUserUpstreamConfigItem> configItemList) {
        this.configItemList = configItemList;
    }
}
