package com.github.houbb.nginx4j.support.index;

import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.heaven.util.io.StreamUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.exception.Nginx4jException;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.List;

public class NginxIndexContentDefault implements NginxIndexContent {

    private static final Log log = LogFactory.getLog(NginxIndexContentDefault.class);

    @Override
    public String getContent(NginxConfig nginxConfig) {
        try {
            byte[] bytes = getIndexContentBytes(nginxConfig);
            return new String(bytes, nginxConfig.getCharset());
        } catch (UnsupportedEncodingException e) {
            log.error("[Nginx] getIndexContent meet ex", e);
            throw new RuntimeException(e);
        }
    }

    public byte[] getIndexContentBytes(NginxConfig nginxConfig) {
        try {
            List<String> indexHtmlList = nginxConfig.getHttpServerIndexList();

            String basicPath = nginxConfig.getHttpServerRoot();
            for(String indexHtml : indexHtmlList) {
                String fullPath = FileUtil.buildFullPath(basicPath, indexHtml);

                File file = new File(fullPath);
                if(file.exists()) {
                    log.info("[Nginx4j] indexFile match indexPath={}", fullPath);
                    return Files.readAllBytes(file.toPath());
                }
            }

            // 默认
            log.info("[Nginx4j] indexFile read default index.html");
            return StreamUtil.getFileBytes("index.html");
        } catch (IOException e) {
            log.error("[Nginx4j] getIndexContent meet ex", e);
            throw new Nginx4jException(e);
        }
    }

}
