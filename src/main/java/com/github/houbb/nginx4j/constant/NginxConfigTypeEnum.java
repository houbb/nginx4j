package com.github.houbb.nginx4j.constant;

public enum NginxConfigTypeEnum {
    PARAM("param", "基础参数"),
    COMMENT("comment", "注释"),
    IF("if", "判断模块"),
    BLOCK("block", "普通模块");
    ;

    private final String code;
    private final String desc;

    NginxConfigTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
