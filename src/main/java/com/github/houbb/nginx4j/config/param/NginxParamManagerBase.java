package com.github.houbb.nginx4j.config.param;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础实现
 * @since 0.19.0
 */
public class NginxParamManagerBase implements INginxParamManager {

    private static final Log logger = LogFactory.getLog(NginxParamManagerBase.class);

    private final List<INginxParamLifecycleComplete> completeList = new ArrayList<>();
    private final List<INginxParamLifecycleDispatch> dispatchList = new ArrayList<>();
    private final Map<String, INginxParamLifecycleDispatch> dispatchMap = new HashMap<>();
    private final List<INginxParamLifecycleWrite> writeList = new ArrayList<>();

    @Override
    public INginxParamLifecycleDispatch getMatchDispatch(String name) {
        return dispatchMap.get(name);
    }

    @Override
    public INginxParamManager registerDispatch(INginxParamLifecycleDispatch dispatch) {
        dispatchMap.put(dispatch.directiveName(), dispatch);
        return this;
    }

    @Override
    public INginxParamManager registerComplete(INginxParamLifecycleComplete complete) {
        completeList.add(complete);
        return this;
    }

    @Override
    public INginxParamManager registerWrite(INginxParamLifecycleWrite write) {
        writeList.add(write);
        return this;
    }

    @Override
    public List<INginxParamLifecycleComplete> getCompleteList() {
        return completeList;
    }

    @Override
    public List<INginxParamLifecycleDispatch> getDispatchList() {
        return dispatchList;
    }

    @Override
    public List<INginxParamLifecycleWrite> getWriteList() {
        return writeList;
    }

}
