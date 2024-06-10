package com.github.houbb.nginx4j.support.placeholder.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.support.placeholder.AbstractNginxPlaceholderRequest;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelId;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 连接的序列号。
 *
 * @author 老马啸西风
 * @since 0.19.0
 */
public class NginxPlaceholderConnectionRequests extends AbstractNginxPlaceholderRequest {

    private static final Log logger = LogFactory.getLog(NginxPlaceholderConnectionRequests.class);


    @Override
    protected Object extractBeforeDispatch(FullHttpRequest request, NginxRequestDispatchContext context) {
        // 获取并增加 $connection_requests 的值
        ChannelId channelId = context.getCtx().channel().id();
        final Map<ChannelId, AtomicInteger> countMap = context.getConnectionRequestCount();
        AtomicInteger requestCount = countMap.getOrDefault(channelId, new AtomicInteger(0));
        int connectionRequests = requestCount.incrementAndGet();

        // set
        countMap.put(channelId, requestCount);

        return connectionRequests;
    }

    @Override
    protected String getKeyBeforeDispatch(FullHttpRequest request, NginxRequestDispatchContext context) {
        return "$connection_requests";
    }

}
