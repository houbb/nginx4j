package com.github.houbb.nginx4j.support.tryfiles;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.constant.NginxDirectiveEnum;
import com.github.houbb.nginx4j.support.placeholder.INginxPlaceholder;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import com.github.houbb.nginx4j.support.returns.NginxReturnResult;
import com.github.houbb.nginx4j.util.InnerFileUtil;
import com.github.houbb.nginx4j.util.InnerNginxContextUtil;
import io.netty.handler.codec.http.FullHttpRequest;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * try_files path1 path2 ... final;
 *
 * - `path1`, `path2`, ...：要检查的文件或 URI 列表。可以是相对路径或绝对路径。
 *
 * - `final`：如果前面的所有路径都不存在，最后一个参数可以是一个 URI，Nginx 将内部重定向到该 URI，或者是一个 HTTP 状态码（如 404），用于返回相应的错误。
 *
 * @see INginxPlaceholder 占位符
 * @see com.github.houbb.nginx4j.support.request.dispatch.http.NginxRequestDispatchHttpReturn 设置对应的返回码 =xxx
 */
public class NginxTryFilesDefault implements INginxTryFiles{

    private static final Log log = LogFactory.getLog(NginxTryFilesDefault.class);

    /**
     * 处理 try_files 指令
     *
     * @param request     请求
     * @param nginxConfig 配置
     * @param context     上下文
     */
    public void tryFiles(FullHttpRequest request,
                         final NginxConfig nginxConfig,
                         NginxRequestDispatchContext context) {
        // 获取当前的 location
        List<NginxCommonConfigEntry> directiveList = InnerNginxContextUtil.getLocationDirectives(context);
        Map<String, List<NginxCommonConfigEntry>> directiveMap = InnerNginxContextUtil.getLocationDirectiveMap(directiveList);

        List<NginxCommonConfigEntry> tryFiles = directiveMap.get(NginxDirectiveEnum.TRY_FILES.getCode());
        if(CollectionUtil.isEmpty(tryFiles)) {
            return;
        }

        NginxCommonConfigEntry firstEntry = tryFiles.get(0);

        // 遍历非 final 的文件信息
        String notFinalUri = getNotFinalMatchedFileUri(firstEntry, context);
        if(StringUtil.isNotEmpty(notFinalUri)) {
            request.setUri(notFinalUri);
            return;
        }

        // 判断 final 变量
        final String lastUri = firstEntry.getValues().get(firstEntry.getValues().size()-1);
        if(lastUri.startsWith("=")) {
            // 拆分为 return
            NginxReturnResult result = new NginxReturnResult();
            result.setCode(Integer.parseInt(lastUri.substring(1)));
            result.setValue("try_files final");
            context.setNginxReturnResult(result);
            return;
        }

        String lastReplaceUri = replacePlaceholders(lastUri, context.getPlaceholderMap());
        request.setUri(lastReplaceUri);
    }

    /**
     * 获取匹配的文件 url
     * @param entry 实体
     * @param context 上下文
     * @return 结果
     */
    private String getNotFinalMatchedFileUri(final NginxCommonConfigEntry entry,
                                            NginxRequestDispatchContext context) {
        List<String> values = entry.getValues();

        for(int i = 0; i < values.size()-1; i++) {
            String replacedUri = getMatchedFileUri(values.get(i), context);

            if(StringUtil.isNotEmpty(replacedUri)) {
                return replacedUri;
            }
        }

        return null;
    }

    private String getMatchedFileUri(final String requestUri,
                                     NginxRequestDispatchContext context) {
        final Map<String, Object> replaceMap = context.getPlaceholderMap();

        String replacedUri = replacePlaceholders(requestUri, replaceMap);

        // 判断文件是否存
        File file = InnerFileUtil.getTargetFile(replacedUri, context);
        if(file.exists()) {
            log.info("Nginx getMatchedFileUri file={}", file.getAbsolutePath());
            return replacedUri;
        }

    return null;
    }

    /**
     * 替换字符串中的占位符
     *
     * @param input    用户输入的字符串，包含占位符
     * @param variables 存储占位符及其替换值的 Map
     * @return 替换后的字符串
     */
    public static String replacePlaceholders(String input, Map<String, Object> variables) {
        // 使用 StringBuilder 来构建替换后的字符串
        StringBuilder result = new StringBuilder(input);

        // 遍历 Map 进行替换
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            String placeholder = entry.getKey();
            String replacement = String.valueOf(entry.getValue());

            // 使用 String 的 replace 方法替换所有占位符
            int start = result.indexOf(placeholder);
            while (start != -1) {
                result.replace(start, start + placeholder.length(), replacement);
                start = result.indexOf(placeholder, start + replacement.length());
            }
        }

        return result.toString();
    }

}
