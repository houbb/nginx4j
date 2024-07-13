package com.github.houbb.nginx4j.config.param;

import com.github.houbb.nginx4j.config.param.impl.dispatch.*;
import com.github.houbb.nginx4j.config.param.impl.write.NginxParamHandleAddHeader;
import com.github.houbb.nginx4j.config.param.impl.write.NginxParamHandleProxyCookieDomain;
import com.github.houbb.nginx4j.config.param.impl.write.NginxParamHandleProxyCookieFlags;
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
        this.registerDispatch(new NginxParamHandleErrorPage());
        this.registerDispatch(new NginxParamHandleProxySetHeader());
        this.registerDispatch(new NginxParamHandleProxyCookiePath());
        this.registerDispatch(new NginxParamHandleRewrite());
        this.registerDispatch(new NginxParamHandleReturn());

        this.registerWrite(new NginxParamHandleAddHeader());
        this.registerWrite(new NginxParamHandleProxyHideHeader());
        this.registerWrite(new NginxParamHandleProxyCookieDomain());
        this.registerWrite(new NginxParamHandleProxyCookieFlags());

    }


}
