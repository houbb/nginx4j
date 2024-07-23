package com.github.houbb.nginx4j.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NginxCheckerTest {

    // 方法：判断 proxy_pass 的目标是 upstream 组还是具体的 URL
    public static String checkProxyPassTarget(String proxyPass, Set<String> upstreamNames) {
        // 移除 proxy_pass 前的空格，并确保字符串是以 "proxy_pass" 开头
        proxyPass = proxyPass.trim();
        if (!proxyPass.startsWith("proxy_pass ")) {
            throw new IllegalArgumentException("Invalid proxy_pass directive");
        }

        // 提取 proxy_pass 的目标
        String target = proxyPass.substring(11).trim();


        // 检查目标是否是上游服务器组
        // 目标字符串不能包含协议头，并且必须是上游服务器组集合的一部分
        // 拆分
        if (!target.startsWith("http://") && !target.startsWith("https://") && upstreamNames.contains(target)) {
            return "Upstream Group";
        }

        // 检查目标是否是具体的 URL
        if (target.startsWith("http://") || target.startsWith("https://")) {
            return "URL";
        }

        // 如果不匹配任何已知的上游服务器组，默认认为是 URL
        return "Unknown target type";
    }

    public static void main(String[] args) {
        // 示例 upstream 组名称集合
        Set<String> upstreamNames = new HashSet<>(Arrays.asList("backend", "api", "service"));

        // 示例 proxy_pass 语句
        String proxyPass1 = "proxy_pass http://backend;";
        String proxyPass2 = "proxy_pass http://example.com;";
        String proxyPass3 = "proxy_pass backend;";

        // 检查并打印结果
        System.out.println(checkProxyPassTarget(proxyPass1, upstreamNames)); // Upstream Group
        System.out.println(checkProxyPassTarget(proxyPass2, upstreamNames)); // URL
        System.out.println(checkProxyPassTarget(proxyPass3, upstreamNames)); // Upstream Group
    }

}
