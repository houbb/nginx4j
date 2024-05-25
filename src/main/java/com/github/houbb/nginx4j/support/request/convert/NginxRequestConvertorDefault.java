package com.github.houbb.nginx4j.support.request.convert;

import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.support.request.dto.NginxRequestInfoBo;

public class NginxRequestConvertorDefault implements NginxRequestConvertor {


    /**
     *
     * Server read: GET /my HTTP/1.1
     * Host: localhost:8080
     * Connection: keep-alive
     * Cache-Control: max-age=0
     * sec-ch-ua: "Google Chrome";v="123", "Not:A-Brand";v="8", "Chromium";v="123"
     * sec-ch-ua-mobile: ?0
     * sec-ch-ua-platform: "Windows"
     * Upgrade-Insecure-Requests: 1
     * User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36
     *
     * @param requestString 文本
     * @param nginxConfig 配置
     * @return 结果
     * @since 0.1.0
     */
    @Override
    public NginxRequestInfoBo convert(String requestString, NginxConfig nginxConfig) {
        // 使用正则表达式按行分割请求字符串
        String[] requestLines = requestString.split("\r\n");

        // 获取第一行请求行
        String firstLine = requestLines[0];

        String[] strings = firstLine.split(" ");
        String method = strings[0];
        String url = strings[1];

        return new NginxRequestInfoBo(url, method);
    }

}
