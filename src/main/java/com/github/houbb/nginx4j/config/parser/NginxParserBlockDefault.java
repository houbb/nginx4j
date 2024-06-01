package com.github.houbb.nginx4j.config.parser;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.odiszapc.nginxparser.NgxBlock;
import com.github.odiszapc.nginxparser.NgxConfig;
import com.github.odiszapc.nginxparser.NgxEntry;
import com.github.odiszapc.nginxparser.NgxParam;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NginxParserBlockDefault extends AbstractNginxParserEntry implements INginxParserBlock {

    protected NgxConfig innerNgxConfig;

    public void setInnerNgxConfig(NgxConfig innerNgxConfig) {
        this.innerNgxConfig = innerNgxConfig;
    }

    @Override
    public List<INginxParserParam> findParams(String... keys) {
        List<NgxEntry> params = innerNgxConfig.findAll(NgxConfig.PARAM, keys);
        if(CollectionUtil.isEmpty(params)) {
            return Collections.emptyList();
        }

        return params.stream().map(new Function<NgxEntry, INginxParserParam>() {
            @Override
            public INginxParserParam apply(NgxEntry ngxEntry) {
                NgxParam ngxParam = (NgxParam) ngxEntry;
                NginxParserParamDefault paramDefault = new NginxParserParamDefault();
                paramDefault.setName(ngxParam.getName());
                paramDefault.setValues(ngxParam.getValues());
                return paramDefault;
            }
        }).collect(Collectors.toList());
    }

    @Override
    public INginxParserParam findParam(String... keys) {
        List<INginxParserParam> params = this.findParams(keys);
        if(CollectionUtil.isEmpty(params)) {
            return null;
        }

        return params.get(0);
    }

    @Override
    public List<INginxParserBlock> findBlocks(String... keys) {
        List<NgxEntry> blocks = innerNgxConfig.findAll(NgxConfig.BLOCK, keys); // 示例3
        if(CollectionUtil.isEmpty(blocks)) {
            return Collections.emptyList();
        }

        return blocks.stream().map(new Function<NgxEntry, INginxParserBlock>() {
            @Override
            public INginxParserBlock apply(NgxEntry ngxEntry) {
                NgxBlock ngxBlock = (NgxBlock) ngxEntry;
                String rawName = ngxBlock.getName();
                List<String> rawValues = ngxBlock.getValues();

                NginxParserBlockDefault blockDefault = new NginxParserBlockDefault();
                blockDefault.setName(rawName);
                blockDefault.setValues(rawValues);
                blockDefault.setInnerNgxConfig(innerNgxConfig);
                return blockDefault;
            }
        }).collect(Collectors.toList());
    }

    @Override
    public INginxParserBlock findBlock(String... keys) {
        List<INginxParserBlock> blocks = this.findBlocks(keys);
        if(CollectionUtil.isEmpty(blocks)) {
            return null;
        }

        return blocks.get(0);
    }

    @Override
    public String getType() {
        return NginxParserEntryType.BLOCK.getCode();
    }
}
