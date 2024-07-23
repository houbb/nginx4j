package com.github.houbb.nginx4j.support.request.dispatch.http;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import com.github.houbb.nginx4j.support.returns.NginxReturnResult;
import com.github.houbb.nginx4j.util.InnerRespUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 *
 * @since 0.27.0
 */
public class NginxRequestDispatchProxyPass extends AbstractNginxRequestDispatch {

    private static final Log logger = LogFactory.getLog(NginxRequestDispatchProxyPass.class);

    @Override
    public void doDispatch(NginxRequestDispatchContext context) {
        //TODO: 通过 http-client 实现？还是直接根据 netty 来实现？
    }

}
