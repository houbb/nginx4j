package com.github.houbb.nginx4j.support.request.dispatch.http;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.handler.codec.http.*;

/**
 * 文件范围查询
 *
 * @since 0.7.0
 * @author 老马啸西风
 */
public class NginxRequestDispatchFileRange extends AbstractNginxRequestDispatchFile {

    private static final Log logger = LogFactory.getLog(AbstractNginxRequestDispatchFullResp.class);

    @Override
    protected HttpResponse buildHttpResponse(NginxRequestDispatchContext context) {
        long start = context.getActualStart();

        // 构造HTTP响应
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1,
                start < 0 ? HttpResponseStatus.OK : HttpResponseStatus.PARTIAL_CONTENT);

        return response;
    }

    @Override
    protected void fillContext(NginxRequestDispatchContext context) {
        final long fileLength = context.getFile().length();
        final HttpRequest httpRequest = context.getRequest();

        // 解析Range头
        String rangeHeader = httpRequest.headers().get("Range");
        logger.info("[Nginx] fileRange start rangeHeader={}", rangeHeader);

        long[] range = parseRange(rangeHeader, fileLength);
        long start = range[0];
        long end = range[1];
        long actualLength = end - start + 1;

        context.setActualStart(start);
        context.setActualFileLength(actualLength);
    }

    protected long[] parseRange(String rangeHeader, long totalLength) {
        // 简单解析Range头，返回[start, end]
        // Range头格式为: "bytes=startIndex-endIndex"
        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            String range = rangeHeader.substring("bytes=".length());
            String[] parts = range.split("-");
            long start = parts[0].isEmpty() ? totalLength - 1 : Long.parseLong(parts[0]);
            long end = parts.length > 1 ? Long.parseLong(parts[1]) : totalLength - 1;
            return new long[]{start, end};
        }
        return new long[]{-1, -1}; // 表示无效的范围请求
    }

}
