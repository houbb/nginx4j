package com.github.houbb.nginx4j.config;

import com.github.houbb.nginx4j.constant.NginxLocationPathTypeEnum;

import java.util.List;

/**
 * 用户配置
 *
 * @since 0.12.0
 */
public class NginxUserServerLocationConfig {

    private List<String> values;

    private String name;

    private String value;

    /**
     * 指令
     */
    private List<NginxUserConfigParam> directives;

    /**
     * 优先级
     */
    private NginxLocationPathTypeEnum typeEnum;

    public NginxLocationPathTypeEnum getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(NginxLocationPathTypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<NginxUserConfigParam> getDirectives() {
        return directives;
    }

    public void setDirectives(List<NginxUserConfigParam> directives) {
        this.directives = directives;
    }
}
