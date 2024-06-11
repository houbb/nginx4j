package com.github.houbb.nginx4j.util;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.config.NginxCommonUserConfig;
import com.github.houbb.nginx4j.constant.NginxConfigTypeEnum;
import com.github.odiszapc.nginxparser.NgxEntry;
import com.github.odiszapc.nginxparser.NgxIfBlock;
import com.github.odiszapc.nginxparser.NgxParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 0.21.0
 */
public class InnerConfigEntryUtil {

    public static NginxCommonConfigEntry buildConfigEntry(NgxParam ngxParam) {
        NginxCommonConfigEntry configEntry = new NginxCommonUserConfig();
        configEntry.setName(ngxParam.getName());
        configEntry.setValue(ngxParam.getValue());
        configEntry.setValues(ngxParam.getValues());
        configEntry.setType(NginxConfigTypeEnum.PARAM);
        return configEntry;
    }

    public static NginxCommonConfigEntry buildConfigEntry(NgxIfBlock ifBlock) {
        NginxCommonConfigEntry configEntry = new NginxCommonUserConfig();
        configEntry.setName(ifBlock.getName());
        configEntry.setValue(ifBlock.getValue());
        configEntry.setValues(ifBlock.getValues());
        configEntry.setType(NginxConfigTypeEnum.IF);

        // 子属性
        List<NginxCommonConfigEntry> children = new ArrayList<>();
        List<NgxEntry> paramList = ifBlock.findAll(NgxParam.class);
        if(CollectionUtil.isNotEmpty(paramList)) {
            for(NgxEntry entry : paramList) {
                if(entry instanceof NgxParam) {
                    NgxParam param = (NgxParam) entry;
                    NginxCommonConfigEntry childEntry = buildConfigEntry(param);
                    children.add(configEntry);
                }
            }
        }

        configEntry.setChildren(children);
        return configEntry;
    }

}
