package com.github.houbb.nginx4j.support.request.dispatch.http;

import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatch;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

public abstract class AbstractNginxRequestDispatch implements NginxRequestDispatch {

    public abstract void doDispatch(final NginxRequestDispatchContext context);

    /**
     * 内容的分发处理
     * <p>
     * //1. root
     * //2. dir
     * //3. 小文件
     * //4. 大文件
     *
     * @param context 上下文
     */
    public void dispatch(final NginxRequestDispatchContext context) {
        doDispatch(context);
    }

}
