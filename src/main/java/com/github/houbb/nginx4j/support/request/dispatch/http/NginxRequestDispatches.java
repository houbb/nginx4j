package com.github.houbb.nginx4j.support.request.dispatch.http;

import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatch;

/**
 * 分发工具类
 */
public final class NginxRequestDispatches {

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

}
