package com.github.houbb.nginx4j.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexRewriteTest {

    public static void main(String[] args) {
        // 示例原始 URL
        String originalUrl = "http://example.com/path/to/resource";

        // 定义匹配模式和替换模式
        String regex = "http://example\\.com/(.*)";
//        String replacement = "http://newdomain.com/$1";
        String replacement = "http://newdomain.com/$1";

        // 创建 Pattern 对象
        Pattern pattern = Pattern.compile(regex);

        // 创建 Matcher 对象
        Matcher matcher = pattern.matcher(originalUrl);

        // 进行替换
        String rewrittenUrl = matcher.replaceAll(replacement);

        // 输出重写后的 URL
        System.out.println("Original URL: " + originalUrl);
        System.out.println("Rewritten URL: " + rewrittenUrl);
    }


}
