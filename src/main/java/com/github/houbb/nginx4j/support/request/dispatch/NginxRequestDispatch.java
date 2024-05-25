package com.github.houbb.nginx4j.support.request.dispatch;

import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.support.request.dto.NginxRequestInfoBo;

public interface NginxRequestDispatch {

    /**
     * 内容的分发处理
     * @param requestInfoBo 请求
     * @param nginxConfig 配置
     * @return 结果
     */
    String dispatch(final NginxRequestInfoBo requestInfoBo, final NginxConfig nginxConfig);

}
