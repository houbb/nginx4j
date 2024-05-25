package com.github.houbb.nginx4j.support.request.dispatch;

import com.github.houbb.nginx4j.config.NginxConfig;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public interface NginxRequestDispatch {

    /**
     * 内容的分发处理
     * @param httpRequest 请求
     * @param nginxConfig 配置
     * @return 结果
     */
    FullHttpResponse dispatch(final FullHttpRequest httpRequest, final NginxConfig nginxConfig);

}
