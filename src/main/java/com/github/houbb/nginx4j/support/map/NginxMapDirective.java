package com.github.houbb.nginx4j.support.map;

import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

/**
 * map 指令的处理
 *
 * @since 0.22.0
 */
public interface NginxMapDirective {

    void map(final NginxRequestDispatchContext context);

}
