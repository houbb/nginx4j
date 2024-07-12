package com.github.houbb.nginx4j.config.param.impl.dispatch;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.config.param.AbstractNginxParamLifecycleDispatch;
import com.github.houbb.nginx4j.constant.NginxConst;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.placeholder.INginxPlaceholderManager;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

import java.util.List;
import java.util.Map;

/**
 * SET 符号，设置一个 $ 变量
 *
 * @since 0.17.0
 * @author 老马啸西风
 */
public class NginxParamHandleSet extends AbstractNginxParamLifecycleDispatch {

    private static final Log logger = LogFactory.getLog(NginxParamHandleSet.class);

    /**
     * # 设置一个占位符的值
     *
     * set $mobile 1;
     *
     * @param configParam 参数
     * @param context     上下文
     */
    @Override
    public boolean doBeforeDispatch(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context) {
        Map<String, Object> placeholderMap = context.getPlaceholderMap();
        final INginxPlaceholderManager nginxPlaceholderManager = context.getNginxConfig().getNginxPlaceholderManager();

        // 处理
        List<String> values = configParam.getValues();
        String headerName = values.get(0);
        String headerValue = getPlaceholderStr(values.get(1), nginxPlaceholderManager, context);

        // 变量名必须以 $ 开始
        if(!headerName.startsWith(NginxConst.PLACEHOLDER_PREFIX)) {
            throw new Nginx4jException("SET 指令对应的变量名必须以 $ 开始");
        }

        placeholderMap.put(headerName, headerValue);

        return true;
    }

    /**
     * 获取占位符对应的值
     * @param value 原始值
     * @param placeholderManager 管理类
     * @param context 上下文
     * @return 结果
     */
    protected String getPlaceholderStr(String value,
                                       final INginxPlaceholderManager placeholderManager,
                                       final NginxRequestDispatchContext context) {
        // value
        if(value.startsWith(NginxConst.PLACEHOLDER_PREFIX)) {
            Object actualValue = placeholderManager.getValue(context, value);
            if(actualValue == null) {
                logger.error("占位符未初始化 value={}", value);
                throw new Nginx4jException("占位符未初始化" + value);
            }

            // 设置值
            String actualValueStr = actualValue.toString();
            logger.debug("占位符替换 value={}, actualValueStr={}", value, actualValueStr);
            return actualValueStr;
        }

        // 原始值
        return value;
    }

    @Override
    public boolean doAfterDispatch(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context) {
        return true;
    }

    @Override
    protected String getKey(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context) {
        return "set";
    }

    @Override
    public String directiveName() {
        return "set";
    }

}
