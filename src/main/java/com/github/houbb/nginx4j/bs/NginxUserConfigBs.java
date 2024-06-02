package com.github.houbb.nginx4j.bs;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxUserConfig;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;
import com.github.houbb.nginx4j.constant.NginxConst;
import com.github.houbb.nginx4j.constant.NginxUserConfigDefaultConst;
import com.github.houbb.nginx4j.support.handler.NginxNettyServerHandler;

import java.util.*;

/**
 * @since 0.12.0
 */
public class NginxUserConfigBs {

    private static final Log logger = LogFactory.getLog(NginxNettyServerHandler.class);

    /**
     * 默认用户的服务端配置
     * @since 0.14.0
     */
    private NginxUserServerConfig defaultUserServerConfig = NginxUserServerConfigBs.newInstance().build();

    // 全局配置
    private String httpPid = NginxUserConfigDefaultConst.HTTP_PID;

    public static NginxUserConfigBs newInstance() {
        return new NginxUserConfigBs();
    }

    /**
     * 全部的 server 配置列表
     *
     * @since 0.12.0
     */
    private final List<NginxUserServerConfig> serverConfigList = new ArrayList<>();

    private final Set<Integer> serverPortSet = new HashSet<>();

    public NginxUserConfigBs httpPid(String httpPid) {
        this.httpPid = httpPid;
        return this;
    }

    public NginxUserConfigBs defaultUserServerConfig(NginxUserServerConfig defaultUserServerConfig) {
        this.defaultUserServerConfig = defaultUserServerConfig;
        return this;
    }

    public NginxUserConfigBs addServerConfig(NginxUserServerConfig serverConfig) {
        serverConfigList.add(serverConfig);

        serverPortSet.add(serverConfig.getHttpServerListen());

        return this;
    }

    public NginxUserConfig build() {
        NginxUserConfig config = new NginxUserConfig();
        config.setHttpPid(httpPid);
        config.setServerPortSet(serverPortSet);
        config.setServerConfigList(serverConfigList);
        config.setDefaultUserServerConfig(defaultUserServerConfig);
        return config;
    }

}
