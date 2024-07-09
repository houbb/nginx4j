package com.github.houbb.nginx4j.config;

import com.github.houbb.nginx4j.constant.NginxConfigTypeEnum;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class NginxCommonConfigEntry implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;

    /**
     * 值列表
     */
    private List<String> values;

    /**
     * 类型
     * @since 0.21.0
     */
    private NginxConfigTypeEnum type;

    /**
     * 子列表
     * @since 0.21.0
     */
    private List<NginxCommonConfigEntry> children;

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

    public List<NginxCommonConfigEntry> getChildren() {
        return children;
    }

    public void setChildren(List<NginxCommonConfigEntry> children) {
        this.children = children;
    }

    public NginxConfigTypeEnum getType() {
        return type;
    }

    public void setType(NginxConfigTypeEnum type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NginxCommonConfigEntry entry = (NginxCommonConfigEntry) o;
        return Objects.equals(name, entry.name) && Objects.equals(value, entry.value) && Objects.equals(values, entry.values) && type == entry.type && Objects.equals(children, entry.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, values, type, children);
    }

    @Override
    public String toString() {
        return "NginxCommonConfigEntry{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", values=" + values +
                ", type=" + type +
                ", children=" + children +
                '}';
    }

}
