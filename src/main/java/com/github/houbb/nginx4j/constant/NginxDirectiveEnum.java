package com.github.houbb.nginx4j.constant;

/**
 * nginx 指令枚举
 *
 * @since 0.25.0
 */
public enum NginxDirectiveEnum {
    TRY_FILES("try_files", "");

    private final String code;
    private final String desc;

    NginxDirectiveEnum(String code, String desc) {
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
