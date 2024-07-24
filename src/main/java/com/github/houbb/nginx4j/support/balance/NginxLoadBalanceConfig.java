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
     * 获取对应的流名称
     */
    private String upstreamName;

    /**
     * 服务器列表
     */
    private List<IServer> upstreamServerList;

    /**
     * 代理策略
     */
    private String upstreamProxyStrategy;

    /**
     * 代理策略的值
     */
    private String upstreamProxyStrategyValue;

    public boolean isNeedProxyPass() {
        return needProxyPass;
    }

    public void setNeedProxyPass(boolean needProxyPass) {
        this.needProxyPass = needProxyPass;
    }

    public String getUpstreamName() {
        return upstreamName;
    }

    public void setUpstreamName(String upstreamName) {
        this.upstreamName = upstreamName;
    }

    public List<IServer> getUpstreamServerList() {
        return upstreamServerList;
    }

    public void setUpstreamServerList(List<IServer> upstreamServerList) {
        this.upstreamServerList = upstreamServerList;
    }

    public String getUpstreamProxyStrategy() {
        return upstreamProxyStrategy;
    }

    public void setUpstreamProxyStrategy(String upstreamProxyStrategy) {
        this.upstreamProxyStrategy = upstreamProxyStrategy;
    }

    public String getUpstreamProxyStrategyValue() {
        return upstreamProxyStrategyValue;
    }

    public void setUpstreamProxyStrategyValue(String upstreamProxyStrategyValue) {
        this.upstreamProxyStrategyValue = upstreamProxyStrategyValue;
    }

    @Override
    public String toString() {
        return "NginxLoadBalanceConfig{" +
                "needProxyPass=" + needProxyPass +
                ", upstreamName='" + upstreamName + '\'' +
                ", upstreamServerList=" + upstreamServerList +
                ", upstreamProxyStrategy='" + upstreamProxyStrategy + '\'' +
                ", upstreamProxyStrategyValue='" + upstreamProxyStrategyValue + '\'' +
                '}';
    }

}
