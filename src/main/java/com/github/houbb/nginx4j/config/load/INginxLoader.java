package com.github.houbb.nginx4j.config.load;

import com.github.houbb.nginx4j.config.NginxConfig;

public interface INginxLoader {

    /**
     * 加载
     * @return 结果
     *
     * */
    NginxConfig load();

}
