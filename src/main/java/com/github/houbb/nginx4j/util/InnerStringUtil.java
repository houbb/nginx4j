package com.github.houbb.nginx4j.util;

import com.github.houbb.heaven.annotation.CommonEager;
import com.github.houbb.heaven.util.lang.StringUtil;

@CommonEager
public class InnerStringUtil {

    public static String trim(String input) {
        if(StringUtil.isEmpty(input)) {
            return input;
        }

        if (input.startsWith("\"") && input.endsWith("\"")) {
            return input.substring(1, input.length() - 1);
        }
        return input;
    }

}
