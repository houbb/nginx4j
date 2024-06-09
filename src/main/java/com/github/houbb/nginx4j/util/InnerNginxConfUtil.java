package com.github.houbb.nginx4j.util;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.odiszapc.nginxparser.NgxConfig;
import com.github.odiszapc.nginxparser.NgxEntry;
import com.github.odiszapc.nginxparser.NgxParam;

import java.util.ArrayList;
import java.util.List;

public class InnerNginxConfUtil {

    public static List<NgxParam> findParams(final NgxConfig conf,
                                            final String paramName) {
        List<NgxParam> resultList = new ArrayList<>();

        List<NgxEntry> params = conf.findAll(NgxParam.class, "env");
        if (CollectionUtil.isNotEmpty(params)) {
            for (NgxEntry entry : params) {
                NgxParam ngxParam = (NgxParam) entry;
                resultList.add(ngxParam);
            }
        }

        return resultList;
    }

}
