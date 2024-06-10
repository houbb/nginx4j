package com.github.houbb.nginx4j.support.placeholder;

import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

/**
 * 占位符处理类
 *
 *
 * @since 0.17.0
 *
 * @author 老马啸西风
 */
public interface INginxPlaceholder {

    /**
     * 占位符处理逻辑
     * @param context 上下文
     */
    void beforeDispatch(NginxRequestDispatchContext context);

    /**
     * 写入前
     * @param context 上下文
     * @since 0.19.0
     */
    void beforeWrite(NginxRequestDispatchContext context);

    /**
     * 完成前
     * @param context 上下文
     * @since 0.19.0
     */
    void beforeComplete(NginxRequestDispatchContext context);

}
