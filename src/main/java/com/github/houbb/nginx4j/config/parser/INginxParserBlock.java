package com.github.houbb.nginx4j.config.parser;

import java.util.List;

public interface INginxParserBlock extends INginxParserEntry {

    /**
     * 找到所有的参数
     * @param keys 键
     * @return 结果
     */
    List<INginxParserParam> findParams(String...keys);

    /**
     * 找到所有的参数
     * @param keys 键
     * @return 结果
     */
    INginxParserParam findParam(String...keys);

    /**
     * 找到所有匹配的 block
     * @param keys 键
     * @return 结果
     */
    List<INginxParserBlock> findBlocks(String...keys);

    /**
     * 找到第一个匹配的 block
     * @param keys 键
     * @return 结果
     */
    INginxParserBlock findBlock(String... keys);

}
