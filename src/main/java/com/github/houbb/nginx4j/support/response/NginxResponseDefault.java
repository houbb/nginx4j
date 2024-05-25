package com.github.houbb.nginx4j.support.response;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.constant.HttpStatusEnum;
import com.github.houbb.nginx4j.support.request.dto.NginxRequestInfoBo;

public class NginxResponseDefault implements NginxResponse {

    @Override
    public String buildResp(final HttpStatusEnum httpStatusEnum,
                            final String content,
                            final NginxRequestInfoBo requestInfo,
                            final NginxConfig nginxConfig) {
       return buildCommentResp(content, httpStatusEnum, requestInfo, nginxConfig);
    }

    /**
     * String format = "HTTP/1.1 200 OK\r\n" +
     *                 "Content-Type: text/plain\r\n" +
     *                 "\r\n" +
     *                 "%s";
     *
     * @param rawText 原始内容
     * @param httpStatusEnum 结果枚举
     * @param nginxRequestInfoBo 请求内容
     * @param nginxConfig 配置
     * @return 结果
     */
    protected String buildCommentResp(String rawText,
                                      final HttpStatusEnum httpStatusEnum,
                                      final NginxRequestInfoBo nginxRequestInfoBo,
                                      final NginxConfig nginxConfig) {
        String defaultContent = httpStatusEnum.getDefaultDesc();
        if(StringUtil.isNotEmpty(rawText)) {
            defaultContent = rawText;
        }

        String respFormat = "HTTP/1.1 %s %s\r\n" +
                "Content-Type: text/plain\r\n" +
                "\r\n" +
                "%s";

        return String.format(respFormat, httpStatusEnum.getCode(), httpStatusEnum.getDesc(), defaultContent);
    }

}
