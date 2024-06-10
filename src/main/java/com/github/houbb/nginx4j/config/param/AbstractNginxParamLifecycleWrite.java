package com.github.houbb.nginx4j.config.param;

import com.github.houbb.nginx4j.config.NginxCommonConfigParam;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelHandlerContext;

/**
 * 参数处理类
 *
 * @since 0.19.0
 */
public abstract class AbstractNginxParamLifecycleWrite implements INginxParamLifecycleWrite {

    public abstract void doBeforeWrite(NginxCommonConfigParam configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context);

    public abstract void doAfterWrite(NginxCommonConfigParam configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context);

    @Override
    public void beforeWrite(NginxCommonConfigParam configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context) {
        this.doBeforeWrite(configParam, ctx, object, context);
    }

    @Override
    public void afterWrite(NginxCommonConfigParam configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context) {
        this.doAfterWrite(configParam, ctx, object, context);
    }

    @Override
    public boolean match(NginxCommonConfigParam configParam,
                         ChannelHandlerContext ctx,
                         Object object,
                         NginxRequestDispatchContext context) {
        return doMatch(configParam, ctx, object, context);
    }

    protected abstract String getKey(NginxCommonConfigParam configParam,
                                     ChannelHandlerContext ctx,
                                     Object object,
                                     NginxRequestDispatchContext context);

    public boolean doMatch(NginxCommonConfigParam configParam,
                           ChannelHandlerContext ctx,
                           Object object,
                           NginxRequestDispatchContext context) {
        String key = getKey(configParam, ctx, object, context);
        return key.equalsIgnoreCase(configParam.getName());
    }

}
