package com.github.houbb.nginx4j.support.rewrite;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.config.NginxUserServerLocationConfig;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.util.InnerLocationConfigUtil;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NginxRewriteDirectiveDefault implements NginxRewriteDirective {

    private static final Log logger = LogFactory.getLog(NginxRewriteDirectiveDefault.class);

    @Override
    public NginxRewriteDirectiveResult handleRewrite(NginxRewriteDirectiveContext context) {
        NginxUserServerLocationConfig currentLocationConfig = context.getCurrentLocationConfig();
        // 判断是否存在 rewrite
        List<NginxCommonConfigEntry> nginxCommonConfigEntries = currentLocationConfig.getConfigEntryList();

        NginxCommonConfigEntry matchRewriteConfig = getMatchRewriteEntry(nginxCommonConfigEntries, context);
        NginxRewriteDirectiveResult result = new NginxRewriteDirectiveResult();
        if(matchRewriteConfig == null) {
            return result;
        }

        final FullHttpRequest fullHttpRequest = context.getRequest();

        // 命中
        result.setMatchRewrite(true);
        result.setRewriteConfig(matchRewriteConfig);
        result.setBeforeUrl(fullHttpRequest.uri());
        String rewriteFlag = getRewriteFlag(matchRewriteConfig);
        result.setRewriteFlag(rewriteFlag);
        // 获取处理后的
        String replaceUri = getReplacedUrl(fullHttpRequest, matchRewriteConfig);
        result.setAfterUrl(replaceUri);

        // 获取 location 配置
        // 如果是 last 的话，再做替换
        fullHttpRequest.setUri(replaceUri);
        final NginxUserServerLocationConfig locationConfig = getLocationConfig(rewriteFlag, fullHttpRequest, context);
        result.setCurrentLocationConfig(locationConfig);

        return result;
    }

    private String getReplacedUrl(final FullHttpRequest fullHttpRequest,
                                  NginxCommonConfigEntry matchRewriteConfig) {
        // rewrite regex replacement
        List<String> values = matchRewriteConfig.getValues();
        final String regex = values.get(0);
        final String replacement = values.get(1);

        final String originalUrl = fullHttpRequest.uri();

        // 创建 Pattern 对象
        Pattern pattern = Pattern.compile(regex);

        // 创建 Matcher 对象
        Matcher matcher = pattern.matcher(originalUrl);

        // 进行替换
        String rewrittenUrl = matcher.replaceAll(replacement);
        return rewrittenUrl;
    }

    private NginxUserServerLocationConfig getLocationConfig(final String rewriteFlag,
                                                            final FullHttpRequest fullHttpRequest,
                                                            NginxRewriteDirectiveContext context) {
        // 如果不是 last，那么直接返回原来的信息
        if(!NginxRewriteFlagEnum.LAST.getCode().equals(rewriteFlag)) {
            return context.getCurrentLocationConfig();
        }

        // 处理
        return InnerLocationConfigUtil.getCurrentServerLocation(context.getNginxConfig(),
                context.getNginxUserServerConfig(),
                fullHttpRequest
                );
    }

    private String getRewriteFlag(NginxCommonConfigEntry matchRewriteConfig) {
        // rewrite regex replacement [flag];
        List<String> values = matchRewriteConfig.getValues();
        if(values.size() < 2) {
            throw new Nginx4jException("rewrite 指令格式为 rewrite regex replacement [flag];，当前不符合 " + values);
        }

        if(values.size() == 3) {
            return values.get(2);
        }

        return NginxRewriteFlagEnum.LAST.getCode();
    }

    private NginxCommonConfigEntry getMatchRewriteEntry(List<NginxCommonConfigEntry> nginxCommonConfigEntries,
                                                        NginxRewriteDirectiveContext context) {
        if(CollectionUtil.isEmpty(nginxCommonConfigEntries)) {
            return null;
        }

        final FullHttpRequest fullHttpRequest = context.getRequest();
        String uri = fullHttpRequest.uri();

        for(NginxCommonConfigEntry entry : nginxCommonConfigEntries) {
            if(entry.getName().equalsIgnoreCase("rewrite")) {
                // rewrite regex replacement [flag];
                List<String> values = entry.getValues();
                if(values.size() < 2) {
                    throw new Nginx4jException("rewrite 指令格式为 rewrite regex replacement [flag];，当前不符合 " + values);
                }

                String regex = values.get(0);
                if(uri.matches(regex)) {
                    logger.info("uri={} 命中 regex={}", uri, regex);
                    return entry;
                }
            }
        }

        return null;
    }



}
