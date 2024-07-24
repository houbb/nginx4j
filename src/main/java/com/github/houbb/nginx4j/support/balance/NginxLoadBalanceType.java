package com.github.houbb.nginx4j.support.balance;

public enum NginxLoadBalanceType {
    ROUND_ROBIN("round-robin", "负载均衡"),
    LEAST_CONN("least_conn", "最少连接"),
    LEAST_REQUESTS("least-requests", "最少请求"),
    IP_HASH("ip_hash", "源地址哈希"),
    HASH("hash", "哈希策略"),
    RANDOM("random", "随机"),
    ;

    private final String code;
    private final String desc;

    NginxLoadBalanceType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
