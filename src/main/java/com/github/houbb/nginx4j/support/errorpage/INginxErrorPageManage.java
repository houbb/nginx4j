package com.github.houbb.nginx4j.support.errorpage;

/**
 * 错误页管理类
 *
 * @since 0.25.0
 */
public interface INginxErrorPageManage {

    /**
     * 注册
     * @param code 编码
     * @param htmlPath 页面路径
     */
    void register(final String code, final String htmlPath);

    /**
     * 获取对应的页面
     * @param code 编码
     * @return 结果
     */
    String getPath(String code);

}

