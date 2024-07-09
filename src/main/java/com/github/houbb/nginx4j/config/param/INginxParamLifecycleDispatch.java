package com.github.houbb.nginx4j.config.param;

import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

/**
 * 参数处理类-分发的声明周期管理
 *
 * @since 0.19.0
 */
public interface INginxParamLifecycleDispatch {

    /**
     * 开始分发前
     * @param context 上下文
     * @return 是否继续执行下一个指令 v0.23.0
     */
    boolean beforeDispatch(LifecycleDispatchContext context);

    /**
     * 分发后
     * @param context 上下文
     * @return 是否继续执行下一个指令 v0.23.0
     */
    boolean afterDispatch(LifecycleDispatchContext context);

    /**
     * 是否匹配当前处理类
     * @param context 上下文
     * @return 结果
     */
    boolean match(LifecycleDispatchContext context);

}
