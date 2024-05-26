package com.github.houbb.nginx4j.util;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.support.request.dispatch.http.AbstractNginxRequestDispatchFullResp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @since 0.4.0
 */
public class InnerMimeUtil {

    private static final Log logger = LogFactory.getLog(InnerMimeUtil.class);

    /**
     * 获取文件内容类别
     * @param file 文件
     * @return 结果
     */
    public static String getContentType(File file) {
        try {
            return Files.probeContentType(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取文件内容类别
     * @param file 文件
     * @param charset 编码
     * @return 结果
     */
    public static String getContentTypeWithCharset(File file, String charset) {
        try {
            String contentType =  Files.probeContentType(file.toPath()) + "; charset=" + charset;
            logger.info("file={}, contentType={}", file.getAbsolutePath(), contentType);
            return contentType;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取文件内容类别
     * @param fileFullPath 文件
     * @return 结果
     */
    public static String getContentType(final String fileFullPath) {
        return getContentType(new File(fileFullPath));
    }

}
