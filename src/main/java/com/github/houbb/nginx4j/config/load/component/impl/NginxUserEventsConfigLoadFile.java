package com.github.houbb.nginx4j.config.load.component.impl;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.nginx4j.config.NginxUserEventsConfig;
import com.github.houbb.nginx4j.config.NginxUserMainConfig;
import com.github.houbb.nginx4j.config.load.component.INginxUserEventsConfigLoad;
import com.github.houbb.nginx4j.config.load.component.INginxUserMainConfigLoad;
import com.github.odiszapc.nginxparser.NgxConfig;
import com.github.odiszapc.nginxparser.NgxEntry;
import com.github.odiszapc.nginxparser.NgxParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 0.18.0
 */
public class NginxUserEventsConfigLoadFile implements INginxUserEventsConfigLoad {

    private final NgxConfig conf;

    public NginxUserEventsConfigLoadFile(NgxConfig conf) {
        this.conf = conf;
    }

    @Override
    public NginxUserEventsConfig load() {
        NginxUserEventsConfig config = new NginxUserEventsConfig();

        // worker_connections
        int workerConnections = getWorkerConnections();
        config.setWorkerConnections(workerConnections);

// multi_accept
        boolean multiAccept = getMultiAccept();
        config.setMultiAccept(multiAccept);

// use
        String eventModel = getEventModel();
        config.setUse(eventModel);


        return config;
    }

    // 获取 worker_connections 的值
    private int getWorkerConnections() {
        NgxParam param = conf.findParam("worker_connections");
        if (param != null) {
            try {
                return Integer.parseInt(param.getValue());
            } catch (NumberFormatException e) {
                // Handle parsing exception
            }
        }
        return 0; // Default value or handle as necessary
    }

    // 获取 multi_accept 的值
    private boolean getMultiAccept() {
        NgxParam param = conf.findParam("multi_accept");
        if (param != null) {
            String value = param.getValue();
            return "on".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value);
        }
        return false; // Default value or handle as necessary
    }

    // 获取 use 的值
    private String getEventModel() {
        NgxParam param = conf.findParam("use");
        if (param != null) {
            return param.getValue();
        }
        return null;
    }


}
