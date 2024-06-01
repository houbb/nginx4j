package com.github.houbb.nginx4j.config.parser;

import java.util.List;

/**
 * @since 0.12.0
 */
public interface INginxParserEntry {

    /**
     * 获取名称
     * @return 名称
     */
    String getName();

    List<String> getValues();

    String getValue();

    /**
     * 获取类别
     * @return 类别
     */
    String getType();


}
