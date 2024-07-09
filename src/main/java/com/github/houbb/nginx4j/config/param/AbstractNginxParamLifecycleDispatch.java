package com.github.houbb.nginx4j.config.param;

import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;


/**
 * 参数处理类
 *
 * @since 0.16.0
 */
public abstract class AbstractNginxParamLifecycleDispatch implements INginxParamLifecycleDispatch {

    public abstract boolean doBeforeDispatch(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context);

    public abstract boolean doAfterDispatch(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context);

    protected abstract String getKey(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context);

    public boolean doMatch(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context) {
        String key = getKey(configParam, context);
        return key.equalsIgnoreCase(configParam.getName());
    }

    @Override
    public boolean beforeDispatch(LifecycleDispatchContext context) {
        return doBeforeDispatch(context.getConfigParam(), context.getContext());
    }

    @Override
    public boolean afterDispatch(LifecycleDispatchContext context) {
        return doAfterDispatch(context.getConfigParam(), context.getContext());
    }

    @Override
    public boolean match(LifecycleDispatchContext context) {
        return doMatch(context.getConfigParam(), context.getContext());
    }

}
