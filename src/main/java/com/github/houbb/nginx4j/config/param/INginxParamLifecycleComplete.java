package com.github.houbb.nginx4j.config.param;

import com.github.houbb.nginx4j.config.NginxCommonConfigParam;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelHandlerContext;

/**
 * 参数处理类
 *
 * @since 0.19.0
 */
public interface INginxParamLifecycleComplete {


    /**
     * channel 写之前
     * @param configParam 参数
     * @param ctx channel 上下文
     * @param object 对象
     * @param context 上下文
     */
    void beforeComplete(NginxCommonConfigParam configParam,
                     final ChannelHandlerContext ctx,
                     final Object object,
                     final NginxRequestDispatchContext context);

    /**
     * channel 写之后
     * @param configParam 参数
     * @param ctx channel 上下文
     * @param object 对象
     * @param context 上下文
     */
    void afterComplete(NginxCommonConfigParam configParam,
                    final ChannelHandlerContext ctx,
                    final Object object,
                    final NginxRequestDispatchContext context);

    /**
     * channel 是否匹配
     * @param configParam 参数
     * @param ctx channel 上下文
     * @param object 对象
     * @param context 上下文
     * @return 是否匹配
     */
    boolean match(NginxCommonConfigParam configParam,
                  final ChannelHandlerContext ctx,
                  final Object object,
                  final NginxRequestDispatchContext context);

}
