package com.github.houbb.nginx4j.config.parser;

/**
 * 实体类别
 *
 * @since 0.12.0
 */
public enum NginxParserEntryType {
    PARAM("PARAM", "参数"),
    BLOCK("BLOCK", "模块"),
    CONFIG("CONFIG", "整体配置文件"),
    ;

    private final String code;
    private final String desc;

    NginxParserEntryType(String code, String desc) {
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
