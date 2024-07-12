package com.github.houbb.nginx4j.support.returns;

import java.util.List;

/**
 * return 的结果
 *
 * @since 0.24.0
 */
public class NginxReturnResult {

    private int code;

    private String value;

    private List<String> valueList;

    public List<String> getValueList() {
        return valueList;
    }

    public void setValueList(List<String> valueList) {
        this.valueList = valueList;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "NginxReturnResult{" +
                "code=" + code +
                ", value='" + value + '\'' +
                ", valueList=" + valueList +
                '}';
    }
}
