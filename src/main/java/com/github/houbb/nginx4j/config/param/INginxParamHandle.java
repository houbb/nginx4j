package com.github.houbb.nginx4j.config.param;

import com.github.houbb.nginx4j.config.NginxUserConfigParam;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelHandlerContext;

/**
 * 参数处理类
 *
 * @since 0.16.0
 */
public interface INginxParamHandle {

    /**
     * 开始分发前
     * @param context 上下文
     */
    void beforeDispatch(NginxUserConfigParam configParam, final NginxRequestDispatchContext context);

    /**
     * 分发后
     * @param context 上下文
     */
    void afterDispatch(NginxUserConfigParam configParam, final NginxRequestDispatchContext context);

    /**
     * channel 写之前
     * @param ctx channel 上下文
     * @param object 对象
     * @param context 上下文
     */
    void beforeWrite(NginxUserConfigParam configParam,
                     final ChannelHandlerContext ctx,
                     final Object object,
                     final NginxRequestDispatchContext context);

    /**
     * channel 写之后
     * @param ctx channel 上下文
     * @param object 对象
     * @param context 上下文
     */
    void afterWrite(NginxUserConfigParam configParam,
                    final ChannelHandlerContext ctx,
                    final Object object,
                    final NginxRequestDispatchContext context);

    /**
     * 是否匹配当前处理类
     * @param configParam 参数
     * @param context 上下文
     * @return 结果
     */
    boolean match(final NginxUserConfigParam configParam, final NginxRequestDispatchContext context);

}
