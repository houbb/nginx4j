package com.github.houbb.nginx4j.config.param;

import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelHandlerContext;

/**
 * 参数处理类
 *
 * @since 0.19.0
 */
public abstract class AbstractNginxParamLifecycleComplete implements INginxParamLifecycleComplete {

    public abstract void doBeforeComplete(NginxCommonConfigEntry configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context);

    public abstract void doAfterComplete(NginxCommonConfigEntry configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context);

    @Override
    public void beforeComplete(LifecycleCompleteContext context) {
        this.doBeforeComplete(context.getConfigParam(), context.getCtx(), context.getObject(), context.getContext());
    }

    @Override
    public void afterComplete(LifecycleCompleteContext context) {
        this.doAfterComplete(context.getConfigParam(), context.getCtx(), context.getObject(), context.getContext());
    }

    @Override
    public boolean match(LifecycleCompleteContext context) {
        return doMatch(context.getConfigParam(), context.getCtx(), context.getObject(), context.getContext());
    }

    protected abstract String getKey(NginxCommonConfigEntry configParam,
                                     ChannelHandlerContext ctx,
                                     Object object,
                                     NginxRequestDispatchContext context);

    public boolean doMatch(NginxCommonConfigEntry configParam,
                           ChannelHandlerContext ctx,
                           Object object,
                           NginxRequestDispatchContext context) {
        String key = getKey(configParam, ctx, object, context);
        return key.equalsIgnoreCase(configParam.getName());
    }

}
