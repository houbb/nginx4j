package com.github.houbb.nginx4j.config.load.component.impl;

import com.github.houbb.nginx4j.config.NginxUserLogConfig;
import com.github.houbb.nginx4j.config.load.component.INginxUserLogConfigLoad;
import com.github.odiszapc.nginxparser.NgxConfig;
import com.github.odiszapc.nginxparser.NgxParam;

/**
 * @since 0.18.0
 */
public class NginxUserLogConfigLoadFile implements INginxUserLogConfigLoad {

    private final NgxConfig conf;

    public NginxUserLogConfigLoadFile(NgxConfig conf) {
        this.conf = conf;
    }

    @Override
    public NginxUserLogConfig load() {
        NginxUserLogConfig config = new NginxUserLogConfig();

        // access_log
        if (isAccessLogOff()) {
            config.setAccessLogOff(true);
        } else {
            String accessLog = getAccessLog();
            config.setAccessLog(accessLog);
        }

// error_log
        if (isErrorLogOff()) {
            config.setErrorLogOff(true);
        } else {
            String errorLog = getErrorLog();
            config.setErrorLog(errorLog);
        }

// log_format
        String logFormat = getLogFormat();
        config.setLogFormat(logFormat);

// open_log_file_cache
        String openLogFileCache = getOpenLogFileCache();
        config.setOpenLogFileCache(openLogFileCache);

// log_not_found
        boolean logNotFound = getLogNotFound();
        config.setLogNotFound(logNotFound);

// log_subrequest
        boolean logSubrequest = getLogSubrequest();
        config.setLogSubrequest(logSubrequest);

// buffered
        boolean buffered = getBuffered();
        config.setBuffered(buffered);

// flush
        String flush = getFlush();
        config.setFlush(flush);


        return config;
    }

    // 获取 access_log 的值
    private String getAccessLog() {
        NgxParam param = conf.findParam("access_log");
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

    // 获取 log_format 的值
    private String getLogFormat() {
        NgxParam param = conf.findParam("log_format");
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    // 获取 open_log_file_cache 的值
    private String getOpenLogFileCache() {
        NgxParam param = conf.findParam("open_log_file_cache");
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    // 获取 log_not_found 的值
    private boolean getLogNotFound() {
        NgxParam param = conf.findParam("log_not_found");
        if (param != null) {
            String value = param.getValue();
            return "on".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value);
        }
        return false; // Default value or handle as necessary
    }

    // 获取 log_subrequest 的值
    private boolean getLogSubrequest() {
        NgxParam param = conf.findParam("log_subrequest");
        if (param != null) {
            String value = param.getValue();
            return "on".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value);
        }
        return false; // Default value or handle as necessary
    }

    // 获取 access_log off 的值
    private boolean isAccessLogOff() {
        NgxParam param = conf.findParam("access_log");
        if (param != null) {
            String value = param.getValue();
            return "off".equalsIgnoreCase(value);
        }
        return false;
    }

    // 获取 error_log off 的值
    private boolean isErrorLogOff() {
        NgxParam param = conf.findParam("error_log");
        if (param != null) {
            String value = param.getValue();
            return "off".equalsIgnoreCase(value);
        }
        return false;
    }

    // 获取 buffered 的值
    private boolean getBuffered() {
        NgxParam param = conf.findParam("buffered");
        if (param != null) {
            String value = param.getValue();
            return "on".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value);
        }
        return false; // Default value or handle as necessary
    }

    // 获取 flush 的值
    private String getFlush() {
        NgxParam param = conf.findParam("flush");
        if (param != null) {
            return param.getValue();
        }
        return null;
    }




}
