package com.github.houbb.nginx4j.config.param;

import com.github.houbb.nginx4j.config.param.impl.dispatch.NginxParamHandleProxySetHeader;
import com.github.houbb.nginx4j.config.param.impl.dispatch.NginxParamHandleSet;
import com.github.houbb.nginx4j.config.param.impl.write.NginxParamHandleAddHeader;
import com.github.houbb.nginx4j.config.param.impl.write.NginxParamHandleProxyHideHeader;

/**
 * 默认管理策略
 *
 * @since 0.19.0
 */
public class NginxParamManagerDefault extends NginxParamManagerBase {


    public NginxParamManagerDefault() {
        // 初始化
        // 添加内置的策略

        this.registerDispatch(new NginxParamHandleSet());
        this.registerDispatch(new NginxParamHandleProxySetHeader());

        this.registerWrite(new NginxParamHandleProxyHideHeader());
        this.registerWrite(new NginxParamHandleAddHeader());
    }


}
