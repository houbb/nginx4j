package com.github.houbb.nginx4j.config;

/**
 * 用户配置
 *
 * @since 0.18.0
 */
public class NginxUserUpstreamConfig extends NginxCommonUserConfig {

    // upstream 定义一个上游服务器组。
    private String upstream;

    // server 定义单个上游服务器。
    private String server;

    // weight 设置服务器的权重，用于负载均衡。
    private String weight;

    // max_fails 设置在服务器被标记为不可用前允许的最大失败次数。
    private String maxFails;

    // fail_timeout 设置在服务器被标记为不可用多长时间后尝试重新启用的时间。
    private String failTimeout;

    // backup 指定服务器为备份服务器，只有在所有非备份服务器失败时才会使用。
    private String backup;

    // down 标记服务器为永久不可用，不会尝试与其建立连接。
    private String down;

    // keepalive 设置与上游服务器的 keepalive 连接参数。
    private String keepalive;

    // zone 设置共享内存区域以跟踪上游服务器的状态。
    private String zone;

    // hash 根据指定的键值对请求进行散列分配。
    private String hash;

    // ip_hash 根据客户端 IP 地址进行散列分配。
    private String ipHash;

    // least_conn 选择活跃连接数最少的服务器进行请求分发。
    private String leastConn;

    public String getUpstream() {
        return upstream;
    }

    public void setUpstream(String upstream) {
        this.upstream = upstream;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getMaxFails() {
        return maxFails;
    }

    public void setMaxFails(String maxFails) {
        this.maxFails = maxFails;
    }

    public String getFailTimeout() {
        return failTimeout;
    }

    public void setFailTimeout(String failTimeout) {
        this.failTimeout = failTimeout;
    }

    public String getBackup() {
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }

    public String getDown() {
        return down;
    }

    public void setDown(String down) {
        this.down = down;
    }

    public String getKeepalive() {
        return keepalive;
    }

    public void setKeepalive(String keepalive) {
        this.keepalive = keepalive;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getIpHash() {
        return ipHash;
    }

    public void setIpHash(String ipHash) {
        this.ipHash = ipHash;
    }

    public String getLeastConn() {
        return leastConn;
    }

    public void setLeastConn(String leastConn) {
        this.leastConn = leastConn;
    }

}
