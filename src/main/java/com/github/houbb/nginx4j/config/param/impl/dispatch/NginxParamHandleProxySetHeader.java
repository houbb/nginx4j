package com.github.houbb.nginx4j.config.param.impl.dispatch;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.config.param.AbstractNginxParamLifecycleDispatch;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;

import java.util.List;

/**
 * 参数处理类 请求头处理
 *
 * @since 0.16.0
 */
public class NginxParamHandleProxySetHeader extends AbstractNginxParamLifecycleDispatch {

    private static final Log logger = LogFactory.getLog(NginxParamHandleProxySetHeader.class);

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
    public boolean doBeforeDispatch(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context) {
        List<String> values = configParam.getValues();

        // $ 占位符号后续处理

        String headerName = values.get(0);
        String headerValue = values.get(1);

        FullHttpRequest fullHttpRequest = context.getRequest();

        // 设置
        HttpHeaders headers = fullHttpRequest.headers();
        if (StringUtil.isEmpty(headerValue)) {
            headers.remove(headerName);
            logger.info(">>>>>>>>>>>> doBeforeDispatch headers.remove({})", headerName);
        } else {
            // 是否包含
            if (headers.contains(headerName)) {
                headers.set(headerName, headerValue);
                logger.info(">>>>>>>>>>>> doBeforeDispatch headers.set({}, {});", headerName, headerValue);
            } else {
                headers.add(headerName, headerValue);
                logger.info(">>>>>>>>>>>> doBeforeDispatch headers.set({}, {});", headerName, headerValue);
            }
        }

        return true;
    }

    @Override
    public boolean doAfterDispatch(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context) {
        return true;
    }

    @Override
    protected String getKey(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context) {
        return "proxy_set_header";
    }

    @Override
    public String directiveName() {
        return "proxy_set_header";
    }
}
