package com.github.houbb.nginx4j.config.load.component.impl;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.nginx4j.config.NginxUserUpstreamConfig;
import com.github.houbb.nginx4j.config.NginxUserUpstreamConfigItem;
import com.github.houbb.nginx4j.config.load.component.INginxUserUpstreamConfigLoad;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.util.InnerConfigEntryUtil;
import com.github.odiszapc.nginxparser.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @since 0.27.0
 */
public class NginxUserUpstreamConfigLoadFile implements INginxUserUpstreamConfigLoad {

    private final NgxConfig conf;

    private final NgxBlock ngxBlock;

    public NginxUserUpstreamConfigLoadFile(NgxConfig conf, NgxBlock ngxBlock) {
        this.conf = conf;
        this.ngxBlock = ngxBlock;
    }

    @Override
    public NginxUserUpstreamConfig load() {
        NginxUserUpstreamConfig config = new NginxUserUpstreamConfig();
        config.setName(ngxBlock.getName());
        config.setValue(ngxBlock.getValue());
        config.setValues(ngxBlock.getValues());

        // 配置
        String upstreamName = ngxBlock.getValue();
        if(StringUtil.isEmpty(upstreamName)) {
            throw new Nginx4jException("upstream 名称不可为空");
        }

        config.setUpstream(upstreamName);
        Collection<NgxEntry> entryList = ngxBlock.getEntries();
        List<NginxUserUpstreamConfigItem> configItemList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(entryList)) {
            for(NgxEntry ngxEntry : entryList) {
                if(ngxEntry instanceof NgxParam) {
                    NgxParam ngxParam = (NgxParam) ngxEntry;

                    NginxUserUpstreamConfigItem configItem = new NginxUserUpstreamConfigItem();
                    configItem.setName(ngxParam.getName());
                    configItem.setValue(ngxParam.getValue());
                    configItem.setValues(ngxParam.getValues());

                    configItemList.add(configItem);
                }
            }
        }

        config.setConfigItemList(configItemList);
        return config;
    }


}
