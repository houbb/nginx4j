package com.github.houbb.nginx4j.config.load;

import com.github.houbb.nginx4j.config.NginxUserConfig;

public interface INginxUserConfigLoader {

    /**
     * 加载
     * @return 结果
     *
     * */
    NginxUserConfig load();

}
