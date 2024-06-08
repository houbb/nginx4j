package com.github.houbb.nginx4j.config.param;

public class NginxParamManagerDefault extends NginxParamManagerBase {



    public NginxParamManagerDefault() {
        // 初始化
        // 添加内置的策略

        this.register(new NginxParamHandleProxyHideHeader());
        this.register(new NginxParamHandleAddHeader());
        this.register(new NginxParamHandleProxyHideHeader());
    }


}
