package com.github.houbb.nginx4j.config.param.impl.dispatch;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.config.param.AbstractNginxParamLifecycleDispatch;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.errorpage.INginxErrorPageManage;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

import java.util.List;

/**
 * 参数处理类 响应头处理
 *
 * @since 0.25.0
 * @author 老马啸西风
 */
public class NginxParamHandleErrorPage extends AbstractNginxParamLifecycleDispatch {

    private static final Log logger = LogFactory.getLog(NginxParamHandleErrorPage.class);


    @Override
    public boolean doBeforeDispatch(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context) {
        List<String> values = configParam.getValues();
        if(CollectionUtil.isEmpty(values) || values.size() < 2) {
            throw new Nginx4jException("error_page 必须包含2个参数");
        }

        NginxConfig nginxConfig = context.getNginxConfig();
        INginxErrorPageManage nginxErrorPageManage = nginxConfig.getNginxErrorPageManage();

        // 直接拆分
        String lastHtml = values.get(values.size()-1);
        for(int i = 0; i < values.size()-1; i++) {
            String code = values.get(i);
            nginxErrorPageManage.register(code, lastHtml);
        }

        return true;
    }

    @Override
    public boolean doAfterDispatch(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context) {
        return true;
    }

    @Override
    protected String getKey(NginxCommonConfigEntry configParam, NginxRequestDispatchContext context) {
        return "error_page";
    }

    @Override
    public String directiveName() {
        return "error_page";
    }

}
