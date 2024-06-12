package com.github.houbb.nginx4j.support.condition;

import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import com.github.houbb.nginx4j.util.InnerStringUtil;

/**
 * 操作符
 *
 * @since 0.21.0
 */
public class NginxIfOperatorRegexMatchIgnoreCase implements NginxIfOperator {

    public boolean eval(String left, String right, NginxRequestDispatchContext dispatchContext) {
        if(left == null || right == null) {
            return false;
        }

        String trimRight = "(?i)" + InnerStringUtil.trim(right);
        return left.matches(trimRight);
    }

    public String operator() {
        return "~*";
    }

}
