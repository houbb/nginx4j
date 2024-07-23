package com.github.houbb.nginx4j.support.balance;

import com.github.houbb.load.balance.support.server.IServer;

import java.util.List;

/**
 * @since 0.27.0
 */
public class NginxLoadBalanceConfig {

    /**
     * 是否需要反向代理
     */
    private boolean needProxyPass;

    /**
     * 服务器列表
     */
    private List<IServer> serverList;

    /**
     * 代理策略
     */
    private String proxyStrategy;

    public boolean isNeedProxyPass() {
        return needProxyPass;
    }

    public void setNeedProxyPass(boolean needProxyPass) {
        this.needProxyPass = needProxyPass;
    }

    public List<IServer> getServerList() {
        return serverList;
    }

    public void setServerList(List<IServer> serverList) {
        this.serverList = serverList;
    }

    public String getProxyStrategy() {
        return proxyStrategy;
    }

    public void setProxyStrategy(String proxyStrategy) {
        this.proxyStrategy = proxyStrategy;
    }

    @Override
    public String toString() {
        return "NginxLoadBalanceConfig{" +
                "needProxyPass=" + needProxyPass +
                ", serverList=" + serverList +
                ", proxyStrategy='" + proxyStrategy + '\'' +
                '}';
    }

}
