package com.github.houbb.nginx4j.config.load.component.impl;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.config.NginxUserHttpConfig;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;
import com.github.houbb.nginx4j.config.load.component.INginxUserHttpConfigLoad;
import com.github.houbb.nginx4j.config.load.component.INginxUserServerConfigLoad;
import com.github.houbb.nginx4j.util.InnerConfigEntryUtil;
import com.github.odiszapc.nginxparser.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @since 0.21.0
 */
public class NginxUserHttpConfigLoadFile implements INginxUserHttpConfigLoad {

    private final NgxConfig conf;

    private final NgxBlock httpBlock;

    public NginxUserHttpConfigLoadFile(NgxConfig conf, NgxBlock httpBlock) {
        this.conf = conf;
        this.httpBlock = httpBlock;
    }


    @Override
    public NginxUserHttpConfig load() {
        NginxUserHttpConfig userHttpConfig = new NginxUserHttpConfig();

        // 基础参数
        List<NginxCommonConfigEntry> configEntryList = new ArrayList<>();
        Collection<NgxEntry> entryList = httpBlock.getEntries();
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
        userHttpConfig.setConfigEntryList(configEntryList);

        // server 信息?
        List<NgxEntry> servers = httpBlock.findAll(NgxConfig.BLOCK, "server"); // 示例3
        List<NginxUserServerConfig> serverConfigList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(servers)) {
            for (NgxEntry entry : servers) {
                // 每一个 server 的处理
                NgxBlock ngxBlock = (NgxBlock) entry;

                final INginxUserServerConfigLoad serverConfigLoad = new NginxUserServerConfigLoadFile(conf, ngxBlock);
                NginxUserServerConfig serverConfig = serverConfigLoad.load();
                serverConfigList.add(serverConfig);
            }
        }
        userHttpConfig.setServerConfigList(serverConfigList);
        return userHttpConfig;
    }

}
