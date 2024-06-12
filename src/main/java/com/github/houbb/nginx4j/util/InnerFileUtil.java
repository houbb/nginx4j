package com.github.houbb.nginx4j.util;

import com.github.houbb.heaven.annotation.CommonEager;
import com.github.houbb.heaven.util.io.ResourceUtil;
import com.github.houbb.nginx4j.support.index.NginxIndexFileDefault;

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

}
