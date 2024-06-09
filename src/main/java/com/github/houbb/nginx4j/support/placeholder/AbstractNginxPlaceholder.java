package com.github.houbb.nginx4j.support.placeholder;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.Map;

/**
 * 占位符处理类
 * @since 0.17.0
 *
 * @author 老马啸西风
 */
public abstract class AbstractNginxPlaceholder implements INginxPlaceholder {

    private static final Log logger = LogFactory.getLog(AbstractNginxPlaceholder.class);

    @Override
    public void placeholder(NginxRequestDispatchContext context) {
        // 上下文存储的内容
        Map<String, Object> placeholderMap = context.getPlaceholderMap();

        // 请求头
        FullHttpRequest request = context.getRequest();

        String key = getKey(request, context);
        Object value = extract(request, context);

        placeholderMap.put(key, value);

        logger.debug("placeholder put key={},value={}", key, value);
    }

    /**
     * 提取值
     * @param request 请求头
     * @param context 上下文
     * @return 结果
     */
    protected abstract Object extract(FullHttpRequest request, NginxRequestDispatchContext context);

    /**
     * 唯一标识
     * @param request 请求头
     * @param context 上下文
     * @return 结果
     */
    protected abstract String getKey(FullHttpRequest request, NginxRequestDispatchContext context);

}
