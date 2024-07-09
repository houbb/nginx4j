package com.github.houbb.nginx4j.support.rewrite;

public enum NginxRewriteFlagEnum {
    /**
     * - `last`: 停止当前所在的 `rewrite` 指令所在的位置，并重新搜索新的 location。相当于 Apache 的 `L` 标志。
     * - `break`: 停止处理当前的 `rewrite` 指令，但继续处理剩下的指令，不会重新搜索 location。
     * - `redirect`: 返回 302 临时重定向。
     * - `permanent`: 返回 301 永久重定向。
     */
    LAST("last", "停止当前所在的 `rewrite` 指令所在的位置，并重新搜索新的 location。相当于 Apache 的 `L` 标志。"),
    BREAK("break", "停止处理当前的 `rewrite` 指令，但继续处理剩下的指令，不会重新搜索 location。"),
    REDIRECT("redirect", "返回 302 临时重定向。"),
    PERMANENT("permanent", "返回 301 永久重定向。"),
    ;

    private final String code;
    private final String desc;

    NginxRewriteFlagEnum(String code, String desc) {
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
