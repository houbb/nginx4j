package com.github.houbb.nginx4j.support.request.dispatch;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.config.NginxConfig;
import com.github.houbb.nginx4j.config.NginxUserServerLocationConfig;
import com.github.houbb.nginx4j.config.param.INginxParamLifecycleDispatch;
import com.github.houbb.nginx4j.config.param.INginxParamManager;
import com.github.houbb.nginx4j.config.param.LifecycleBaseContext;
import com.github.houbb.nginx4j.config.param.LifecycleDispatchContext;
import com.github.houbb.nginx4j.config.param.impl.dispatch.NginxParamHandleSet;
import com.github.houbb.nginx4j.constant.NginxConfigTypeEnum;
import com.github.houbb.nginx4j.constant.NginxConst;
import com.github.houbb.nginx4j.support.balance.INginxLoadBalanceConfig;
import com.github.houbb.nginx4j.support.balance.NginxLoadBalanceConfig;
import com.github.houbb.nginx4j.support.balance.NginxLoadBalanceDefaultConfig;
import com.github.houbb.nginx4j.support.condition.NginxIf;
import com.github.houbb.nginx4j.support.map.NginxMapDirective;
import com.github.houbb.nginx4j.support.placeholder.INginxPlaceholderManager;
import com.github.houbb.nginx4j.support.request.dispatch.http.NginxRequestDispatches;
import com.github.houbb.nginx4j.support.rewrite.NginxRewriteDirectiveResult;
import com.github.houbb.nginx4j.support.rewrite.NginxRewriteFlagEnum;
import com.github.houbb.nginx4j.support.tryfiles.INginxTryFiles;
import com.github.houbb.nginx4j.support.tryfiles.NginxTryFilesDefault;
import com.github.houbb.nginx4j.util.InnerFileUtil;
import com.github.houbb.nginx4j.util.InnerNginxContextUtil;
import io.netty.handler.codec.http.FullHttpRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


/**
 * 静态资源
 * @author 老马啸西风
 * @since 0.2.0
 */
public class NginxRequestDispatchManager implements NginxRequestDispatch {

    private static final Log logger = LogFactory.getLog(NginxRequestDispatchManager.class);

    /**
     * 内容的分发处理
     *
     * @param context 上下文
     */
    public void dispatch(NginxRequestDispatchContext context) {
        //opt 放在这里统一处理，提前处理之后，再去获得对应的分发类会比较自然一些。
        beforeDispatch(context);

        // tryFiles
        final INginxTryFiles nginxTryFiles = new NginxTryFilesDefault();
        nginxTryFiles.tryFiles(context.getRequest(), context.getNginxConfig(), context);

        final NginxRequestDispatch dispatch = getDispatch(context);

        dispatch.dispatch(context);

        //after
        afterDispatch(context);
    }

    protected NginxRequestDispatch getDispatch(NginxRequestDispatchContext context) {
        final FullHttpRequest requestInfoBo = context.getRequest();
        final NginxConfig nginxConfig = context.getNginxConfig();

        // 消息解析不正确
        /*如果无法解码400*/
        if (!requestInfoBo.decoderResult().isSuccess()) {
            return NginxRequestDispatches.http400();
        }

        //proxy_pass
        final INginxLoadBalanceConfig nginxLoadBalance = new NginxLoadBalanceDefaultConfig();
        NginxLoadBalanceConfig balanceConfig = nginxLoadBalance.buildBalanceConfig(context);
        if(balanceConfig.isNeedProxyPass()) {
            return NginxRequestDispatches.proxyPass();
        }

        // 如果存在 return
        // 301+return 不应该放在这里实现。
        if(context.getNginxReturnResult() != null) {
            return NginxRequestDispatches.httpReturn();
        }

        //301
        final NginxRewriteDirectiveResult rewriteDirectiveResult = context.getNginxRewriteDirectiveResult();
        if(rewriteDirectiveResult.isMatchRewrite()) {
            String rewriteFlag = rewriteDirectiveResult.getRewriteFlag();

            if(NginxRewriteFlagEnum.PERMANENT.getCode().equals(rewriteFlag)) {
                return NginxRequestDispatches.http301();
            }

            if(NginxRewriteFlagEnum.REDIRECT.getCode().equals(rewriteFlag)) {
                return NginxRequestDispatches.http302();
            }
        }

        // 文件
        File targetFile = InnerFileUtil.getTargetFile(requestInfoBo.uri(), context);
        // 是否存在
        if(targetFile.exists()) {
            // 设置文件
            context.setFile(targetFile);

            // 如果是文件夹
            if(targetFile.isDirectory()) {
                return NginxRequestDispatches.fileDir();
            }

            // range ?
            boolean isRangeRequest = isRangeRequest(requestInfoBo, context);
            if(isRangeRequest) {
                return NginxRequestDispatches.fileRange();
            }

            // 文件处理
            return NginxRequestDispatches.file();
        }  else {
            return NginxRequestDispatches.http404();
        }
    }

    /**
     * 是否为范围查询
     * @param request 请求
     * @param context 配置
     * @return 结果
     * @since 0.7.0
     */
    protected boolean isRangeRequest(final FullHttpRequest request, NginxRequestDispatchContext context) {
        // 解析Range头
        String rangeHeader = request.headers().get("Range");
        return StringUtil.isNotEmpty(rangeHeader);
    }

    /**
     * 请求头的统一处理
     * @param context 上下文
     */
    protected void beforeDispatch(final NginxRequestDispatchContext context) {
        logger.info("beforeDispatch context={}", context);

        // v0.17.0 占位符管理类
        final INginxPlaceholderManager placeholderManager = context.getNginxConfig().getNginxPlaceholderManager();
        // 提前处理内置的各种参数
        placeholderManager.beforeDispatch(context);

        // v.22.0 map 的处理，生命周期只能在 dispatch 前吗？后期也许可以拓展的更多？
        final NginxMapDirective mapDirective = context.getNginxConfig().getNginxMapDirective();
        mapDirective.map(context);

        //1. 当前的配置
        NginxUserServerLocationConfig locationConfig = context.getCurrentUserServerLocationConfig();
        if(locationConfig == null) {
            return;
        }

        // 基础变量
        List<NginxCommonConfigEntry> directives = InnerNginxContextUtil.getAllDirectives(context);
        if(CollectionUtil.isEmpty(directives)) {
            return;
        }

        // 处理
        // TODO: 这里为什么会死循环？？？
        for(NginxCommonConfigEntry configParam : directives) {
            final INginxParamManager paramManager = context.getNginxConfig().getNginxParamManager();

            final INginxParamLifecycleDispatch lifecycleDispatch = paramManager.getMatchDispatch(configParam.getName());
            if(lifecycleDispatch == null) {
                continue;
            }

            // 处理
            LifecycleDispatchContext baseContext = new LifecycleDispatchContext();
            baseContext.setContext(context);
            baseContext.setConfigParam(configParam);
            baseContext.setBreakAllFlag(false);

            processNginxCommonConfigEntry(configParam, context, baseContext, new Consumer<LifecycleBaseContext>() {
                @Override
                public void accept(LifecycleBaseContext baseContext) {
                    LifecycleDispatchContext dispatchContext = (LifecycleDispatchContext) baseContext;
                    boolean dispatchResult = lifecycleDispatch.beforeDispatch(dispatchContext);
                }
            }, true);

            if(baseContext.isBreakAllFlag()) {
                logger.info("直接终止处理");
                return;
            }
        }
    }

    private void processNginxCommonConfigEntry(final NginxCommonConfigEntry configParam,
                                               final NginxRequestDispatchContext context,
                                               final LifecycleBaseContext baseContext,
                                               Consumer<LifecycleBaseContext> consumer) {
        processNginxCommonConfigEntry(configParam, context, baseContext, consumer, false);
    }

    private void processNginxCommonConfigEntry(final NginxCommonConfigEntry configParam,
                                               final NginxRequestDispatchContext context,
                                               final LifecycleBaseContext baseContext,
                                               Consumer<LifecycleBaseContext> consumer,
                                               boolean executeIf) {
        // 参数管理类
        final INginxPlaceholderManager placeholderManager = context.getNginxConfig().getNginxPlaceholderManager();

        // 占位符处理
        placeholderHandle(configParam, placeholderManager, context);

        final NginxConfigTypeEnum configTypeEnum = configParam.getType();
        if(NginxConfigTypeEnum.PARAM.equals(configTypeEnum)) {
            consumer.accept(baseContext);
        } else if(NginxConfigTypeEnum.IF.equals(configTypeEnum)){
            if(executeIf) {
                // 判断是否满足条件
                final NginxIf nginxIf = context.getNginxConfig().getNginxIf();
                if(nginxIf.match(configParam, context)) {
                    List<NginxCommonConfigEntry> configEntryList = configParam.getChildren();
                    for(NginxCommonConfigEntry configEntryChild : configEntryList) {
                        // 递归
                        baseContext.setConfigParam(configEntryChild);
                        this.processNginxCommonConfigEntry(configEntryChild, context, baseContext, consumer, executeIf);
                    }
                }
            }
        }
    }


    /**
     * 占位符处理
     *
     * SET 问题，这个是按顺序处理的，所以暂时不用特别考虑
     *
     * @param configParam 配置指令
     * @param placeholderManager 占位符管理类
     * @param context 上下文
     * @since 0.17.0
     */
    protected void placeholderHandle(NginxCommonConfigEntry configParam,
                                     final INginxPlaceholderManager placeholderManager,
                                     final NginxRequestDispatchContext context) {
        String name = configParam.getName();
        if("set".equals(name)) {
            //TODO: 这里直接 return?? 避免死循环？

            NginxParamHandleSet handleSet = new NginxParamHandleSet();
            handleSet.doBeforeDispatch(configParam, context);
            return;
        }

        // 跳过 if
        if("if".equals(name)) {
            return;
        }

        // name 暂时不添加 $ 处理
        // value
        String value = configParam.getValue();
        String actualValue = getPlaceholderStr(value, placeholderManager, context);
        configParam.setValue(actualValue);

        // list
        List<String> valueList = configParam.getValues();
        List<String> newValueList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(valueList)) {
            for(String valueItem : valueList) {
                String actualValueItem = getPlaceholderStr(valueItem, placeholderManager, context);
                newValueList.add(actualValueItem);
            }

            configParam.setValues(newValueList);
        }

        // 结束
    }

    /**
     * 获取占位符对应的值
     * @param value 原始值
     * @param placeholderManager 管理类
     * @param context 上下文
     * @return 结果
     */
    protected String getPlaceholderStr(String value,
                                       final INginxPlaceholderManager placeholderManager,
                                       final NginxRequestDispatchContext context) {
        // value
        if(value.startsWith(NginxConst.PLACEHOLDER_PREFIX)) {
            Object actualValue = placeholderManager.getValue(context, value);
            if(actualValue == null) {
                logger.error("占位符未初始化 value={}", value);
                return value;
            }

            // 设置值
            String actualValueStr = actualValue.toString();
            logger.debug("占位符替换 value={}, actualValueStr={}", value, actualValueStr);
            return actualValueStr;
        }

        // 原始值
        return value;
    }

    protected void afterDispatch(final NginxRequestDispatchContext context) {
        //1. 当前的配置
        NginxUserServerLocationConfig locationConfig = context.getCurrentUserServerLocationConfig();
        if(locationConfig == null) {
            return;
        }

        List<NginxCommonConfigEntry> directives = InnerNginxContextUtil.getAllDirectives(context);
        if(CollectionUtil.isEmpty(directives)) {
            return;
        }

        // 处理
        for(NginxCommonConfigEntry configParam : directives) {
            LifecycleDispatchContext baseContext = new LifecycleDispatchContext();
            baseContext.setContext(context);
            baseContext.setConfigParam(configParam);

            processNginxCommonConfigEntry(configParam, context, baseContext, new Consumer<LifecycleBaseContext>() {
                @Override
                public void accept(LifecycleBaseContext baseContext) {
                    final INginxParamManager paramManager = context.getNginxConfig().getNginxParamManager();
                    List<INginxParamLifecycleDispatch> dispatchList = paramManager.getDispatchList();
                    if(CollectionUtil.isNotEmpty(dispatchList)) {
                        for(INginxParamLifecycleDispatch dispatch : dispatchList) {
                            LifecycleDispatchContext dispatchContext = (LifecycleDispatchContext) baseContext;
                            if(dispatch.match(dispatchContext)) {
                                boolean dispatchResult = dispatch.afterDispatch(dispatchContext);
                                if(!dispatchResult) {
                                    break;
                                }
                            }
                        }
                    }
                }
            });
        }
    }


}
