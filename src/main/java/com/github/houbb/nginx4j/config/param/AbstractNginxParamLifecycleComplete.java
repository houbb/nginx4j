package com.github.houbb.nginx4j.config.param;

import com.github.houbb.nginx4j.config.NginxCommonConfigParam;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelHandlerContext;

/**
 * 参数处理类
 *
 * @since 0.19.0
 */
public abstract class AbstractNginxParamLifecycleComplete implements INginxParamLifecycleComplete {

    public abstract void doBeforeComplete(NginxCommonConfigParam configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context);

    public abstract void doAfterComplete(NginxCommonConfigParam configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context);

    @Override
    public void beforeComplete(NginxCommonConfigParam configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context) {
        this.doBeforeComplete(configParam, ctx, object, context);
    }

    @Override
    public void afterComplete(NginxCommonConfigParam configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context) {
        this.doAfterComplete(configParam, ctx, object, context);
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
