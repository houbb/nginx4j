package com.github.houbb.nginx4j.support.request.dispatch.http;

import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatch;

/**
 * 分发工具类
 */
public final class NginxRequestDispatches {

    public static NginxRequestDispatch http301() {
        return new NginxRequestDispatch301();
    }
    public static NginxRequestDispatch http302() {
        return new NginxRequestDispatch302();
    }

    public static NginxRequestDispatch http400() {
        return new NginxRequestDispatch400();
    }

    public static NginxRequestDispatch http404() {
        return new NginxRequestDispatch404();
    }

    public static NginxRequestDispatch file() {
        return new NginxRequestDispatchFile();
    }
    public static NginxRequestDispatch fileDir() {
        return new NginxRequestDispatchFileDir();
    }

    public static NginxRequestDispatch fileRange() {
        return new NginxRequestDispatchFileRange();
    }

    public static NginxRequestDispatch httpReturn() {
        return new NginxRequestDispatchHttpReturn();
    }

    /**
     * 结果
     *
     * @since 0.27.0
     * @return 请求
     */
    public static NginxRequestDispatch proxyPass() {
        return new NginxRequestDispatchProxyPass();
    }

}
