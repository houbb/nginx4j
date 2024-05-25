package com.github.houbb.nginx4j.util;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;

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
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        return mimeTypesMap.getContentType(file);
    }

    /**
     * 获取文件内容类别
     * @param fileFullPath 文件
     * @return 结果
     */
    public static String getContentType(final String fileFullPath) {
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        return mimeTypesMap.getContentType(fileFullPath);
    }

}
