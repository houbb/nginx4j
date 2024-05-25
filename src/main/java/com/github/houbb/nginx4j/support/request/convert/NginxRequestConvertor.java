package com.github.houbb.nginx4j.support.request.convert;

import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.support.request.dto.NginxRequestInfoBo;

/**
 * 请求信息处理类
 */
public interface NginxRequestConvertor {

    NginxRequestInfoBo convert(final String requestString, NginxConfig nginxConfig);

}
