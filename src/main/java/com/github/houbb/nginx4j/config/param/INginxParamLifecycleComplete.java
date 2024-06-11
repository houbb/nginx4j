package com.github.houbb.nginx4j.config.param;

/**
 * 参数处理类
 *
 * @since 0.19.0
 */
public interface INginxParamLifecycleComplete {


    /**
     * channel 写之前
     * @param context 上下文
     */
    void beforeComplete(LifecycleCompleteContext context);

    /**
     * channel 写之后
     * @param context 上下文
     */
    void afterComplete(LifecycleCompleteContext context);

    /**
     * channel 是否匹配
     * @param context 上下文
     * @return 是否匹配
     */
    boolean match(LifecycleCompleteContext context);

}
