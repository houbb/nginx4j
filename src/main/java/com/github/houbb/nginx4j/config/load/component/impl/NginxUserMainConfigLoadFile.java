package com.github.houbb.nginx4j.config.load.component.impl;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.nginx4j.config.NginxUserMainConfig;
import com.github.houbb.nginx4j.config.load.component.INginxUserMainConfigLoad;
import com.github.odiszapc.nginxparser.NgxConfig;
import com.github.odiszapc.nginxparser.NgxEntry;
import com.github.odiszapc.nginxparser.NgxParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 0.18.0
 */
public class NginxUserMainConfigLoadFile implements INginxUserMainConfigLoad {

    private final NgxConfig conf;

    public NginxUserMainConfigLoadFile(NgxConfig conf) {
        this.conf = conf;
    }

    @Override
    public NginxUserMainConfig load() {
        NginxUserMainConfig config = new NginxUserMainConfig();

        // worker_processes
        String workerProcesses = getWorkerProcesses();
        config.setWorkerProcesses(workerProcesses);

// error_log
        String errorLog = getErrorLog();
        config.setErrorLog(errorLog);

// pid
        String pid = getPid();
        config.setPid(pid);

// worker_rlimit_nofile
        int workerRlimitNofile = getWorkerRlimitNofile();
        config.setWorkerRlimitNofile(workerRlimitNofile);

// worker_priority
        int workerPriority = getWorkerPriority();
        config.setWorkerPriority(workerPriority);

// daemon
        boolean daemon = getDaemon();
        config.setDaemon(daemon);

// master_process
        boolean masterProcess = getMasterProcess();
        config.setMasterProcess(masterProcess);

// user
        String user = getUser();
        config.setUser(user);

// worker_cpu_affinity
        String workerCpuAffinity = getWorkerCpuAffinity();
        config.setWorkerCpuAffinity(workerCpuAffinity);

// worker_shutdown_timeout
        String workerShutdownTimeout = getWorkerShutdownTimeout();
        config.setWorkerShutdownTimeout(workerShutdownTimeout);

// timer_resolution
        String timerResolution = getTimerResolution();
        config.setTimerResolution(timerResolution);

// include
        String include = getInclude();
        config.setInclude(include);

// load_module
        String loadModule = getLoadModule();
        config.setLoadModule(loadModule);

// env
        List<String> envVariables = getEnvVariables();
        config.setEnvList(envVariables);

        return config;
    }

    // 获取 worker_processes 的值
    private String getWorkerProcesses() {
        NgxParam param = conf.findParam("worker_processes");
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    // 获取 error_log 的值
    private String getErrorLog() {
        NgxParam param = conf.findParam("error_log");
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    // 获取 pid 的值
    private String getPid() {
        NgxParam param = conf.findParam("pid");
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    // 获取 worker_rlimit_nofile 的值
    private int getWorkerRlimitNofile() {
        NgxParam param = conf.findParam("worker_rlimit_nofile");
        if (param != null) {
            try {
                return Integer.parseInt(param.getValue());
            } catch (NumberFormatException e) {
                // Handle parsing exception
            }
        }
        return 0; // Default value or handle as necessary
    }

    // 获取 worker_priority 的值
    private int getWorkerPriority() {
        NgxParam param = conf.findParam("worker_priority");
        if (param != null) {
            try {
                return Integer.parseInt(param.getValue());
            } catch (NumberFormatException e) {
                // Handle parsing exception
            }
        }
        return 0; // Default value or handle as necessary
    }

    // 获取 daemon 的值
    private boolean getDaemon() {
        NgxParam param = conf.findParam("daemon");
        if (param != null) {
            String value = param.getValue();
            return "on".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value);
        }
        return false; // Default value or handle as necessary
    }

    // 获取 master_process 的值
    private boolean getMasterProcess() {
        NgxParam param = conf.findParam("master_process");
        if (param != null) {
            String value = param.getValue();
            return "on".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value);
        }
        return false; // Default value or handle as necessary
    }

    // 获取 user 的值
    private String getUser() {
        NgxParam param = conf.findParam("user");
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    // 获取 worker_cpu_affinity 的值
    private String getWorkerCpuAffinity() {
        NgxParam param = conf.findParam("worker_cpu_affinity");
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    // 获取 worker_shutdown_timeout 的值
    private String getWorkerShutdownTimeout() {
        NgxParam param = conf.findParam("worker_shutdown_timeout");
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    // 获取 timer_resolution 的值
    private String getTimerResolution() {
        NgxParam param = conf.findParam("timer_resolution");
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    // 获取 include 的值
    private String getInclude() {
        NgxParam param = conf.findParam("include");
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    // 获取 load_module 的值
    private String getLoadModule() {
        NgxParam param = conf.findParam("load_module");
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    // 获取 env 的值（多个环境变量）
    private List<String> getEnvVariables() {
        List<String> envVariables = new ArrayList<>();
        // Example implementation assuming multiple values
        List<NgxEntry> params = conf.findAll(NgxParam.class, "env");
        if (CollectionUtil.isNotEmpty(params)) {
            for (NgxEntry entry : params) {
                NgxParam ngxParam = (NgxParam) entry;
                envVariables.add(ngxParam.getValue());
            }
        }
        return envVariables;
    }


}
