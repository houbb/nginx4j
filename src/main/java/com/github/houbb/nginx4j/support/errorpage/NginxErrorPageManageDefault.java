package com.github.houbb.nginx4j.support.errorpage;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @since 0.25.0
 * @author 老马啸西风
 */
public class NginxErrorPageManageDefault implements INginxErrorPageManage {

    private static final Log logger = LogFactory.getLog(NginxErrorPageManageDefault.class);

    private final Map<String, String> map = new HashMap<>();

    @Override
    public void register(String code, String htmlPath) {
        map.put(code, htmlPath);
        logger.info("error_page register code={}, path={}", code, htmlPath);
    }

    @Override
    public String getPath(String code) {
        String path = map.get(code);

        logger.info("error_page register code={}, path={}", code, path);
        return path;
    }

}
