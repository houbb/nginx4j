package com.github.houbb.nginx4j.config.load.component.impl;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.nginx4j.config.NginxUserGzipConfig;
import com.github.houbb.nginx4j.config.load.component.INginxUserGzipConfigLoad;
import com.github.houbb.nginx4j.util.InnerNginxConfUtil;
import com.github.odiszapc.nginxparser.NgxConfig;
import com.github.odiszapc.nginxparser.NgxParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 0.18.0
 */
public class NginxUserGzipConfigLoadFile implements INginxUserGzipConfigLoad {

    private final NgxConfig conf;

    public NginxUserGzipConfigLoadFile(NgxConfig conf) {
        this.conf = conf;
    }

    @Override
    public NginxUserGzipConfig load() {
        NginxUserGzipConfig config = new NginxUserGzipConfig();

        // gzip on
        boolean gzipEnabled = getGzipEnabled();
        config.setGzip(gzipEnabled);

// gzip_comp_level
        int gzipCompLevel = getGzipCompLevel();
        config.setGzipCompLevel(gzipCompLevel);

// gzip_types
        List<String> gzipTypes = getGzipTypes();
        config.setGzipTypes(gzipTypes);

// gzip_min_length
        int gzipMinLength = getGzipMinLength();
        config.setGzipMinLength(gzipMinLength);

// gzip_buffers
        String gzipBuffers = getGzipBuffers();
        config.setGzipBuffers(gzipBuffers);

// gzip_disable
        List<String> gzipDisable = getGzipDisable();
        config.setGzipDisable(gzipDisable);

// gzip_proxied
        String gzipProxied = getGzipProxied();
        config.setGzipProxied(gzipProxied);

// gzip_vary
        boolean gzipVary = getGzipVary();
        config.setGzipVary(gzipVary);

// gzip_http_version
        String gzipHttpVersion = getGzipHttpVersion();
        config.setGzipHttpVersion(gzipHttpVersion);

// gzip_static
        boolean gzipStatic = getGzipStatic();
        config.setGzipStatic(gzipStatic);


        return config;
    }

    // 获取 gzip_enabled 的值
    private boolean getGzipEnabled() {
        NgxParam param = conf.findParam("gzip");
        if (param != null) {
            String value = param.getValue();
            return "on".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value);
        }
        return false; // Default value or handle as necessary
    }

    // 获取 gzip_comp_level 的值
    private int getGzipCompLevel() {
        NgxParam param = conf.findParam("gzip_comp_level");
        if (param != null) {
            try {
                return Integer.parseInt(param.getValue());
            } catch (NumberFormatException e) {
                // Handle parsing exception
            }
        }
        return 0; // Default value or handle as necessary
    }

    // 获取 gzip_types 的值
    private List<String> getGzipTypes() {
        List<String> gzipTypes = new ArrayList<>();
        List<NgxParam> params = InnerNginxConfUtil.findParams(conf, "gzip_types");
        if (CollectionUtil.isNotEmpty(params)) {
            for (NgxParam param : params) {
                gzipTypes.add(param.getValue());
            }
        }
        return gzipTypes;
    }

    // 获取 gzip_min_length 的值
    private int getGzipMinLength() {
        NgxParam param = conf.findParam("gzip_min_length");
        if (param != null) {
            try {
                return Integer.parseInt(param.getValue());
            } catch (NumberFormatException e) {
                // Handle parsing exception
            }
        }
        return 0; // Default value or handle as necessary
    }

    // 获取 gzip_buffers 的值
    private String getGzipBuffers() {
        NgxParam param = conf.findParam("gzip_buffers");
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    // 获取 gzip_disable 的值
    private List<String> getGzipDisable() {
        List<String> gzipDisable = new ArrayList<>();
        List<NgxParam> params = InnerNginxConfUtil.findParams(conf, "gzip_disable");
        if (CollectionUtil.isNotEmpty(params)) {
            for (NgxParam param : params) {
                gzipDisable.add(param.getValue());
            }
        }
        return gzipDisable;
    }

    // 获取 gzip_proxied 的值
    private String getGzipProxied() {
        NgxParam param = conf.findParam("gzip_proxied");
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    // 获取 gzip_vary 的值
    private boolean getGzipVary() {
        NgxParam param = conf.findParam("gzip_vary");
        if (param != null) {
            String value = param.getValue();
            return "on".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value);
        }
        return false; // Default value or handle as necessary
    }

    // 获取 gzip_http_version 的值
    private String getGzipHttpVersion() {
        NgxParam param = conf.findParam("gzip_http_version");
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    // 获取 gzip_static 的值
    private boolean getGzipStatic() {
        NgxParam param = conf.findParam("gzip_static");
        if (param != null) {
            String value = param.getValue();
            return "on".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value);
        }
        return false; // Default value or handle as necessary
    }



}
