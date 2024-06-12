package com.github.houbb.nginx4j.support.condition;

import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作符
 *
 * @since 0.21.0
 */
public class NginxIfOperatorManager {

    private static final Map<String, NginxIfOperator> map = new HashMap<>();

    static {
        final NginxIfOperatorDefine operatorDefine = new NginxIfOperatorDefine();
        final NginxIfOperatorEquals operatorEquals = new NginxIfOperatorEquals();
        final NginxIfOperatorNotEquals operatorNotEquals = new NginxIfOperatorNotEquals();
        final NginxIfOperatorRegexMatch regexMatch = new NginxIfOperatorRegexMatch();
        final NginxIfOperatorRegexNotMatch regexNotMatch = new NginxIfOperatorRegexNotMatch();
        final NginxIfOperatorRegexMatchIgnoreCase regexMatchIgnoreCase = new NginxIfOperatorRegexMatchIgnoreCase();
        final NginxIfOperatorRegexMatchIgnoreCaseNot regexMatchIgnoreCaseNot = new NginxIfOperatorRegexMatchIgnoreCaseNot();

        map.put(operatorDefine.operator(), operatorDefine);
        map.put(operatorEquals.operator(), operatorEquals);
        map.put(operatorNotEquals.operator(), operatorNotEquals);
        map.put(regexMatch.operator(), regexMatch);
        map.put(regexNotMatch.operator(), regexNotMatch);
        map.put(regexMatchIgnoreCase.operator(), regexMatchIgnoreCase);
        map.put(regexMatchIgnoreCaseNot.operator(), regexMatchIgnoreCaseNot);
    }

    public boolean match(NginxCommonConfigEntry configParam, NginxRequestDispatchContext dispatchContext) {
        List<String> values = configParam.getValues();

        String key = getOperKey(configParam, dispatchContext);

        return map.get(key).eval(values.get(0), values.get(2), dispatchContext);
    }

    protected String getOperKey(NginxCommonConfigEntry configParam, NginxRequestDispatchContext dispatchContext) {
        List<String> values = configParam.getValues();

        if(values.size() == 1) {
            return "";
        }

        return values.get(1);
    }

}
