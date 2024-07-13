package com.github.houbb.nginx4j.util;

import com.github.houbb.heaven.annotation.CommonEager;
import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.heaven.util.io.ResourceUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;
import com.github.houbb.nginx4j.support.index.NginxIndexFileDefault;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

import java.io.File;

/**
 * 内部文件工具类
 *
 * @since 0.17.0
 */
@CommonEager
public class InnerFileUtil {

    public static String getRootPath() {
        String resource = ResourceUtil.getClassResource(InnerFileUtil.class);
        int index = resource.indexOf("/target/classes") + "/target/classes/".length();
        return resource.substring(0, index);
    }

    public static File getTargetFile(final String requestUri, final NginxRequestDispatchContext context) {
        final NginxUserServerConfig nginxUserServerConfig = context.getCurrentNginxUserServerConfig();
        boolean isRootPath = isRootPath(requestUri, context);

        final NginxConfig nginxConfig = context.getNginxConfig();

        // 根路径
        if(isRootPath) {
            return nginxConfig.getNginxIndexFile().getIndexFile(context);
        }

        final String basicPath = nginxUserServerConfig.getRoot();

        // other
        String fullPath = FileUtil.buildFullPath(basicPath, requestUri);
        return new File(fullPath);
    }

    public static boolean isRootPath(final String path, final NginxRequestDispatchContext context) {
        //root path
        if(StringUtil.isEmpty(path) || "/".equals(path)) {
            return true;
        }
        return false;
    }

}
