package com.github.houbb.nginx4j.support.placeholder;

import com.github.houbb.heaven.util.lang.StringUtil;
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
public abstract class AbstractNginxPlaceholderRequest implements INginxPlaceholder {

    private static final Log logger = LogFactory.getLog(AbstractNginxPlaceholderRequest.class);

    @Override
    public void beforeWrite(NginxRequestDispatchContext context) {
        String key = getKeyBeforeWrite(context);
        Object value = extractBeforeWrite(context);

        doForPlaceholderMap(context, key, value, "BeforeWrite");
    }

    /**
     * 提取值
     * @param context 上下文
     * @return 结果
     * @since 0.19.0
     */
    protected Object extractBeforeWrite(NginxRequestDispatchContext context) {
        return null;
    }

    /**
     * 唯一标识
     * @param context 上下文
     * @return 结果
     * @since 0.19.0
     */
    protected String getKeyBeforeWrite(NginxRequestDispatchContext context) {
        return null;
    }

    @Override
    public void beforeComplete(NginxRequestDispatchContext context) {
        String key = getKeyBeforeComplete(context);
        Object value = extractBeforeComplete(context);

        doForPlaceholderMap(context, key, value, "BeforeComplete");
    }

    /**
     * 提取值
     * @param context 上下文
     * @return 结果
     * @since 0.19.0
     */
    protected Object extractBeforeComplete(NginxRequestDispatchContext context) {
        return null;
    }

    /**
     * 唯一标识
     * @param context 上下文
     * @return 结果
     * @since 0.19.0
     */
    protected String getKeyBeforeComplete(NginxRequestDispatchContext context) {
        return null;
    }

    @Override
    public void beforeDispatch(NginxRequestDispatchContext context) {
        // 请求头
        FullHttpRequest request = context.getRequest();

        String key = getKeyBeforeDispatch(request, context);
        Object value = extractBeforeDispatch(request, context);

        doForPlaceholderMap(context, key, value, "BeforeDispatch");
    }

    protected void doForPlaceholderMap(NginxRequestDispatchContext context, String key, Object value, String lifeCycle) {
        if(StringUtil.isEmpty(key)) {
            return;
        }

        // 上下文存储的内容
        Map<String, Object> placeholderMap = context.getPlaceholderMap();
        placeholderMap.put(key, value);
        logger.info(lifeCycle + " placeholder put key={},value={}", key, value);
    }

    /**
     * 提取值
     * @param request 请求头
     * @param context 上下文
     * @return 结果
     */
    protected Object extractBeforeDispatch(FullHttpRequest request, NginxRequestDispatchContext context) {
        return null;
    }

    /**
     * 唯一标识
     * @param request 请求头
     * @param context 上下文
     * @return 结果
     */
    protected String getKeyBeforeDispatch(FullHttpRequest request, NginxRequestDispatchContext context) {
        return null;
    }

}
