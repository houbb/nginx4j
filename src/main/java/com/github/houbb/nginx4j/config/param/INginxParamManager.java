package com.github.houbb.nginx4j.config.param;

import java.util.List;

/**
 * @since 0.16.0
 */
public interface INginxParamManager {

    /**
     * 获取匹配的分发
     * @param name 名称
     * @return 结果
     * @since 0.24.0
     */
    INginxParamLifecycleDispatch getMatchDispatch(final String name);

    /**
     * 注册 dispatch
     * @param dispatch 处理类
     * @return 结果
     * @since 0.19.0
     */
    INginxParamManager registerDispatch(final INginxParamLifecycleDispatch dispatch);

    INginxParamManager registerComplete(final INginxParamLifecycleComplete complete);

    INginxParamManager registerWrite(final INginxParamLifecycleWrite write);

    List<INginxParamLifecycleComplete> getCompleteList();

    List<INginxParamLifecycleDispatch> getDispatchList();

    List<INginxParamLifecycleWrite> getWriteList();

}
