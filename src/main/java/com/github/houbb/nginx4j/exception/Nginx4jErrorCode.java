package com.github.houbb.nginx4j.exception;

import com.github.houbb.heaven.response.respcode.RespCode;

/**
 * 错误码
 *
 * @since 0.27.0
 */
public enum Nginx4jErrorCode implements RespCode {
    UPSTREAM_NOT_FOUND("Ngx001", "upstream 不存在");

    private final String code;
    private final String msg;

    Nginx4jErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getMsg() {
        return null;
    }

}
