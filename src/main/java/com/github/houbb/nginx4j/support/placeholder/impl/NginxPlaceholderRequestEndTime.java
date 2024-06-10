package com.github.houbb.nginx4j.support.placeholder.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.support.placeholder.AbstractNginxPlaceholderRequest;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 占位符处理类
 * @since 0.17.0
 *
 * @author 老马啸西风
 */
public class NginxPlaceholderRequestEndTime extends AbstractNginxPlaceholderRequest {

    private static final Log logger = LogFactory.getLog(NginxPlaceholderRequestEndTime.class);


    @Override
    protected Object extractBeforeComplete(NginxRequestDispatchContext context) {
        long time = System.currentTimeMillis();
        context.setRequestEndTime(time);
        return time;
    }

    @Override
    protected String getKeyBeforeComplete(NginxRequestDispatchContext context) {
        return "$request_end_time";
    }

}
