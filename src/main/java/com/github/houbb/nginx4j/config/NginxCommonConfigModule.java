package com.github.houbb.nginx4j.config;

import java.util.List;

public class NginxCommonConfigModule {

    private String name;

    private String value;

    private List<String> values;

    /**
     * 指令列表
     */
    private List<NginxCommonConfigParam> directives;

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

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public List<NginxCommonConfigParam> getDirectives() {
        return directives;
    }

    public void setDirectives(List<NginxCommonConfigParam> directives) {
        this.directives = directives;
    }
}
