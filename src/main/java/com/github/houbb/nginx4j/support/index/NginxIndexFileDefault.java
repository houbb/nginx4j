package com.github.houbb.nginx4j.support.index;

import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import com.github.houbb.nginx4j.util.InnerFileUtil;

import java.io.File;
import java.util.List;

public class NginxIndexFileDefault implements NginxIndexFile {

    private static final Log log = LogFactory.getLog(NginxIndexFileDefault.class);

    @Override
    public File getIndexFile(NginxRequestDispatchContext context) {
        return getIndexContentBytes(context);
    }

    public File getIndexContentBytes(NginxRequestDispatchContext context) {
        final NginxUserServerConfig nginxUserServerConfig = context.getCurrentNginxUserServerConfig();

        List<String> indexHtmlList = nginxUserServerConfig.getIndexList();
        String basicPath = nginxUserServerConfig.getRoot();
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
        String basicDir = InnerFileUtil.getRootPath();
        String fullIndexPath = FileUtil.buildFullPath(basicDir, "index.html");
        log.info("[Nginx4j] resource={}", fullIndexPath);
        return new File(fullIndexPath);
    }

}
