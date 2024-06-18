package com.github.houbb.nginx4j.support.map;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxUserMapConfig;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

import java.util.List;
import java.util.Map;

/**
 * @since 0.22.0
 * @author 老马啸西风
 */
public class NginxMapDirectiveDefault implements NginxMapDirective {

    private static final Log logger = LogFactory.getLog(NginxMapDirectiveDefault.class);

    @Override
    public void map(NginxRequestDispatchContext context) {
        Map<String, Object> placeholderMap = context.getPlaceholderMap();
        List<NginxUserMapConfig> mapConfigList = context.getNginxConfig().getNginxUserConfig().getMapConfigs();
        if(CollectionUtil.isEmpty(mapConfigList)) {
            // 忽略
            logger.info("mapConfigList 为空，忽略处理 map 指令");
            return;
        }

        for(NginxUserMapConfig mapConfig : mapConfigList) {
            processMap(mapConfig, placeholderMap);
        }
    }

    protected void processMap(NginxUserMapConfig mapConfig,
                              Map<String, Object> placeholderMap) {
        //1. key
        String matchKey = mapConfig.getPlaceholderMatchKey();
        String matchValue = (String) placeholderMap.get(matchKey);

        String targetKey = mapConfig.getPlaceholderTargetKey();

        // 遍历
        for(Map.Entry<String, String> mapEntry : mapConfig.getMapping().entrySet()) {
            if(matchValue == null) {
                logger.info("matchValue is null, ignore match");
                break;
            }

            String key = mapEntry.getKey();
            String value = mapEntry.getValue();
            if(key.equals(matchValue)) {
                // fast-return
                placeholderMap.put(targetKey, value);
                logger.info("命中相等 {}={}, {}={}", matchKey, matchValue, targetKey, value);
                return;
            } else if(matchValue.matches(key)) {
                placeholderMap.put(targetKey, value);
                logger.info("命中正则 {}={}, {}={}", matchKey, matchValue, targetKey, value);
                return;
            }
        }

        // 默认值
        placeholderMap.put(targetKey, mapConfig.getDefaultVal());
        logger.info("命中默认值 {}={}", targetKey, mapConfig.getDefaultVal());
    }

}
