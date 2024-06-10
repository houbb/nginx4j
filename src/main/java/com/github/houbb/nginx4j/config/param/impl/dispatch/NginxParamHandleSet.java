package com.github.houbb.nginx4j.config.param.impl.dispatch;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxCommonConfigParam;
import com.github.houbb.nginx4j.config.param.AbstractNginxParamLifecycleDispatch;
import com.github.houbb.nginx4j.constant.NginxConst;
import com.github.houbb.nginx4j.exception.Nginx4jException;
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
    public void doBeforeDispatch(NginxCommonConfigParam configParam, NginxRequestDispatchContext context) {
        Map<String, Object> placeholderMap = context.getPlaceholderMap();

        // 处理
        List<String> values = configParam.getValues();
        String headerName = values.get(0);
        String headerValue = values.get(1);

        // 变量名必须以 $ 开始
        if(!headerName.startsWith(NginxConst.PLACEHOLDER_PREFIX)) {
            throw new Nginx4jException("SET 指令对应的变量名必须以 $ 开始");
        }

        placeholderMap.put(headerName, headerValue);
    }

    @Override
    public void doAfterDispatch(NginxCommonConfigParam configParam, NginxRequestDispatchContext context) {
    }

    @Override
    protected String getKey(NginxCommonConfigParam configParam, NginxRequestDispatchContext context) {
        return "set";
    }

}
