package com.github.houbb.nginx4j.config.load.component.impl;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.nginx4j.bs.NginxUserServerConfigBs;
import com.github.houbb.nginx4j.config.NginxCommonConfigParam;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;
import com.github.houbb.nginx4j.config.NginxUserServerLocationConfig;
import com.github.houbb.nginx4j.config.load.component.INginxUserServerConfigLoad;
import com.github.houbb.nginx4j.config.load.component.INginxUserServerLocationConfigLoad;
import com.github.houbb.nginx4j.constant.NginxLocationPathTypeEnum;
import com.github.houbb.nginx4j.constant.NginxUserServerConfigDefaultConst;
import com.github.odiszapc.nginxparser.NgxBlock;
import com.github.odiszapc.nginxparser.NgxConfig;
import com.github.odiszapc.nginxparser.NgxEntry;
import com.github.odiszapc.nginxparser.NgxParam;

import java.util.*;

/**
 * @since 0.18.0
 */
public class NginxUserServerConfigLoadFile implements INginxUserServerConfigLoad {

    private final NgxConfig conf;

    private final NgxBlock serverBlock;


    public NginxUserServerConfigLoadFile(NgxConfig conf, NgxBlock serverBlock) {
        this.conf = conf;
        this.serverBlock = serverBlock;
    }

    @Override
    public NginxUserServerConfig load() {
        NginxUserServerConfigBs serverConfigBs = NginxUserServerConfigBs.newInstance();
        String name = serverBlock.getName();

        int httpServerPort = getHttpServerListen(conf, serverBlock);
        String httpServerName = getHttpServerName(conf, serverBlock);
        String httpServerRoot = getHttpServerRoot(conf, serverBlock);
        List<String> httpIndexList = getHttpServerIndexList(conf, serverBlock);

        // sendfile on;
        String sendFile = getHttpServerSendFile(conf, serverBlock);

        //gzip
        String gzip = getHttpServerGzip(conf, serverBlock);
        long gzipMinLen = getHttpServerGzipMinLen(conf, serverBlock);
        List<String> gzipTypes = getHttpServerGzipTypes(conf, serverBlock);

        // 添加 location
        List<NginxUserServerLocationConfig> locationConfigList = getHttpServerLocationList(conf, serverBlock);
        NginxUserServerLocationConfig defaultLocationConfig = getDefaultLocationConfig(locationConfigList);

        serverConfigBs.httpServerName(httpServerName)
                .httpServerListen(httpServerPort)
                .httpServerRoot(httpServerRoot)
                .httpServerIndexList(httpIndexList)
                .sendFile(sendFile)
                .gzip(gzip)
                .gzipMinLength(gzipMinLen)
                .gzipTypes(gzipTypes)
                .locationConfigList(locationConfigList)
                .defaultLocationConfig(defaultLocationConfig);

        return serverConfigBs.build();
    }

    public NginxUserServerLocationConfig getDefaultLocationConfig(List<NginxUserServerLocationConfig> locationConfigList) {
        if(CollectionUtil.isNotEmpty(locationConfigList)) {
            for(NginxUserServerLocationConfig config : locationConfigList) {
                if(NginxLocationPathTypeEnum.DEFAULT.equals(config.getTypeEnum())) {
                    return config;
                }
            }
        }

        //TODO: 全局的默认值？
        return null;
    }

    private List<String> getHttpServerGzipTypes(final NgxConfig conf, final NgxBlock serverBlock) {
        // value
        NgxParam param = serverBlock.findParam("gzip_types");
        if(param != null) {
            return StringUtil.splitToList(param.getValue(), " ");
        }

        // http 默认
        NgxParam httpParam = conf.findParam("gzip_types");
        if(httpParam != null) {
            return StringUtil.splitToList(httpParam.getValue(), " ");
        }

        return NginxUserServerConfigDefaultConst.gzipTypes;
    }

    private long getHttpServerGzipMinLen(final NgxConfig conf, final NgxBlock serverBlock) {
        // value
        NgxParam param = serverBlock.findParam("gzip_min_len");
        if(param != null) {
            return Long.parseLong(param.getValue());
        }

        // http 默认
        NgxParam httpParam = conf.findParam("gzip_min_len");
        if(httpParam != null) {
            return Long.parseLong(httpParam.getValue());
        }

        return NginxUserServerConfigDefaultConst.gzipMinLength;
    }

    private String getHttpServerGzip(final NgxConfig conf, final NgxBlock serverBlock) {
        // value
        NgxParam param = serverBlock.findParam("gzip");
        if(param != null) {
            return param.getValue();
        }

        // http 默认
        NgxParam httpParam = conf.findParam("gzip");
        if(httpParam != null) {
            return httpParam.getValue();
        }

        return NginxUserServerConfigDefaultConst.gzip;
    }

    private List<String> getHttpServerIndexList(final NgxConfig conf, final NgxBlock serverBlock) {
        // value
        NgxParam param = serverBlock.findParam("index");
        if(param != null) {
            return StringUtil.splitToList(param.getValue(), " ");
        }

        // http 默认
        NgxParam httpParam = conf.findParam("index");
        if(httpParam != null) {
            return StringUtil.splitToList(httpParam.getValue(), " ");
        }

        return NginxUserServerConfigDefaultConst.httpServerIndexList;
    }

    private String getHttpServerSendFile(final NgxConfig conf, final NgxBlock serverBlock) {
        // value
        NgxParam param = serverBlock.findParam("sendfile");
        if(param != null) {
            return param.getValue();
        }

        // http 默认
        NgxParam httpParam = conf.findParam("sendfile");
        if(httpParam != null) {
            return httpParam.getValue();
        }

        return NginxUserServerConfigDefaultConst.sendFile;
    }

    private String getHttpServerRoot(final NgxConfig conf, final NgxBlock serverBlock) {
        // value
        NgxParam param = serverBlock.findParam("root");
        if(param != null) {
            return param.getValue();
        }

        // http 默认
        NgxParam httpParam = conf.findParam("root");
        if(httpParam != null) {
            return httpParam.getValue();
        }

        return NginxUserServerConfigDefaultConst.httpServerRoot;
    }

    private int getHttpServerListen(final NgxConfig conf, final NgxBlock serverBlock) {
        // value
        NgxParam listenParam = serverBlock.findParam("listen");
        if(listenParam != null) {
            String value = listenParam.getValue();
            List<String> valueList = StringUtil.splitToList(value, " ");
            return Integer.parseInt(valueList.get(0));
        }

        // http 默认
        NgxParam httpParam = conf.findParam("listen");
        if(httpParam != null) {
            String value = httpParam.getValue();
            List<String> valueList = StringUtil.splitToList(value, " ");
            return Integer.parseInt(valueList.get(0));
        }

        return NginxUserServerConfigDefaultConst.httpServerListen;
    }

    private String getHttpServerName(final NgxConfig conf, final NgxBlock serverBlock) {
        // value
        NgxParam param = serverBlock.findParam("server_name");
        if(param != null) {
            return param.getValue();
        }

        // http 默认
        NgxParam httpParam = conf.findParam("server_name");
        if(httpParam != null) {
            return httpParam.getValue();
        }

        return NginxUserServerConfigDefaultConst.httpServerName;
    }

    private List<NginxUserServerLocationConfig> getHttpServerLocationList(final NgxConfig conf, final NgxBlock serverBlock) {
        List<NginxUserServerLocationConfig> resultList = new ArrayList<>();
        // value
        List<NgxEntry> entryList = serverBlock.findAll(NgxBlock.class, "location");
        if(CollectionUtil.isNotEmpty(entryList)) {
            for(NgxEntry entry : entryList) {
                NgxBlock ngxBlock = (NgxBlock) entry;
                // 参数

                // location 的处理
                final INginxUserServerLocationConfigLoad locationConfigLoad = new NginxUserServerLocationConfigLoadFile(conf, ngxBlock);
                NginxUserServerLocationConfig locationConfig = locationConfigLoad.load();
                resultList.add(locationConfig);
            }
        }

        // 排序。按照匹配的优先级，从高到底排序
        if(CollectionUtil.isNotEmpty(resultList)) {
            Collections.sort(resultList, new Comparator<NginxUserServerLocationConfig>() {
                @Override
                public int compare(NginxUserServerLocationConfig o1, NginxUserServerLocationConfig o2) {
                    return o1.getTypeEnum().getOrder() - o2.getTypeEnum().getOrder();
                }
            });
        }

        return resultList;
    }

}