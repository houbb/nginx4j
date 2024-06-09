package com.github.houbb.nginx4j.config.load.component.impl;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.nginx4j.config.NginxUserCacheConfig;
import com.github.houbb.nginx4j.config.load.component.INginxUserCacheConfigLoad;
import com.github.houbb.nginx4j.util.InnerNginxConfUtil;
import com.github.odiszapc.nginxparser.NgxConfig;
import com.github.odiszapc.nginxparser.NgxParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NginxUserCacheConfigLoadFile implements INginxUserCacheConfigLoad {

    private final NgxConfig conf;

    public NginxUserCacheConfigLoadFile(NgxConfig conf) {
        this.conf = conf;
    }

    @Override
    public NginxUserCacheConfig load() {
        NginxUserCacheConfig config = new NginxUserCacheConfig();

        // proxy_cache_path
        String proxyCachePath = getProxyCachePath();
        config.setProxyCachePath(proxyCachePath);

// proxy_cache
        String proxyCache = getProxyCache();
        config.setProxyCache(proxyCache);

// proxy_cache_key
        String proxyCacheKey = getProxyCacheKey();
        config.setProxyCacheKey(proxyCacheKey);

// proxy_cache_valid
        Map<String, String> proxyCacheValid = getProxyCacheValid();
        config.setProxyCacheValid(proxyCacheValid);

// proxy_cache_methods
        List<String> proxyCacheMethods = getProxyCacheMethods();
        config.setProxyCacheMethods(proxyCacheMethods);

// proxy_cache_bypass
        List<String> proxyCacheBypass = getProxyCacheBypass();
        config.setProxyCacheBypass(proxyCacheBypass);

// proxy_cache_use_stale
        List<String> proxyCacheUseStale = getProxyCacheUseStale();
        config.setProxyCacheUseStale(proxyCacheUseStale);

// proxy_no_cache
        List<String> proxyNoCache = getProxyNoCache();
        config.setProxyNoCache(proxyNoCache);

// proxy_cache_lock
        boolean proxyCacheLock = getProxyCacheLock();
        config.setProxyCacheLock(proxyCacheLock);

// proxy_cache_lock_timeout
        String proxyCacheLockTimeout = getProxyCacheLockTimeout();
        config.setProxyCacheLockTimeout(proxyCacheLockTimeout);

// proxy_cache_background_update
        boolean proxyCacheBackgroundUpdate = getProxyCacheBackgroundUpdate();
        config.setProxyCacheBackgroundUpdate(proxyCacheBackgroundUpdate);

// proxy_cache_revalidate
        boolean proxyCacheRevalidate = getProxyCacheRevalidate();
        config.setProxyCacheRevalidate(proxyCacheRevalidate);

        return config;
    }


    // 获取 proxy_cache_path 的值
    private String getProxyCachePath() {
        NgxParam param = conf.findParam("proxy_cache_path");
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    // 获取 proxy_cache 的值
    private String getProxyCache() {
        NgxParam param = conf.findParam("proxy_cache");
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    // 获取 proxy_cache_key 的值
    private String getProxyCacheKey() {
        NgxParam param = conf.findParam("proxy_cache_key");
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    // 获取 proxy_cache_valid 的值
    private Map<String, String> getProxyCacheValid() {
        Map<String, String> proxyCacheValid = new HashMap<>();
        // Example implementation assuming multiple values
        List<NgxParam> params = InnerNginxConfUtil.findParams(conf, "proxy_cache_valid");
        if (CollectionUtil.isNotEmpty(params)) {
            for (NgxParam param : params) {
                proxyCacheValid.put(param.getName(), param.getValue());
            }
        }
        return proxyCacheValid;
    }

    // 获取 proxy_cache_methods 的值
    private List<String> getProxyCacheMethods() {
        List<String> proxyCacheMethods = new ArrayList<>();
        // Example implementation assuming multiple values
        List<NgxParam> params = InnerNginxConfUtil.findParams(conf, "proxy_cache_methods");
        if (CollectionUtil.isNotEmpty(params)) {
            for (NgxParam param : params) {
                proxyCacheMethods.add(param.getValue());
            }
        }
        return proxyCacheMethods;
    }

    // 获取 proxy_cache_bypass 的值
    private List<String> getProxyCacheBypass() {
        List<String> proxyCacheBypass = new ArrayList<>();
        // Example implementation assuming multiple values
        List<NgxParam> params = InnerNginxConfUtil.findParams(conf, "proxy_cache_bypass");
        if (CollectionUtil.isNotEmpty(params)) {
            for (NgxParam param : params) {
                proxyCacheBypass.add(param.getValue());
            }
        }
        return proxyCacheBypass;
    }

    // 获取 proxy_cache_use_stale 的值
    private List<String> getProxyCacheUseStale() {
        List<String> proxyCacheUseStale = new ArrayList<>();
        // Example implementation assuming multiple values
        List<NgxParam> params = InnerNginxConfUtil.findParams(conf, "proxy_cache_use_stale");
        if (CollectionUtil.isNotEmpty(params)) {
            for (NgxParam param : params) {
                proxyCacheUseStale.add(param.getValue());
            }
        }
        return proxyCacheUseStale;
    }

    // 获取 proxy_no_cache 的值
    private List<String> getProxyNoCache() {
        List<String> proxyNoCache = new ArrayList<>();
        // Example implementation assuming multiple values
        List<NgxParam> params = InnerNginxConfUtil.findParams(conf, "proxy_no_cache");
        if (CollectionUtil.isNotEmpty(params)) {
            for (NgxParam param : params) {
                proxyNoCache.add(param.getValue());
            }
        }
        return proxyNoCache;
    }

    // 获取 proxy_cache_lock 的值
    private boolean getProxyCacheLock() {
        NgxParam param = conf.findParam("proxy_cache_lock");
        if (param != null) {
            String value = param.getValue();
            return "on".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value);
        }
        return false; // Default value or handle as necessary
    }

    // 获取 proxy_cache_lock_timeout 的值
    private String getProxyCacheLockTimeout() {
        NgxParam param = conf.findParam("proxy_cache_lock_timeout");
        if (param != null) {
            return param.getValue();
        }
        return null;
    }

    // 获取 proxy_cache_background_update 的值
    private boolean getProxyCacheBackgroundUpdate() {
        NgxParam param = conf.findParam("proxy_cache_background_update");
        if (param != null) {
            String value = param.getValue();
            return "on".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value);
        }
        return false; // Default value or handle as necessary
    }

    // 获取 proxy_cache_revalidate 的值
    private boolean getProxyCacheRevalidate() {
        NgxParam param = conf.findParam("proxy_cache_revalidate");
        if (param != null) {
            String value = param.getValue();
            return "on".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value);
        }
        return false; // Default value or handle as necessary
    }

}
