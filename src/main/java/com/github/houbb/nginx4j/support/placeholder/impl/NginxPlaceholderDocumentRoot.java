package com.github.houbb.nginx4j.support.placeholder.impl;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.support.placeholder.AbstractNginxPlaceholder;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import com.github.houbb.nginx4j.util.InnerFileUtil;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 占位符处理类
 * @since 0.17.0
 *
 * @author 老马啸西风
 */
public class NginxPlaceholderDocumentRoot extends AbstractNginxPlaceholder {

    private static final Log logger = LogFactory.getLog(NginxPlaceholderDocumentRoot.class);


    @Override
    protected Object extract(FullHttpRequest request, NginxRequestDispatchContext context) {
        return InnerFileUtil.getRootPath();
    }

    @Override
    protected String getKey(FullHttpRequest request, NginxRequestDispatchContext context) {
        return "$document_root";
    }

}
