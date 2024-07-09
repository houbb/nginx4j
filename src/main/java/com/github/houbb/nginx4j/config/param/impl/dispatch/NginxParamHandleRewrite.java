package com.github.houbb.nginx4j.config.param.impl.dispatch;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.config.param.AbstractNginxParamLifecycleDispatch;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import com.github.houbb.nginx4j.support.rewrite.NginxRewriteDirectiveResult;
import com.github.houbb.nginx4j.support.rewrite.NginxRewriteFlagEnum;

/**
 * rewrite 指令
 *
 * @since 0.23.0
 * @author 老马啸西风
 */
public class NginxParamHandleRewrite extends AbstractNginxParamLifecycleDispatch {

    private static final Log logger = LogFactory.getLog(NginxParamHandleRewrite.class);

    /**
     * # 设置一个占位符的值
     *
     * - **last**: 停止当前 `rewrite` 指令的处理，继续处理请求的剩余部分。适用于大多数 URL 重写场景。
     * - **break**: 停止当前 `rewrite` 指令的处理，但继续处理当前 `location` 中的其他指令。适用于需要在同一个 `location` 中继续处理其他逻辑的场景。
     * - **redirect**: 返回 302 临时重定向，适用于临时 URL 更改。
     * - **permanent**: 返回 301 永久重定向，适用于永久 URL 更改，并且希望客户端更新其缓存或书签。
     *
     * @param configParam 参数
     * @param context     上下文
     */
    @Override
    public boolean doBeforeDispatch(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context) {
        // 根据配置，重新设置对应的 location Config 信息

        // 能不能把配置信息，放到 dispatch 中。然后直接实现对应的 301/302? 配置信息也需要、

        // 额外加一个 return code info 等信息。
        if(isNeedBreak(configParam, context)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean doAfterDispatch(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context) {
        if(isNeedBreak(configParam, context)) {
            return false;
        }

        return true;
    }

    private boolean isNeedBreak(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context) {
        final NginxRewriteDirectiveResult rewriteDirectiveResult = context.getNginxRewriteDirectiveResult();
        NginxCommonConfigEntry matchConfigEntry = rewriteDirectiveResult.getRewriteConfig();
        if(matchConfigEntry == null) {
            return false;
        }

        if(!rewriteDirectiveResult.isMatchRewrite()) {
            return false;
        }

        // 如果是 break，则忽略处理
        return NginxRewriteFlagEnum.BREAK.getCode().equals(rewriteDirectiveResult.getRewriteFlag());
    }

    @Override
    protected String getKey(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context) {
        return "rewrite";
    }

}
