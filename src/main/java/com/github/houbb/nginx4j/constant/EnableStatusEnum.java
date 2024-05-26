package com.github.houbb.nginx4j.constant;

public enum EnableStatusEnum {

    ON("on", "启用"),
    OFF("off", "关闭"),
    ;

    private final String code;
    private final String desc;

    EnableStatusEnum(String code, String desc) {
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
