package com.github.houbb.nginx4j.config.load.component.impl;

import com.alibaba.fastjson.JSON;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.JsonUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.config.NginxUserServerLocationConfig;
import com.github.houbb.nginx4j.config.load.component.INginxUserServerLocationConfigLoad;
import com.github.houbb.nginx4j.constant.NginxLocationPathTypeEnum;
import com.github.houbb.nginx4j.support.request.dispatch.http.NginxRequestDispatchReturn;
import com.github.houbb.nginx4j.util.InnerConfigEntryUtil;
import com.github.odiszapc.nginxparser.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @since 0.18.0
 */
public class NginxUserServerLocationConfigLoadFile implements INginxUserServerLocationConfigLoad {

    private static final Log logger = LogFactory.getLog(NginxUserServerLocationConfigLoadFile.class);

    /**
     * 全局配置
     */
    private final NgxConfig conf;

    /**
     * 当前模块
     */
    private final NgxBlock ngxBlock;

    public NginxUserServerLocationConfigLoadFile(NgxConfig conf, NgxBlock ngxBlockLocation) {
        this.conf = conf;
        this.ngxBlock = ngxBlockLocation;
    }

    @Override
    public NginxUserServerLocationConfig load() {
        // 参数
        NginxUserServerLocationConfig locationConfig = new NginxUserServerLocationConfig();
        locationConfig.setName(ngxBlock.getName());
        locationConfig.setValue(ngxBlock.getValue());
        locationConfig.setValues(ngxBlock.getValues());

        NginxLocationPathTypeEnum typeEnum = NginxLocationPathTypeEnum.getTypeEnum(locationConfig);
        locationConfig.setTypeEnum(typeEnum);

        //4. 基础的指令+if 模块
        List<NginxCommonConfigEntry> configEntryList = new ArrayList<>();
        Collection<NgxEntry> entryList = ngxBlock.getEntries();
        if(CollectionUtil.isNotEmpty(entryList)) {
            for(NgxEntry ngxEntry : entryList) {
                if(ngxEntry instanceof NgxParam) {
                    configEntryList.add(InnerConfigEntryUtil.buildConfigEntry((NgxParam) ngxEntry));
                }
                if(ngxEntry instanceof NgxIfBlock) {
                    configEntryList.add(InnerConfigEntryUtil.buildConfigEntry((NgxIfBlock) ngxEntry));
                }
            }
        }
        locationConfig.setConfigEntryList(configEntryList);

        logger.info("[ConfigLoad] locationConfig={}", JSON.toJSONString(locationConfig));
        return locationConfig;
    }


}
