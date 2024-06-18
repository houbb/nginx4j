package com.github.houbb.nginx4j.config.load.component.impl;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.nginx4j.config.NginxUserMapConfig;
import com.github.houbb.nginx4j.config.load.component.INginxUserMapConfigLoad;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.odiszapc.nginxparser.NgxBlock;
import com.github.odiszapc.nginxparser.NgxConfig;
import com.github.odiszapc.nginxparser.NgxEntry;
import com.github.odiszapc.nginxparser.NgxParam;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @since 0.22.0
 */
public class NginxUserMapConfigLoadFile implements INginxUserMapConfigLoad {

    private final NgxConfig conf;

    private final NgxBlock mapBlock;

    public NginxUserMapConfigLoadFile(NgxConfig conf, NgxBlock mapBlock) {
        this.conf = conf;
        this.mapBlock = mapBlock;
    }

    @Override
    public NginxUserMapConfig load() {
        Map<String, String> mapping = new HashMap<>();

        NginxUserMapConfig config = new NginxUserMapConfig();

        List<String> values = mapBlock.getValues();
        if(values.size() != 2) {
            throw new Nginx4jException("map 指令的 values 必须为 2，形如 map $key1 $key2");
        }
        config.setPlaceholderMatchKey(values.get(0));
        config.setPlaceholderTargetKey(values.get(1));

        Collection<NgxEntry> entryList = mapBlock.getEntries();
        if(CollectionUtil.isEmpty(entryList)) {
            throw new Nginx4jException("map 指令的映射关系不可为空，可以配置 default xxx");
        }

        for(NgxEntry entry : entryList) {
            if(entry instanceof NgxParam) {
                NgxParam ngxParam = (NgxParam) entry;
                String name = ngxParam.getName();
                String value = ngxParam.getValue();

                // 对比
                if("default".equals(name)) {
                    config.setDefaultVal(value);
                } else {
                    mapping.put(name, value);
                }
            }
        }

        config.setMapping(mapping);
        return config;
    }

}
