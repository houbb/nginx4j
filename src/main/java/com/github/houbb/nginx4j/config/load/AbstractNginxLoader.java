package com.github.houbb.nginx4j.config.load;

import com.github.houbb.nginx4j.config.NginxConfig;

public abstract class AbstractNginxLoader implements INginxLoader {

    protected abstract NginxConfig doLoad();

    @Override
    public NginxConfig load() {
        return this.doLoad();
    }

}
