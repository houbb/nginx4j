package com.github.houbb.nginx4j.config.param;

import com.github.houbb.nginx4j.config.NginxCommonConfigParam;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelHandlerContext;


/**
 * 参数处理类
 *
 * @since 0.16.0
 */
public abstract class AbstractNginxParamHandle implements INginxParamHandle {

    public abstract void doBeforeDispatch(NginxCommonConfigParam configParam, NginxRequestDispatchContext context);

    public abstract void doAfterDispatch(NginxCommonConfigParam configParam, NginxRequestDispatchContext context);

    public abstract void doBeforeWrite(NginxCommonConfigParam configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context);

    public abstract void doAfterWrite(NginxCommonConfigParam configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context);

    protected abstract String getKey(NginxCommonConfigParam configParam, NginxRequestDispatchContext context);

    public boolean doMatch(NginxCommonConfigParam configParam, NginxRequestDispatchContext context) {
        String key = getKey(configParam, context);
        return key.equalsIgnoreCase(configParam.getName());
    }

    @Override
    public void beforeDispatch(NginxCommonConfigParam configParam, NginxRequestDispatchContext context) {
        doBeforeDispatch(configParam, context);
    }

    @Override
    public void afterDispatch(NginxCommonConfigParam configParam, NginxRequestDispatchContext context) {
        doAfterDispatch(configParam, context);
    }

    @Override
    public void beforeWrite(NginxCommonConfigParam configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context) {
        doBeforeWrite(configParam, ctx, object, context);
    }

    @Override
    public void afterWrite(NginxCommonConfigParam configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context) {
        doAfterWrite(configParam, ctx, object, context);
    }

    @Override
    public boolean match(NginxCommonConfigParam configParam, NginxRequestDispatchContext context) {
        return doMatch(configParam, context);
    }

}
