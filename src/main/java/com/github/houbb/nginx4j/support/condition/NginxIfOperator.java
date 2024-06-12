package com.github.houbb.nginx4j.support.condition;

import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

/**
 * 操作符
 *
 * @since 0.21.0
 */
public interface NginxIfOperator {

    boolean eval(String left, String right, final NginxRequestDispatchContext dispatchContext);

    String operator();

}
