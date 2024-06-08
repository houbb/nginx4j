package com.github.houbb.nginx4j.config.load;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.nginx4j.bs.NginxUserConfigBs;
import com.github.houbb.nginx4j.bs.NginxUserServerConfigBs;
import com.github.houbb.nginx4j.config.NginxUserConfigParam;
import com.github.houbb.nginx4j.config.NginxUserConfig;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;
import com.github.houbb.nginx4j.config.NginxUserServerLocationConfig;
import com.github.houbb.nginx4j.constant.NginxLocationPathTypeEnum;
import com.github.houbb.nginx4j.constant.NginxUserConfigDefaultConst;
import com.github.houbb.nginx4j.constant.NginxUserServerConfigDefaultConst;
import com.github.odiszapc.nginxparser.NgxBlock;
import com.github.odiszapc.nginxparser.NgxConfig;
import com.github.odiszapc.nginxparser.NgxEntry;
import com.github.odiszapc.nginxparser.NgxParam;

import java.io.IOException;
import java.util.*;

/**
 * @since 0.13.0
 */
public  class NginxUserConfigLoaderConfigFile extends AbstractNginxUserConfigLoader {

    private final String filePath;

    public NginxUserConfigLoaderConfigFile(String filePath) {
        this.filePath = filePath;
    }


    protected void fillBasicInfo(final NginxUserConfigBs configBs,
                                 final NgxConfig conf) {
        // 基本信息
        configBs.httpPid(getHttpPid(conf));
    }

    private String getHttpPid(final NgxConfig conf) {
        // 基本信息
        NgxParam pidParam = conf.findParam("pid");
        if(pidParam != null) {
            return pidParam.getValue();
        }


        return NginxUserConfigDefaultConst.HTTP_PID;
    }

    /**
     * <pre>
     *         listen 80;  # 监听80端口
     *         server_name example.com;  # 服务器域名
     *
     *         # 单独为这个 server 启用 sendfile
     *         sendfile on;
     *
     *         # 静态文件的根目录
     *         root /usr/share/nginx/html;  # 静态文件存放的根目录
     *         index index.html index.htm;  # 默认首页
     *
     *         # 如果需要为这个 server 单独配置 gzip，可以覆盖全局配置
     *         gzip on;
     *         gzip_disable "msie6";
     *         gzip_vary on;
     *         gzip_proxied any;
     *         gzip_comp_level 6;
     *         gzip_buffers 16 8k;
     *         gzip_http_version 1.1;
     *         gzip_types text/plain text/css application/json application/javascript text/xml application/xml application/xml+rss text/javascript;
     *
     * </pre>
     * @param configBs 配置
     * @param conf 文件信息
     */
    protected void fillServerInfo(final NginxUserConfigBs configBs,
                                 final NgxConfig conf) {
        // 首先获取 block
        List<NgxEntry> servers = conf.findAll(NgxConfig.BLOCK, "http", "server"); // 示例3
        if(CollectionUtil.isNotEmpty(servers)) {
            for (NgxEntry entry : servers) {
                NginxUserServerConfigBs serverConfigBs = NginxUserServerConfigBs.newInstance();
                NgxBlock serverBlock = (NgxBlock) entry;
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

                NginxUserServerConfig serverConfig = serverConfigBs.build();
                configBs.addServerConfig(serverConfig);
            }
        }
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
                NginxUserServerLocationConfig locationConfig = new NginxUserServerLocationConfig();
                locationConfig.setName(ngxBlock.getName());
                locationConfig.setValue(ngxBlock.getValue());
                locationConfig.setValues(ngxBlock.getValues());

                NginxLocationPathTypeEnum typeEnum = NginxLocationPathTypeEnum.getTypeEnum(locationConfig);
                locationConfig.setTypeEnum(typeEnum);

                // 参数
                List<NginxUserConfigParam> paramList = new ArrayList<>();
                List<NgxEntry> ngxEntries = ngxBlock.findAll(NgxParam.class);
                if(CollectionUtil.isNotEmpty(ngxEntries)) {
                    for(NgxEntry ngxEntry : ngxEntries) {
                        NgxParam ngxParam = (NgxParam) ngxEntry;
                        String name = ngxParam.getName();
                        List<String> values = ngxParam.getValues();
                        String value = ngxParam.getValue();

                        NginxUserConfigParam nginxUserConfigParam = new NginxUserConfigParam();
                        nginxUserConfigParam.setName(name);
                        nginxUserConfigParam.setValue(value);
                        nginxUserConfigParam.setValues(values);


                        paramList.add(nginxUserConfigParam);
                    }
                }
                locationConfig.setDirectives(paramList);

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

    @Override
    protected NginxUserConfig doLoad() {
        NgxConfig conf = null;
        try {
            NginxUserConfigBs configBs = NginxUserConfigBs.newInstance();
            conf = NgxConfig.read(filePath);

            //1. basic
            fillBasicInfo(configBs, conf);

            //2. server 信息
            fillServerInfo(configBs, conf);

            // 返回
            return configBs.build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
