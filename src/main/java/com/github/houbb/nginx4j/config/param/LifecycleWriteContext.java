package com.github.houbb.nginx4j.config.param;

import io.netty.channel.ChannelHandlerContext;

/**
 * @since 0.21.0
 */
public class LifecycleWriteContext extends LifecycleBaseContext  {

    private ChannelHandlerContext ctx;
    private Object object;

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
