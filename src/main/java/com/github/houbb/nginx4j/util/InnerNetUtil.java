package com.github.houbb.nginx4j.util;

import com.github.houbb.heaven.util.net.NetUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;

public class InnerNetUtil {

    private static final Log log = LogFactory.getLog(InnerNetUtil.class);

    private static String host;

    static {
        try {
            host = NetUtil.getLocalHost();
        } catch (Exception e) {
            host = "127.0.0.1";

            log.warn("[Nginx4j] get host meet ex, default to localhost={}", host);
        }
    }

    public static String getHost() {
        return host;
    }

}
