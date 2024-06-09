package com.github.houbb.nginx4j.config.load.component.impl;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.nginx4j.config.NginxCommonConfigParam;
import com.github.houbb.nginx4j.config.NginxUserServerLocationConfig;
import com.github.houbb.nginx4j.config.load.component.INginxUserServerLocationConfigLoad;
import com.github.houbb.nginx4j.constant.NginxLocationPathTypeEnum;
import com.github.odiszapc.nginxparser.NgxBlock;
import com.github.odiszapc.nginxparser.NgxConfig;
import com.github.odiszapc.nginxparser.NgxEntry;
import com.github.odiszapc.nginxparser.NgxParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @since 0.18.0
 */
public class NginxUserServerLocationConfigLoadFile implements INginxUserServerLocationConfigLoad {

    private final NgxConfig conf;

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

        // 参数
        List<NginxCommonConfigParam> paramList = new ArrayList<>();
        Collection<NgxEntry> ngxEntries = ngxBlock.getEntries();
        if (CollectionUtil.isNotEmpty(ngxEntries)) {
            for (NgxEntry ngxEntry : ngxEntries) {
                // 暂时跳过一些注释之类的处理
                if (!(ngxEntry instanceof NgxParam)) {
                    continue;
                }

                NgxParam ngxParam = (NgxParam) ngxEntry;
                String name = ngxParam.getName();
                List<String> values = ngxParam.getValues();
                String value = ngxParam.getValue();

                NginxCommonConfigParam nginxCommonConfigParam = new NginxCommonConfigParam();
                nginxCommonConfigParam.setName(name);
                nginxCommonConfigParam.setValue(value);
                nginxCommonConfigParam.setValues(values);

                paramList.add(nginxCommonConfigParam);
            }
        }
        locationConfig.setDirectives(paramList);

        return locationConfig;
    }


}
