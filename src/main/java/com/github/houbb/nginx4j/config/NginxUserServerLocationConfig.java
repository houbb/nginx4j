package com.github.houbb.nginx4j.config;

import com.github.houbb.nginx4j.constant.NginxLocationPathTypeEnum;

/**
 * 用户配置
 *
 * @since 0.12.0
 */
public class NginxUserServerLocationConfig extends NginxCommonUserConfig {

    /**
     * 优先级
     */
    private NginxLocationPathTypeEnum typeEnum;

    public NginxLocationPathTypeEnum getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(NginxLocationPathTypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

}
