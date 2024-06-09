package com.github.houbb.nginx4j.config;

import java.util.List;

/**
 * 用户配置
 *
 * @since 0.12.0
 */
public class NginxCommonUserConfig {

    private List<String> values;

    private String name;

    private String value;

    /**
     * 指令
     */
    private List<NginxCommonConfigParam> directives;

    /**
     * 模块
     */
    private List<NginxCommonConfigModule> modules;

    public List<NginxCommonConfigParam> getDirectives() {
        return directives;
    }

    public void setDirectives(List<NginxCommonConfigParam> directives) {
        this.directives = directives;
    }

    public List<NginxCommonConfigModule> getModules() {
        return modules;
    }

    public void setModules(List<NginxCommonConfigModule> modules) {
        this.modules = modules;
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
}
