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
        PLACEHOLDERS.add(new NginxPlaceholderArgs());
        PLACEHOLDERS.add(new NginxPlaceholderContentLength());
        PLACEHOLDERS.add(new NginxPlaceholderContentType());
        PLACEHOLDERS.add(new NginxPlaceholderDocumentRoot());
        PLACEHOLDERS.add(new NginxPlaceholderHost());
        PLACEHOLDERS.add(new NginxPlaceholderHttpCookie());
        PLACEHOLDERS.add(new NginxPlaceholderRemoteAddress());
        PLACEHOLDERS.add(new NginxPlaceholderRemotePort());
        PLACEHOLDERS.add(new NginxPlaceholderRequestMethod());
        PLACEHOLDERS.add(new NginxPlaceholderRequestUri());
        PLACEHOLDERS.add(new NginxPlaceholderSchema());
        PLACEHOLDERS.add(new NginxPlaceholderServerAddress());
        PLACEHOLDERS.add(new NginxPlaceholderServerName());
        PLACEHOLDERS.add(new NginxPlaceholderServerPort());
        PLACEHOLDERS.add(new NginxPlaceholderServerProtocol());
        PLACEHOLDERS.add(new NginxPlaceholderUri());
        PLACEHOLDERS.add(new NginxPlaceholderUserAgent());
    }

    @Override
    public void init(NginxRequestDispatchContext context) {
        for(INginxPlaceholder placeholder : PLACEHOLDERS) {
            placeholder.placeholder(context);
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
