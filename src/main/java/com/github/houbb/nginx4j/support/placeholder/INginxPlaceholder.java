package com.github.houbb.nginx4j.support.placeholder;

import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

/**
 * 占位符处理类
 * @since 0.17.0
 *
 * @author 老马啸西风
 */
public interface INginxPlaceholder {

    /**
     * 占位符处理逻辑
     * @param context 上下文
     */
    void placeholder(NginxRequestDispatchContext context);

}
