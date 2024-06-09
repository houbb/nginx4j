package com.github.houbb.nginx4j.config;

import java.util.List;

/**
 * 用户配置
 *
 * @since 0.12.0
 */
public class NginxUserMainConfig extends NginxCommonUserConfig {

    // 全局配置开始
    // `worker_processes` 指定工作进程数量。
    private String workerProcesses; // 示例：worker_processes auto;

    // `error_log` 配置错误日志路径和日志级别。
    private String errorLog; // 示例：error_log /var/log/nginx/error.log warn;

    // `pid` 指定存储 Nginx 主进程 PID 的文件路径。
    private String pid; // 示例：pid /run/nginx.pid;

    // `worker_rlimit_nofile` 设置工作进程可打开的最大文件描述符数量。
    private int workerRlimitNofile; // 示例：worker_rlimit_nofile 8192;

    // `worker_priority` 设置工作进程的优先级。
    private int workerPriority; // 示例：worker_priority -10;

    // `daemon` 是否以守护进程方式运行 Nginx。
    private boolean daemon; // 示例：daemon on;

    // `master_process` 是否启用主进程模式。
    private boolean masterProcess; // 示例：master_process on;

    // `user` 设置 Nginx 进程的用户和组。
    private String user; // 示例：user www-data;

    // `worker_cpu_affinity` 绑定工作进程到特定 CPU 核心。
    private String workerCpuAffinity; // 示例：worker_cpu_affinity auto;

    // `worker_shutdown_timeout` 设置工作进程关闭时的超时时间。
    private String workerShutdownTimeout; // 示例：worker_shutdown_timeout 10s;

    // `timer_resolution` 设置事件定时器的分辨率。
    private String timerResolution; // 示例：timer_resolution 100ms;

    // `include` 包含其他配置文件。
    private String include; // 示例：include /etc/nginx/conf.d/*.conf;

    // `load_module` 动态加载 Nginx 模块。
    private String loadModule; // 示例：load_module modules/ngx_http_geoip_module.so;

    // `env` 设置环境变量。
    private List<String> envList; // 示例：env PATH; env MY_VARIABLE=value;

    // 全局配置结束

    public String getWorkerProcesses() {
        return workerProcesses;
    }

    public void setWorkerProcesses(String workerProcesses) {
        this.workerProcesses = workerProcesses;
    }

    public String getErrorLog() {
        return errorLog;
    }

    public void setErrorLog(String errorLog) {
        this.errorLog = errorLog;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getWorkerRlimitNofile() {
        return workerRlimitNofile;
    }

    public void setWorkerRlimitNofile(int workerRlimitNofile) {
        this.workerRlimitNofile = workerRlimitNofile;
    }

    public int getWorkerPriority() {
        return workerPriority;
    }

    public void setWorkerPriority(int workerPriority) {
        this.workerPriority = workerPriority;
    }

    public boolean isDaemon() {
        return daemon;
    }

    public void setDaemon(boolean daemon) {
        this.daemon = daemon;
    }

    public boolean isMasterProcess() {
        return masterProcess;
    }

    public void setMasterProcess(boolean masterProcess) {
        this.masterProcess = masterProcess;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getWorkerCpuAffinity() {
        return workerCpuAffinity;
    }

    public void setWorkerCpuAffinity(String workerCpuAffinity) {
        this.workerCpuAffinity = workerCpuAffinity;
    }

    public String getWorkerShutdownTimeout() {
        return workerShutdownTimeout;
    }

    public void setWorkerShutdownTimeout(String workerShutdownTimeout) {
        this.workerShutdownTimeout = workerShutdownTimeout;
    }

    public String getTimerResolution() {
        return timerResolution;
    }

    public void setTimerResolution(String timerResolution) {
        this.timerResolution = timerResolution;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public String getLoadModule() {
        return loadModule;
    }

    public void setLoadModule(String loadModule) {
        this.loadModule = loadModule;
    }

    public List<String> getEnvList() {
        return envList;
    }

    public void setEnvList(List<String> envList) {
        this.envList = envList;
    }
}
