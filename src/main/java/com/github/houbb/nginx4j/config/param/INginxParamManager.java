package com.github.houbb.nginx4j.config.param;

import com.github.houbb.nginx4j.config.NginxCommonConfigParam;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

import java.util.List;

/**
 * @since 0.16.0
 */
public interface INginxParamManager {

    /**
     * 注册
     * @param nginxParamHandle 处理类
     * @return 结果
     */
    INginxParamManager register(final INginxParamHandle nginxParamHandle);

    /**
     * 所有的处理列表
     * @return 列表
     */
    List<INginxParamHandle> paramHandleList();

    /**
     * 符合条件的
     * @param configParam 配置
     * @param context 上下文
     * @return 结果列表
     */
    List<INginxParamHandle> paramHandleList(final NginxCommonConfigParam configParam, final NginxRequestDispatchContext context);

}
