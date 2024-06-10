package com.github.houbb.nginx4j.config.param;

import com.github.houbb.nginx4j.config.NginxCommonConfigParam;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelHandlerContext;

/**
 * 参数处理类-分发的声明周期管理
 *
 * @since 0.19.0
 */
public interface INginxParamLifecycleDispatch {

    /**
     * 开始分发前
     * @param configParam 参数
     * @param context 上下文
     */
    void beforeDispatch(NginxCommonConfigParam configParam, final NginxRequestDispatchContext context);

    /**
     * 分发后
     * @param configParam 参数
     * @param context 上下文
     */
    void afterDispatch(NginxCommonConfigParam configParam, final NginxRequestDispatchContext context);

    /**
     * 是否匹配当前处理类
     * @param configParam 参数
     * @param context 上下文
     * @return 结果
     */
    boolean match(final NginxCommonConfigParam configParam, final NginxRequestDispatchContext context);

}
