package com.github.houbb.nginx4j.support.condition;

import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

/**
 * 操作符
 *
 * @since 0.21.0
 */
public class NginxIfOperatorEquals implements NginxIfOperator {

    public boolean eval(String left, String right, NginxRequestDispatchContext dispatchContext) {
        if(left == null || right == null) {
            return false;
        }

        return left.equals(right);
    }

    public String operator() {
        return "=";
    }

}
