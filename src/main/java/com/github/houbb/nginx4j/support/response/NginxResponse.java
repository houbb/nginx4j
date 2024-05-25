package com.github.houbb.nginx4j.support.response;

import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.constant.HttpStatusEnum;
import com.github.houbb.nginx4j.support.request.dto.NginxRequestInfoBo;

public interface NginxResponse {


    String buildResp(final HttpStatusEnum httpStatusEnum,
                     final String content,
                     final NginxRequestInfoBo nginxRequestInfoBo,
                     final NginxConfig nginxConfig);


}
