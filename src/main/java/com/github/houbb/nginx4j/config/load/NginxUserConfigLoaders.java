package com.github.houbb.nginx4j.config.load;

public class NginxUserConfigLoaders {

    public static INginxUserConfigLoader configFile(final String file) {
        return new NginxUserConfigLoaderConfigFile(file);
    }

    public static INginxUserConfigLoader configComponentFile(final String file) {
        return new NginxUserConfigLoaderConfigComponentFile(file);
    }

}
