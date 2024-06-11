package com.github.houbb.nginx4j.config.param.impl.write;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.config.param.AbstractNginxParamLifecycleWrite;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;

import java.util.List;

/**
 * 参数处理类 响应头处理
 *
 * @since 0.19.0
 */
public class NginxParamHandleProxyHideHeader extends AbstractNginxParamLifecycleWrite {

    private static final Log logger = LogFactory.getLog(NginxParamHandleProxyHideHeader.class);

    @Override
    public void doBeforeWrite(NginxCommonConfigEntry configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context) {
        if(!(object instanceof HttpResponse)) {
            return;
        }

        HttpResponse httpResponse = (HttpResponse) object;

        List<String> values = configParam.getValues();

        // $ 占位符号后续处理
        String headerName = values.get(0);

        // 设置
        HttpHeaders headers = httpResponse.headers();
        headers.remove(headerName);
        logger.info(">>>>>>>>>>>> doBeforeWrite headers.remove({})", headerName);
    }

    @Override
    public void doAfterWrite(NginxCommonConfigEntry configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context) {

    }

    @Override
    protected String getKey(NginxCommonConfigEntry configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context) {
        return "proxy_hide_header";
    }

}
