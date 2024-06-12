package com.github.houbb.nginx4j.support.condition;

import com.github.houbb.nginx4j.constant.NginxConst;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

import java.util.Map;

/**
 * 操作符
 *
 * @since 0.21.0
 */
public class NginxIfOperatorDefine implements NginxIfOperator {

    public boolean eval(String left, String right, NginxRequestDispatchContext dispatchContext) {
        final Map<String, Object> placeholderMap = dispatchContext.getPlaceholderMap();

        String value = left.toString();
        if(!value.startsWith(NginxConst.PLACEHOLDER_PREFIX)) {
            throw new Nginx4jException("IF 判断属性是否存在，必须 $ 开始");
        }

        // 变量值
        return placeholderMap.containsKey(value);
    }

    public String operator() {
        return "default";
    }

}
