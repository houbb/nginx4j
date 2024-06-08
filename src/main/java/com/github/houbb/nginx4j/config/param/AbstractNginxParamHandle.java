package com.github.houbb.nginx4j.config.param;

import com.github.houbb.nginx4j.config.NginxUserConfigParam;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelHandlerContext;


/**
 * 参数处理类
 *
 * @since 0.16.0
 */
public abstract class AbstractNginxParamHandle implements INginxParamHandle {

    public abstract void doBeforeDispatch(NginxUserConfigParam configParam, NginxRequestDispatchContext context);

    public abstract void doAfterDispatch(NginxUserConfigParam configParam, NginxRequestDispatchContext context);

    public abstract void doBeforeWrite(NginxUserConfigParam configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context);

    public abstract void doAfterWrite(NginxUserConfigParam configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context);

    public abstract boolean doMatch(NginxUserConfigParam configParam, NginxRequestDispatchContext context);

    @Override
    public void beforeDispatch(NginxUserConfigParam configParam, NginxRequestDispatchContext context) {
        doBeforeDispatch(configParam, context);
    }

    @Override
    public void afterDispatch(NginxUserConfigParam configParam, NginxRequestDispatchContext context) {
        doAfterDispatch(configParam, context);
    }

    @Override
    public void beforeWrite(NginxUserConfigParam configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context) {
        doBeforeWrite(configParam, ctx, object, context);
    }

    @Override
    public void afterWrite(NginxUserConfigParam configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context) {
        doAfterWrite(configParam, ctx, object, context);
    }

    @Override
    public boolean match(NginxUserConfigParam configParam, NginxRequestDispatchContext context) {
        return doMatch(configParam, context);
    }

}
