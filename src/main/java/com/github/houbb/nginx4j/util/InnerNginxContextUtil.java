package com.github.houbb.nginx4j.util;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.config.NginxUserConfig;
import com.github.houbb.nginx4j.config.NginxUserHttpConfig;
import com.github.houbb.nginx4j.config.NginxUserServerLocationConfig;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 上下文工具类
 *
 * @since 0.25.0
 */
public class InnerNginxContextUtil {

    public static List<NginxCommonConfigEntry> getAllDirectives(final NginxRequestDispatchContext context) {
        //TODO: 这里可以统一优化，处理前设置一次即可。暂时不懂
        List<NginxCommonConfigEntry> resultList = new ArrayList<>();

        NginxUserConfig nginxUserConfig = context.getNginxConfig().getNginxUserConfig();

        // 基础
        if(CollectionUtil.isNotEmpty((nginxUserConfig.getConfigEntryList()))) {
            resultList.addAll(nginxUserConfig.getConfigEntryList());
        }

        // http
        NginxUserHttpConfig nginxUserHttpConfig = nginxUserConfig.getHttpConfig();
        List<NginxCommonConfigEntry> httpConfigEntryList = nginxUserHttpConfig.getConfigEntryList();
        if(CollectionUtil.isNotEmpty(httpConfigEntryList)) {
            resultList.addAll(httpConfigEntryList);
        }

        //server
        if(CollectionUtil.isNotEmpty((context.getCurrentNginxUserServerConfig().getConfigEntryList()))) {
            resultList.addAll(context.getCurrentNginxUserServerConfig().getConfigEntryList());
        }


        //location
        NginxUserServerLocationConfig locationConfig = context.getCurrentUserServerLocationConfig();
        List<NginxCommonConfigEntry> directives = locationConfig.getConfigEntryList();
        if(CollectionUtil.isNotEmpty(directives)) {
            resultList.addAll(directives);
        }

        return resultList;
    }


    /**
     * 获取当前 location 的指令
     *
     * @param context 上下文
     * @return 结果
     * @since 0.25.0
     */
    public static List<NginxCommonConfigEntry> getLocationDirectives(final NginxRequestDispatchContext context) {
        List<NginxCommonConfigEntry> resultList = new ArrayList<>();

        //location
        NginxUserServerLocationConfig locationConfig = context.getCurrentUserServerLocationConfig();
        List<NginxCommonConfigEntry> directives = locationConfig.getConfigEntryList();
        if(CollectionUtil.isNotEmpty(directives)) {
            resultList.addAll(directives);
        }

        return resultList;
    }

    /**
     * 获取当前 location 的指令, 按照 name 分组
     *
     * @param directives 指令列表
     * @return 结果
     * @since 0.25.0
     */
    public static Map<String, List<NginxCommonConfigEntry>> getLocationDirectiveMap(final List<NginxCommonConfigEntry> directives) {

        Map<String, List<NginxCommonConfigEntry>> map = new HashMap<>();

        if(CollectionUtil.isNotEmpty(directives)) {
            for(NginxCommonConfigEntry entry : directives) {
                final String name = entry.getName();
                List<NginxCommonConfigEntry> list = map.getOrDefault(name, new ArrayList<>());
                list.add(entry);
                map.put(name, list);
            }
        }

        return map;
    }

}
