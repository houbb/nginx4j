package com.github.houbb.nginx4j.support.index;

import com.github.houbb.nginx4j.config.NginxConfig;

public interface NginxIndexContent {

    String getContent(final NginxConfig nginxConfig);

}
