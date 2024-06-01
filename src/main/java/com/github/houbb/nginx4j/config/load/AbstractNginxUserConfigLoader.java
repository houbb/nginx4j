package com.github.houbb.nginx4j.config.load;

import com.github.houbb.nginx4j.config.NginxUserConfig;

public abstract class AbstractNginxUserConfigLoader implements INginxUserConfigLoader {

    protected abstract NginxUserConfig doLoad();

    @Override
    public NginxUserConfig load() {
        return this.doLoad();
    }

}
