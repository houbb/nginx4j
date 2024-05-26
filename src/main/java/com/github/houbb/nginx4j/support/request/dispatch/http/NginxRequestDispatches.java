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

    public static NginxRequestDispatch fileBig() {
        return new NginxRequestDispatchFileBig();
    }

    public static NginxRequestDispatch fileSmall() {
        return new NginxRequestDispatchFileSmall();
    }

    public static NginxRequestDispatch fileDir() {
        return new NginxRequestDispatchFileDir();
    }

    public static NginxRequestDispatch fileRange() {
        return new NginxRequestDispatchFileRange();
    }

}
