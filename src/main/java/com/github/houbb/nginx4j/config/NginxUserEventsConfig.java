package com.github.houbb.nginx4j.config;

/**
 * 用户配置
 *
 * @since 0.18.0
 */
public class NginxUserEventsConfig extends NginxCommonUserConfig {

    // worker_connections 每个工作进程允许的最大连接数。 `worker_connections 1024;`
    private int workerConnections;

    // multi_accept 设置工作进程是否同时接受多个新连接。 `multi_accept on;`
    private boolean multiAccept;

    // use 指定使用的事件驱动模型。 `use epoll;`
    private String use;


    public int getWorkerConnections() {
        return workerConnections;
    }

    public void setWorkerConnections(int workerConnections) {
        this.workerConnections = workerConnections;
    }

    public boolean isMultiAccept() {
        return multiAccept;
    }

    public void setMultiAccept(boolean multiAccept) {
        this.multiAccept = multiAccept;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }
}
