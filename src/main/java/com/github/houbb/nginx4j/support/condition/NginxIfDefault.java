package com.github.houbb.nginx4j.support.condition;

import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

import java.util.List;

/**
 * @since 0.21.0
 */
public class NginxIfDefault implements NginxIf {

    /**
     * - **变量值匹配**:
     *
     *   ```nginx
     *   if ($variable = value) {
     *       # 指令
     *   }
     *   ```
     *
     * - **变量值不匹配**:
     *
     *   ```nginx
     *   if ($variable != value) {
     *       # 指令
     *   }
     *   ```
     *
     * - **变量是否设置**:
     *   ```nginx
     *   if ($variable) {
     *       # 指令
     *   }
     *   ```
     *
     * - **变量是否为空**:
     *   ```nginx
     *   if ($variable = "") {
     *       # 指令
     *   }
     *   ```
     *
     * - **正则表达式匹配**:
     *   ```nginx
     *   if ($variable ~ pattern) {
     *       # 指令
     *   }
     *   ```
     *
     * - **正则表达式不匹配**:
     *   ```nginx
     *   if ($variable !~ pattern) {
     *       # 指令
     *   }
     *   ```
     *
     * - **正则表达式匹配并忽略大小写**:
     *   ```nginx
     *   if ($variable ~* pattern) {
     *       # 指令
     *   }
     *   ```
     *
     * - **正则表达式不匹配并忽略大小写**:
     *   ```nginx
     *   if ($variable !~* pattern) {
     *       # 指令
     *   }
     *   ```
     *
     * @param configParam 配置
     * @param dispatchContext 上下文
     * @return 结果
     */
    @Override
    public boolean match(NginxCommonConfigEntry configParam, NginxRequestDispatchContext dispatchContext) {
        //TODO 实现
        String name = configParam.getName();
        List<String> values = configParam.getValues();


        return false;
    }

}
