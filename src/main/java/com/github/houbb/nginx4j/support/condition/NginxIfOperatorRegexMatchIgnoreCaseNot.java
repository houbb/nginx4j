package com.github.houbb.nginx4j.support.condition;

import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import com.github.houbb.nginx4j.util.InnerStringUtil;

/**
 * 操作符
 *
 * @since 0.21.0
 */
public class NginxIfOperatorRegexMatchIgnoreCaseNot implements NginxIfOperator {

    public boolean eval(String left, String right, NginxRequestDispatchContext dispatchContext) {
        NginxIfOperatorRegexMatchIgnoreCase ignoreCase = new NginxIfOperatorRegexMatchIgnoreCase();

        return !ignoreCase.eval(left, right, dispatchContext);
    }

    public String operator() {
        return "!~*";
    }

}
