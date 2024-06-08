package com.github.houbb.nginx4j.constant;

import com.github.houbb.nginx4j.config.NginxUserServerLocationConfig;

import java.util.List;

/**
 * 路径的匹配方式
 *
 * 1. 精确匹配 (`=`)
 * 2. 前缀匹配 (`^~`)
 * 3. 正则匹配 (`~` 或 `~*`)
 * 4. 普通前缀匹配
 * 5. 默认匹配 /
 *
 */
public enum NginxLocationPathTypeEnum {

    EXACT("EXACT", "精确匹配 (`=`)", 1000),
    PREFIX("PREFIX", "前缀匹配 (^~)", 2000),
    REGEX("REGEX", "正则匹配 (~ 或 ~*)", 3000),
    COMMON_PREFIX("COMMON_PREFIX /prefix", "普通前缀匹配", 4000),
    DEFAULT("DEFAULT", "默认匹配 /", 5000),
    ;

    private final String code;
    private final String desc;

    private final int order;

    NginxLocationPathTypeEnum(String code, String desc, int order) {
        this.code = code;
        this.desc = desc;
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static NginxLocationPathTypeEnum getTypeEnum(final NginxUserServerLocationConfig locationConfig) {
        List<String> values = locationConfig.getValues();
        String firstParam = values.get(0);

        if(values.size() <= 1) {
            if(firstParam.equals("/")) {
                return DEFAULT;
            }
            return COMMON_PREFIX;
        }

        if("=".equals(firstParam)) {
            return EXACT;
        }
        if(firstParam.startsWith("^")) {
            return PREFIX;
        }
        if(firstParam.startsWith("~")) {
            return REGEX;
        }

        return NginxLocationPathTypeEnum.DEFAULT;
    }

}
