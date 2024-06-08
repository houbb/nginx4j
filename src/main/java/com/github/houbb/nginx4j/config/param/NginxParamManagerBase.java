package com.github.houbb.nginx4j.config.param;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxUserConfigParam;
import com.github.houbb.nginx4j.support.handler.NginxNettyServerHandler;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

import java.util.ArrayList;
import java.util.List;

public class NginxParamManagerBase implements INginxParamManager {

    private static final Log logger = LogFactory.getLog(NginxParamManagerBase.class);

    protected static final List<INginxParamHandle> NGINX_PARAM_HANDLES = new ArrayList<>();

    @Override
    public INginxParamManager register(INginxParamHandle nginxParamHandle) {
        ArgUtil.notNull(nginxParamHandle, "nginxParamHandle");

        NGINX_PARAM_HANDLES.add(nginxParamHandle);

        logger.info("NginxParamManagerBase register={}", nginxParamHandle.getClass().getName());

        return this;
    }

    public List<INginxParamHandle> paramHandleList() {
        return NGINX_PARAM_HANDLES;
    }

    @Override
    public List<INginxParamHandle> paramHandleList(NginxUserConfigParam configParam, NginxRequestDispatchContext context) {
        List<INginxParamHandle> resultList = new ArrayList<>();

        //CACHE 可以以 key 为准
        for(INginxParamHandle paramHandle : NGINX_PARAM_HANDLES) {
            if(paramHandle.match(configParam, context)) {
                resultList.add(paramHandle);
            }
        }

        return resultList;
    }

}
