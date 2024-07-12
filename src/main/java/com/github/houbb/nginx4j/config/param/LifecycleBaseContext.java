package com.github.houbb.nginx4j.config.param;

import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelHandlerContext;

/**
 * @since 0.21.0
 */
public class LifecycleBaseContext {

    private NginxCommonConfigEntry configParam;

    private NginxRequestDispatchContext context;

    /**
     * 跳出所有的循环
     * @since 0.24.0
     */
    private boolean breakAllFlag;

    public boolean isBreakAllFlag() {
        return breakAllFlag;
    }

    public void setBreakAllFlag(boolean breakAllFlag) {
        this.breakAllFlag = breakAllFlag;
    }


    public NginxCommonConfigEntry getConfigParam() {
        return configParam;
    }

    public void setConfigParam(NginxCommonConfigEntry configParam) {
        this.configParam = configParam;
    }

    public NginxRequestDispatchContext getContext() {
        return context;
    }

    public void setContext(NginxRequestDispatchContext context) {
        this.context = context;
    }
}
