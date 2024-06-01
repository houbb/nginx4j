package com.github.houbb.nginx4j.config.parser;

import java.util.List;

public interface INginxParserParam extends INginxParserEntry {

    /**
     * 获取值
     * @return 获取值
     */
    String getValue();

    /**
     * 获取值列表
     * @return 值列表
     */
    List<String> getValues();

}
