package com.github.houbb.nginx4j.config;

import java.util.List;
import java.util.Map;

/**
 * 用户配置
 *
 * @since 0.18.0
 */
public class NginxUserCacheConfig {

    // proxy_cache_path 配置代理缓存路径和属性。
    private String proxyCachePath;

    // proxy_cache 启用或禁用缓存以及指定使用的缓存区域。
    private String proxyCache;

    // proxy_cache_key 配置用于生成缓存键的变量。
    private String proxyCacheKey;

    // proxy_cache_valid 设置缓存有效时间。
    private Map<String, String> proxyCacheValid;

    // proxy_cache_methods 指定允许缓存的请求方法。
    private List<String> proxyCacheMethods;

    // proxy_cache_bypass 配置条件下绕过缓存的规则。
    private List<String> proxyCacheBypass;

    // proxy_cache_use_stale 配置是否在后端服务器不可用时使用过期缓存。
    private List<String> proxyCacheUseStale;

    // proxy_no_cache 配置条件下不缓存的规则。
    private List<String> proxyNoCache;

    // proxy_cache_lock 启用或禁用缓存锁机制。
    private boolean proxyCacheLock;

    // proxy_cache_lock_timeout 配置缓存锁定超时时间。
    private String proxyCacheLockTimeout;

    // proxy_cache_background_update 启用或禁用后台更新缓存。
    private boolean proxyCacheBackgroundUpdate;

    // proxy_cache_revalidate 配置是否在缓存过期时重新验证内容。
    private boolean proxyCacheRevalidate;

    public String getProxyCachePath() {
        return proxyCachePath;
    }

    public void setProxyCachePath(String proxyCachePath) {
        this.proxyCachePath = proxyCachePath;
    }

    public String getProxyCache() {
        return proxyCache;
    }

    public void setProxyCache(String proxyCache) {
        this.proxyCache = proxyCache;
    }

    public String getProxyCacheKey() {
        return proxyCacheKey;
    }

    public void setProxyCacheKey(String proxyCacheKey) {
        this.proxyCacheKey = proxyCacheKey;
    }

    public Map<String, String> getProxyCacheValid() {
        return proxyCacheValid;
    }

    public void setProxyCacheValid(Map<String, String> proxyCacheValid) {
        this.proxyCacheValid = proxyCacheValid;
    }

    public List<String> getProxyCacheMethods() {
        return proxyCacheMethods;
    }

    public void setProxyCacheMethods(List<String> proxyCacheMethods) {
        this.proxyCacheMethods = proxyCacheMethods;
    }

    public List<String> getProxyCacheBypass() {
        return proxyCacheBypass;
    }

    public void setProxyCacheBypass(List<String> proxyCacheBypass) {
        this.proxyCacheBypass = proxyCacheBypass;
    }

    public List<String> getProxyCacheUseStale() {
        return proxyCacheUseStale;
    }

    public void setProxyCacheUseStale(List<String> proxyCacheUseStale) {
        this.proxyCacheUseStale = proxyCacheUseStale;
    }

    public List<String> getProxyNoCache() {
        return proxyNoCache;
    }

    public void setProxyNoCache(List<String> proxyNoCache) {
        this.proxyNoCache = proxyNoCache;
    }

    public boolean isProxyCacheLock() {
        return proxyCacheLock;
    }

    public void setProxyCacheLock(boolean proxyCacheLock) {
        this.proxyCacheLock = proxyCacheLock;
    }

    public String getProxyCacheLockTimeout() {
        return proxyCacheLockTimeout;
    }

    public void setProxyCacheLockTimeout(String proxyCacheLockTimeout) {
        this.proxyCacheLockTimeout = proxyCacheLockTimeout;
    }

    public boolean isProxyCacheBackgroundUpdate() {
        return proxyCacheBackgroundUpdate;
    }

    public void setProxyCacheBackgroundUpdate(boolean proxyCacheBackgroundUpdate) {
        this.proxyCacheBackgroundUpdate = proxyCacheBackgroundUpdate;
    }

    public boolean isProxyCacheRevalidate() {
        return proxyCacheRevalidate;
    }

    public void setProxyCacheRevalidate(boolean proxyCacheRevalidate) {
        this.proxyCacheRevalidate = proxyCacheRevalidate;
    }
}
