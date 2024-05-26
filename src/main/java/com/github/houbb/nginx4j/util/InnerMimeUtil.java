package com.github.houbb.nginx4j.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @since 0.4.0
 */
public class InnerMimeUtil {

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
     * @param fileFullPath 文件
     * @return 结果
     */
    public static String getContentType(final String fileFullPath) {
        return getContentType(new File(fileFullPath));
    }

}
