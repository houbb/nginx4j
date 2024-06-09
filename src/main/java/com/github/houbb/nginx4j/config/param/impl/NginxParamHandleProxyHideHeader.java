package com.github.houbb.nginx4j.config.param.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxCommonConfigParam;
import com.github.houbb.nginx4j.config.param.AbstractNginxParamHandle;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;

import java.util.List;

/**
 * 参数处理类 响应头处理
 *
 * @since 0.16.0
 */
public class NginxParamHandleProxyHideHeader extends AbstractNginxParamHandle {

    private static final Log logger = LogFactory.getLog(NginxParamHandleProxyHideHeader.class);

    /**
     * # 增加或修改请求头
     * proxy_set_header X-Real-IP $remote_addr;
     * # 删除请求头
     * proxy_set_header X-Unwanted-Header "";
     *
     * @param configParam 参数
     * @param context     上下文
     */
    @Override
    public void doBeforeDispatch(NginxCommonConfigParam configParam, NginxRequestDispatchContext context) {

    }

    @Override
    public void doAfterDispatch(NginxCommonConfigParam configParam, NginxRequestDispatchContext context) {

    }

    @Override
    public void doBeforeWrite(NginxCommonConfigParam configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context) {
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
    public void doAfterWrite(NginxCommonConfigParam configParam, ChannelHandlerContext ctx, Object object, NginxRequestDispatchContext context) {

    }

    @Override
    protected String getKey(NginxCommonConfigParam configParam, NginxRequestDispatchContext context) {
        return "proxy_hide_header";
    }

}
