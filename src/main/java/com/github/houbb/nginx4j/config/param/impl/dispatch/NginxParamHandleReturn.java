package com.github.houbb.nginx4j.config.param.impl.dispatch;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.config.param.AbstractNginxParamLifecycleDispatch;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import com.github.houbb.nginx4j.support.returns.NginxReturnResult;
import com.github.houbb.nginx4j.support.rewrite.NginxRewriteDirectiveResult;
import com.github.houbb.nginx4j.support.rewrite.NginxRewriteFlagEnum;

import java.util.List;

/**
 * rewrite 指令
 *
 * @author 老马啸西风
 * @since 0.24.0
 */
public class NginxParamHandleReturn extends AbstractNginxParamLifecycleDispatch {

    private static final Log logger = LogFactory.getLog(NginxParamHandleReturn.class);

    /**
     * # 设置一个占位符的值
     * <p>
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
        // 设置对应的 return 信息
        List<String> values = configParam.getValues();

        NginxReturnResult nginxReturnResult = new NginxReturnResult();

        int code = Integer.parseInt(values.get(0));
        nginxReturnResult.setCode(code);

        if (values.size() == 2) {
            nginxReturnResult.setValue(values.get(1));
        }
        nginxReturnResult.setValueList(values);

        // 设置结果
        context.setNginxReturnResult(nginxReturnResult);

        return false;
    }

    @Override
    public boolean doAfterDispatch(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context) {
        return false;
    }

    @Override
    protected String getKey(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context) {
        return "return";
    }

    @Override
    public String directiveName() {
        return "return";
    }
}
