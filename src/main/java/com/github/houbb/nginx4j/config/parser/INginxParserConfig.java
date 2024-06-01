package com.github.houbb.nginx4j.config.parser;

/**
 * 配置接口
 *
 * @since 0.12.0
 */
public interface INginxParserConfig extends INginxParserBlock {

    /**
     * 加载配置信息
     */
    void load();

}
