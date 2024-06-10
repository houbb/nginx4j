package com.github.houbb.nginx4j.support.placeholder;

import com.github.houbb.nginx4j.support.placeholder.impl.*;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认的处理逻辑
 *
 * @since 0.17.0
 */
public class NginxPlaceholderManagerDefault implements INginxPlaceholderManager {

    private static final List<INginxPlaceholder> PLACEHOLDERS = new ArrayList<>();


    public NginxPlaceholderManagerDefault() {
        initPlaceholderList();

    }

    protected void initPlaceholderList() {
        // 顺序暂时通过指定的顺序来处理

        PLACEHOLDERS.add(new NginxPlaceholderRequestStartTime());

        PLACEHOLDERS.add(new NginxPlaceholderArgs());
        PLACEHOLDERS.add(new NginxPlaceholderIsArgs());
        PLACEHOLDERS.add(new NginxPlaceholderQueryString());
        PLACEHOLDERS.add(new NginxPlaceholderConnection());
        PLACEHOLDERS.add(new NginxPlaceholderConnectionRequests());
        PLACEHOLDERS.add(new NginxPlaceholderContentLength());
        PLACEHOLDERS.add(new NginxPlaceholderContentType());
        PLACEHOLDERS.add(new NginxPlaceholderDocumentRoot());
        PLACEHOLDERS.add(new NginxPlaceholderDocumentUri());
        PLACEHOLDERS.add(new NginxPlaceholderHost());
        PLACEHOLDERS.add(new NginxPlaceholderHttpCookie());
        PLACEHOLDERS.add(new NginxPlaceholderHttpReferer());
        PLACEHOLDERS.add(new NginxPlaceholderRemoteAddress());
        PLACEHOLDERS.add(new NginxPlaceholderRemotePort());
        PLACEHOLDERS.add(new NginxPlaceholderRemoteUser());
        PLACEHOLDERS.add(new NginxPlaceholderRequest());
        PLACEHOLDERS.add(new NginxPlaceholderRequestMethod());
        PLACEHOLDERS.add(new NginxPlaceholderRequestUri());
        PLACEHOLDERS.add(new NginxPlaceholderSchema());
        PLACEHOLDERS.add(new NginxPlaceholderServerHostname());
        PLACEHOLDERS.add(new NginxPlaceholderServerAddress());
        PLACEHOLDERS.add(new NginxPlaceholderServerName());
        PLACEHOLDERS.add(new NginxPlaceholderServerPort());
        PLACEHOLDERS.add(new NginxPlaceholderServerProtocol());
        PLACEHOLDERS.add(new NginxPlaceholderUri());
        PLACEHOLDERS.add(new NginxPlaceholderXForwardedFor());
        PLACEHOLDERS.add(new NginxPlaceholderHttpUserAgent());


        // headers
        PLACEHOLDERS.add(new NginxPlaceholderResponseHeaders());
        PLACEHOLDERS.add(new NginxPlaceholderResponseBodyBytesSent());
        PLACEHOLDERS.add(new NginxPlaceholderResponseStatus());

        // complete

        PLACEHOLDERS.add(new NginxPlaceholderRequestEndTime());
        PLACEHOLDERS.add(new NginxPlaceholderRequestTime());
    }

    @Override
    public void beforeDispatch(NginxRequestDispatchContext context) {
        for(INginxPlaceholder placeholder : PLACEHOLDERS) {
            placeholder.beforeDispatch(context);
        }
    }

    @Override
    public void beforeWrite(NginxRequestDispatchContext context) {
        for(INginxPlaceholder placeholder : PLACEHOLDERS) {
            placeholder.beforeWrite(context);
        }
    }

    @Override
    public void beforeComplete(NginxRequestDispatchContext context) {
        for(INginxPlaceholder placeholder : PLACEHOLDERS) {
            placeholder.beforeComplete(context);
        }
    }

    /**
     * 获取值
     *
     * @param context 上下文
     * @param key     key
     * @return 结果
     */
    public Object getValue(NginxRequestDispatchContext context, final String key) {
        return context.getPlaceholderMap().get(key);
    }

}
