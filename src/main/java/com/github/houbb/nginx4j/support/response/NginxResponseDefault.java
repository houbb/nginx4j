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

    /**
     * 符合 http 标准的字符串
     * @param rawText 原始文本
     * @param nginxRequestInfoBo 请求
     * @param nginxConfig 配置
     * @return 结果
     */
    protected String http200Resp(String rawText,
                                 final NginxRequestInfoBo nginxRequestInfoBo,
                                 final NginxConfig nginxConfig) {
        String format = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/plain\r\n" +
                "\r\n" +
                "%s";

        return String.format(format, rawText);
    }

    /**
     * 400 响应
     *
     * @return 结果
     * @since 0.2.0
     */
    protected String http400Resp(String content,
                                 final NginxRequestInfoBo nginxRequestInfoBo,
                                 final NginxConfig nginxConfig) {
        String defaultContent = "400 Bad Request: The request could not be understood by the server due to malformed syntax.";
        if(StringUtil.isNotEmpty(content)) {
            defaultContent = content;
        }

        return "HTTP/1.1 400 Bad Request\r\n" +
                "Content-Type: text/plain\r\n" +
                "\r\n" + defaultContent;
    }

    /**
     * 404 响应
     * @return 结果
     * @since 0.2.0
     */
    protected String http404Resp(String content,
                                 final NginxRequestInfoBo nginxRequestInfoBo,
                                 final NginxConfig nginxConfig) {
        String defaultContent = "404 Not Found: The requested resource was not found on this server.";
        if(StringUtil.isNotEmpty(content)) {
            defaultContent = content;
        }

        return "HTTP/1.1 404 Not Found\r\n" +
                "Content-Type: text/plain\r\n" +
                "\r\n" + defaultContent;
    }

    /**
     * 500 响应
     * @return 结果
     * @since 0.2.0
     */
    protected String http500Resp(String content,
                                 final NginxRequestInfoBo nginxRequestInfoBo,
                                 final NginxConfig nginxConfig) {
        String defaultContent = "500 Internal Server Error: The server encountered an unexpected condition that prevented it from fulfilling the request.";
        if(StringUtil.isNotEmpty(content)) {
            defaultContent = content;
        }

        return "HTTP/1.1 500 Internal Server Error\r\n" +
                "Content-Type: text/plain\r\n" +
                "\r\n" + defaultContent;
    }

}
