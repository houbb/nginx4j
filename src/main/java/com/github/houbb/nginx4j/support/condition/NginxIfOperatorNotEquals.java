package com.github.houbb.nginx4j.support.condition;

import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

/**
 * 操作符
 *
 * @since 0.21.0
 */
public class NginxIfOperatorNotEquals implements NginxIfOperator {

    public boolean eval(String left, String right, NginxRequestDispatchContext dispatchContext) {
        NginxIfOperator equals = new NginxIfOperatorEquals();
        return !equals.eval(left, right, dispatchContext);
    }

    public String operator() {
        return "!=";
    }

}
