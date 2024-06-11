package com.github.houbb.nginx4j.config.param;

import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelHandlerContext;

/**
 * 参数处理类 write 生命周期
 *
 * @since 0.19.0
 */
public interface INginxParamLifecycleWrite {

    /**
     * channel 写之前
     * @param context 上下文
     */
    void beforeWrite(LifecycleWriteContext context);

    /**
     * channel 写之后
     * @param context 上下文
     */
    void afterWrite(LifecycleWriteContext context);

    /**
     * channel 是否匹配
     * @param context 上下文
     * @return 是否匹配
     */
    boolean match(LifecycleWriteContext context);

}
