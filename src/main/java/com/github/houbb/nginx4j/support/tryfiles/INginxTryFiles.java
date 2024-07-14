package com.github.houbb.nginx4j.support.tryfiles;

import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * try_files 指令处理
 *
 * @since 0.26.0
 */
public interface INginxTryFiles {

    /**
     * 处理 try_files 指令
     * @param request 请求
     * @param nginxConfig 配置
     * @param context 上下文
     */
    void tryFiles(FullHttpRequest request,
                    final NginxConfig nginxConfig,
                    NginxRequestDispatchContext context);

}
