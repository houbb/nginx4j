package com.github.houbb.nginx4j.support.placeholder;

import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

/**
 * 占位符处理类
 * @since 0.17.0
 *
 * @author 老马啸西风
 */
public interface INginxPlaceholderManager {

    /**
     * 初始化
     * @param context 上下文
     */
    void beforeDispatch(final NginxRequestDispatchContext context);

    /**
     * 初始化
     * @param context 上下文
     */
    void beforeWrite(final NginxRequestDispatchContext context);

    /**
     * 初始化
     * @param context 上下文
     */
    void beforeComplete(final NginxRequestDispatchContext context);

    /**
     * 获取值
     * @param context 上下文
     * @param key key
     * @return 结果
     */
    Object getValue(NginxRequestDispatchContext context, final String key);

}
