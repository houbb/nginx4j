package com.github.houbb.nginx4j.config.load;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.nginx4j.bs.NginxUserConfigBs;
import com.github.houbb.nginx4j.config.NginxUserConfig;
import com.github.houbb.nginx4j.config.NginxUserEventsConfig;
import com.github.houbb.nginx4j.config.NginxUserMainConfig;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;
import com.github.houbb.nginx4j.config.load.component.INginxUserEventsConfigLoad;
import com.github.houbb.nginx4j.config.load.component.INginxUserMainConfigLoad;
import com.github.houbb.nginx4j.config.load.component.INginxUserServerConfigLoad;
import com.github.houbb.nginx4j.config.load.component.impl.NginxUserEventsConfigLoadFile;
import com.github.houbb.nginx4j.config.load.component.impl.NginxUserMainConfigLoadFile;
import com.github.houbb.nginx4j.config.load.component.impl.NginxUserServerConfigLoadFile;
import com.github.odiszapc.nginxparser.NgxBlock;
import com.github.odiszapc.nginxparser.NgxConfig;
import com.github.odiszapc.nginxparser.NgxEntry;

import java.io.IOException;
import java.util.List;

/**
 * 细化为不同的组件读取
 *
 * @since 0.18.0
 * @author 老马啸西风
 */
public  class NginxUserConfigLoaderConfigComponentFile extends AbstractNginxUserConfigLoader {

    private final String filePath;

    public NginxUserConfigLoaderConfigComponentFile(String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected NginxUserConfig doLoad() {
        NgxConfig conf = null;
        try {
            conf = NgxConfig.read(filePath);

            NginxUserConfigBs configBs = NginxUserConfigBs.newInstance();

            //1. basic
            INginxUserMainConfigLoad mainConfigLoad = new NginxUserMainConfigLoadFile(conf);
            NginxUserMainConfig mainConfig = mainConfigLoad.load();
            configBs.mainConfig(mainConfig);

            //2. events
            INginxUserEventsConfigLoad eventsConfigLoad = new NginxUserEventsConfigLoadFile(conf);
            NginxUserEventsConfig eventsConfig = new NginxUserEventsConfig();
            configBs.eventsConfig(eventsConfig);

            //3. server 信息
            // 首先获取 block
            List<NgxEntry> servers = conf.findAll(NgxConfig.BLOCK, "http", "server"); // 示例3
            if(CollectionUtil.isNotEmpty(servers)) {
                for (NgxEntry entry : servers) {
                    // 每一个 server 的处理
                    NgxBlock ngxBlock = (NgxBlock) entry;

                    final INginxUserServerConfigLoad serverConfigLoad = new NginxUserServerConfigLoadFile(conf, ngxBlock);
                    NginxUserServerConfig serverConfig = serverConfigLoad.load();
                    configBs.addServerConfig(serverConfig);
                }
            }

            // 返回
            return configBs.build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
