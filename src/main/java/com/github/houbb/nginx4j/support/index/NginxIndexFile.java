package com.github.houbb.nginx4j.support.index;

import com.github.houbb.nginx4j.config.NginxConfig;

import java.io.File;

public interface NginxIndexFile {

    File getIndexFile(final NginxConfig nginxConfig);

}
