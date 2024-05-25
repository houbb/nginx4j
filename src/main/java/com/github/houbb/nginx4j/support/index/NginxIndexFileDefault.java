package com.github.houbb.nginx4j.support.index;

import com.github.houbb.heaven.support.tuple.impl.Pair;
import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.heaven.util.io.ResourceUtil;
import com.github.houbb.heaven.util.io.StreamUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.util.InnerMimeUtil;

import java.io.File;
import java.util.List;

public class NginxIndexFileDefault implements NginxIndexFile {

    private static final Log log = LogFactory.getLog(NginxIndexFileDefault.class);

    @Override
    public File getIndexFile(NginxConfig nginxConfig) {
        return getIndexContentBytes(nginxConfig);
    }

    public File getIndexContentBytes(NginxConfig nginxConfig) {
        List<String> indexHtmlList = nginxConfig.getHttpServerIndexList();

        String basicPath = nginxConfig.getHttpServerRoot();
        for(String indexHtml : indexHtmlList) {
            String fullPath = FileUtil.buildFullPath(basicPath, indexHtml);

            File file = new File(fullPath);
            if(file.exists()) {
                log.info("[Nginx4j] indexFile match define path={}", file.getAbsolutePath());
                return file;
            }
        }

        // 默认
        log.info("[Nginx4j] indexFile try read default index.html");
        String resource = ResourceUtil.getClassResource(NginxIndexFileDefault.class);
        int index = resource.indexOf("/target/classes") + "/target/classes/".length();
        String basicDir = resource.substring(0, index);
        String fullIndexPath = FileUtil.buildFullPath(basicDir, "index.html");
        log.info("[Nginx4j] resource={}", fullIndexPath);
        return new File(fullIndexPath);
    }

}
