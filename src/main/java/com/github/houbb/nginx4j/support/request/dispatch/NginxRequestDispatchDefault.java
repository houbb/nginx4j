package com.github.houbb.nginx4j.support.request.dispatch;

import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.constant.HttpStatusEnum;
import com.github.houbb.nginx4j.support.request.dto.NginxRequestInfoBo;
import com.github.houbb.nginx4j.support.response.NginxResponse;
import com.github.houbb.nginx4j.support.server.NginxServerSocket;


/**
 * 静态资源
 * @since 0.2.0
 */
public class NginxRequestDispatchDefault implements NginxRequestDispatch {

    private static final Log log = LogFactory.getLog(NginxServerSocket.class);

    /**
     * 内容的分发处理
     *
     * @param requestInfoBo 请求
     * @param nginxConfig   配置
     * @return 结果
     */
    public String dispatch(final NginxRequestInfoBo requestInfoBo, final NginxConfig nginxConfig) {
        final String basicPath = nginxConfig.getHttpServerRoot();
        final String path = requestInfoBo.getUrl();

        boolean isRootPath = isRootPath(requestInfoBo, nginxConfig);
        final NginxResponse nginxResponse = nginxConfig.getNginxResponse();
        if(isRootPath) {
            log.info("[Nginx] current req meet root path");
            String indexContent = nginxConfig.getNginxIndexContent().getContent(nginxConfig);
            return nginxResponse.buildResp(HttpStatusEnum.OK, indexContent, requestInfoBo, nginxConfig);
        }

        // other
        String fullPath = FileUtil.buildFullPath(basicPath, path);
        // 是否存在
        if(FileUtil.exists(fullPath)) {
            String fileContent = FileUtil.getFileContent(fullPath);
            return nginxResponse.buildResp(HttpStatusEnum.OK, fileContent, requestInfoBo, nginxConfig);
        }  else {
            return nginxResponse.buildResp(HttpStatusEnum.NOT_FOUND, HttpStatusEnum.NOT_FOUND.getDefaultDesc(), requestInfoBo, nginxConfig);
        }
    }

    protected boolean isRootPath(final NginxRequestInfoBo requestInfoBo, final NginxConfig nginxConfig) {
        final String path = requestInfoBo.getUrl();

        //root path
        if(StringUtil.isEmpty(path) || "/".equals(path)) {
            return true;
        }
        return false;
    }

}
