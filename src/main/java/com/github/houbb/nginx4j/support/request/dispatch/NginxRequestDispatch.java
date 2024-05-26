package com.github.houbb.nginx4j.support.request.dispatch;

public interface NginxRequestDispatch {

    /**
     * 内容的分发处理
     *
     * //1. root
     * //2. dir
     * //3. 小文件
     * //4. 大文件
     *
     * @param context 上下文
     * @return 结果
     */
    void dispatch(final NginxRequestDispatchContext context);

}
