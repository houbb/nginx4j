package com.github.houbb.nginx4j.config.param;

import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelHandlerContext;

/**
 * 参数处理类
 *
 * @since 0.19.0
 */
public abstract class AbstractNginxParamLifecycleWrite implements INginxParamLifecycleWrite {

    public abstract void doBeforeWrite(NginxCommonConfigEntry configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context);

    public abstract void doAfterWrite(NginxCommonConfigEntry configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context);

    @Override
    public void beforeWrite(LifecycleWriteContext context) {
        this.doBeforeWrite(context.getConfigParam(), context.getCtx(), context.getObject(), context.getContext());
    }

    @Override
    public void afterWrite(LifecycleWriteContext context) {
        this.doAfterWrite(context.getConfigParam(), context.getCtx(), context.getObject(), context.getContext());
    }

    @Override
    public boolean match(LifecycleWriteContext context) {
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
