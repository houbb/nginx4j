package com.github.houbb.nginx4j.config;

import java.util.Map;

/**
 * 用户 map
 *
 * @since 0.22.0
 */
public class NginxUserMapConfig extends NginxCommonUserConfig {

    /**
     * 匹配条件 key
     */
    private String placeholderMatchKey;

    /**
     * 目标值 key
     */
    private String placeholderTargetKey;

    /**
     * 默认值
     */
    private String defaultVal;

    /**
     * 映射关系
     */
    private Map<String, String> mapping;

    public String getPlaceholderMatchKey() {
        return placeholderMatchKey;
    }

    public void setPlaceholderMatchKey(String placeholderMatchKey) {
        this.placeholderMatchKey = placeholderMatchKey;
    }

    public String getPlaceholderTargetKey() {
        return placeholderTargetKey;
    }

    public void setPlaceholderTargetKey(String placeholderTargetKey) {
        this.placeholderTargetKey = placeholderTargetKey;
    }

    public String getDefaultVal() {
        return defaultVal;
    }

    public void setDefaultVal(String defaultVal) {
        this.defaultVal = defaultVal;
    }

    public Map<String, String> getMapping() {
        return mapping;
    }

    public void setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
    }

    @Override
    public String toString() {
        return "NginxUserMapConfig{" +
                "placeholderMatchKey='" + placeholderMatchKey + '\'' +
                ", placeholderTargetKey='" + placeholderTargetKey + '\'' +
                ", defaultVal='" + defaultVal + '\'' +
                ", mapping=" + mapping +
                "} " + super.toString();
    }

}
