package com.github.houbb.nginx4j.config;

import java.util.List;

/**
 * 用户配置
 *
 * @since 0.12.0
 */
public class NginxCommonUserConfig extends NginxCommonConfigEntry {

    /**
     * 配置列表
     * @since 0.21.0
     */
    private List<NginxCommonConfigEntry> configEntryList;

    public List<NginxCommonConfigEntry> getConfigEntryList() {
        return configEntryList;
    }

    public void setConfigEntryList(List<NginxCommonConfigEntry> configEntryList) {
        this.configEntryList = configEntryList;
    }

    @Override
    public String toString() {
        return "NginxCommonUserConfig{" +
                "configEntryList=" + configEntryList +
                '}';
    }

}
