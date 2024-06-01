package com.github.houbb.nginx4j.bs;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxUserConfig;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;
import com.github.houbb.nginx4j.constant.NginxConst;
import com.github.houbb.nginx4j.constant.NginxUserConfigDefaultConst;
import com.github.houbb.nginx4j.support.handler.NginxNettyServerHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @since 0.12.0
 */
public class NginxUserConfigBs {

    private static final Log logger = LogFactory.getLog(NginxNettyServerHandler.class);

    // 全局配置
    private String httpPid = NginxUserConfigDefaultConst.HTTP_PID;

    public static NginxUserConfigBs newInstance() {
        return new NginxUserConfigBs();
    }

    private final Map<String, NginxUserServerConfig> serverConfigMap = new HashMap<>();

    private final Set<Integer> serverPortSet = new HashSet<>();

    public NginxUserConfigBs httpPid(String httpPid) {
        this.httpPid = httpPid;
        return this;
    }

    public NginxUserConfigBs addServerConfig(int port, String hostName, NginxUserServerConfig serverConfig) {
        ArgUtil.notEmpty(hostName, "hostName");
        ArgUtil.notNull(serverConfig, "serverConfig");

        serverConfigMap.put(hostName, serverConfig);
        serverPortSet.add(port);

        return this;
    }

    public NginxUserConfig build() {
        prepareForServerConfig();

        NginxUserConfig config = new NginxUserConfig();
        config.setHttpPid(httpPid);
        config.setServerConfigMap(serverConfigMap);
        config.setServerPortSet(serverPortSet);

        return config;
    }

    private void prepareForServerConfig() {
        boolean hasUserDefaultServer = hasUserDefaultServer();
        if(!hasUserDefaultServer) {
            NginxUserServerConfig config = NginxUserServerConfigBs.newInstance().build();
            logger.warn("不存在用户默认的 server, 系统自动添加默认的 server 如下={}", config);
            this.addServerConfig(config.getHttpServerListen(), NginxConst.DEFAULT_SERVER, config);
        }
    }

    private boolean hasUserDefaultServer() {
        return serverConfigMap.containsKey(NginxConst.DEFAULT_SERVER);
    }

}
