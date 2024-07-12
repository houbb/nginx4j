package com.github.houbb.nginx4j.support.request.dispatch;

import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.config.NginxUserServerConfig;
import com.github.houbb.nginx4j.support.request.dispatch.http.NginxRequestDispatches;
import com.github.houbb.nginx4j.support.rewrite.NginxRewriteDirectiveResult;
import com.github.houbb.nginx4j.support.rewrite.NginxRewriteFlagEnum;
import io.netty.handler.codec.http.FullHttpRequest;

import java.io.File;


/**
 * 静态资源
 * @author 老马啸西风
 * @since 0.2.0
 */
public class NginxRequestDispatchManager implements NginxRequestDispatch {

    private static final Log log = LogFactory.getLog(NginxRequestDispatchManager.class);

    /**
     * 内容的分发处理
     *
     * @param context 上下文
     */
    public void dispatch(NginxRequestDispatchContext context) {
        final NginxRequestDispatch dispatch = getDispatch(context);

        dispatch.dispatch(context);
    }

    protected NginxRequestDispatch getDispatch(NginxRequestDispatchContext context) {
        final FullHttpRequest requestInfoBo = context.getRequest();
        final NginxConfig nginxConfig = context.getNginxConfig();

        // 消息解析不正确
        /*如果无法解码400*/
        if (!requestInfoBo.decoderResult().isSuccess()) {
            return NginxRequestDispatches.http400();
        }

        // 如果存在 return
        // 301+return 不应该放在这里实现。
//        if(context.getNginxReturnResult() != null) {
//            return NginxRequestDispatches.httpReturn();
//        }
        //301
        final NginxRewriteDirectiveResult rewriteDirectiveResult = context.getNginxRewriteDirectiveResult();
        if(rewriteDirectiveResult.isMatchRewrite()) {
            String rewriteFlag = rewriteDirectiveResult.getRewriteFlag();

            if(NginxRewriteFlagEnum.PERMANENT.getCode().equals(rewriteFlag)) {
                return NginxRequestDispatches.http301();
            }

            if(NginxRewriteFlagEnum.REDIRECT.getCode().equals(rewriteFlag)) {
                return NginxRequestDispatches.http302();
            }
        }

        // 文件
        File targetFile = getTargetFile(requestInfoBo, context);
        // 是否存在
        if(targetFile.exists()) {
            // 设置文件
            context.setFile(targetFile);

            // 如果是文件夹
            if(targetFile.isDirectory()) {
                return NginxRequestDispatches.fileDir();
            }

            // range ?
            boolean isRangeRequest = isRangeRequest(requestInfoBo, context);
            if(isRangeRequest) {
                return NginxRequestDispatches.fileRange();
            }

            // 文件处理
            return NginxRequestDispatches.file();
        }  else {
            return NginxRequestDispatches.http404();
        }
    }

    /**
     * 是否为范围查询
     * @param request 请求
     * @param context 配置
     * @return 结果
     * @since 0.7.0
     */
    protected boolean isRangeRequest(final FullHttpRequest request, NginxRequestDispatchContext context) {
        // 解析Range头
        String rangeHeader = request.headers().get("Range");
        return StringUtil.isNotEmpty(rangeHeader);
    }

    protected File getTargetFile(final FullHttpRequest request, final NginxRequestDispatchContext context) {
        final NginxUserServerConfig nginxUserServerConfig = context.getCurrentNginxUserServerConfig();
        boolean isRootPath = isRootPath(request, context);
        final NginxConfig nginxConfig = context.getNginxConfig();

        // 根路径
        if(isRootPath) {
            log.info("[Nginx] current req meet root path");
            return nginxConfig.getNginxIndexFile().getIndexFile(context);
        }

        final String basicPath = nginxUserServerConfig.getRoot();
        final String path = request.uri();

        // other
        String fullPath = FileUtil.buildFullPath(basicPath, path);
        return new File(fullPath);
    }

    protected boolean isRootPath(final FullHttpRequest request, final NginxRequestDispatchContext context) {
        final String path = request.uri();

        //root path
        if(StringUtil.isEmpty(path) || "/".equals(path)) {
            return true;
        }
        return false;
    }

}
