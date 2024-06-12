package com.github.houbb.nginx4j.support.request.dispatch.http;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxCommonConfigEntry;
import com.github.houbb.nginx4j.config.NginxUserConfig;
import com.github.houbb.nginx4j.config.NginxUserHttpConfig;
import com.github.houbb.nginx4j.config.NginxUserServerLocationConfig;
import com.github.houbb.nginx4j.config.param.*;
import com.github.houbb.nginx4j.config.param.impl.dispatch.NginxParamHandleSet;
import com.github.houbb.nginx4j.constant.NginxConfigTypeEnum;
import com.github.houbb.nginx4j.constant.NginxConst;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.condition.NginxIf;
import com.github.houbb.nginx4j.support.placeholder.INginxPlaceholderManager;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatch;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractNginxRequestDispatch implements NginxRequestDispatch {

    private static final Log logger = LogFactory.getLog(AbstractNginxRequestDispatch.class);

    public abstract void doDispatch(final NginxRequestDispatchContext context);

    /**
     * 内容的分发处理
     * <p>
     * //1. root
     * //2. dir
     * //3. 小文件
     * //4. 大文件
     *
     * @param context 上下文
     */
    public void dispatch(final NginxRequestDispatchContext context) {
        beforeDispatch(context);

        // 统一的处理
        doDispatch(context);

        // 统一的处理
        afterDispatch(context);
    }

    protected void afterDispatch(final NginxRequestDispatchContext context) {
        //1. 当前的配置
        NginxUserServerLocationConfig locationConfig = context.getCurrentUserServerLocationConfig();
        if(locationConfig == null) {
            return;
        }

        List<NginxCommonConfigEntry> directives = getAllDirectives(context);
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
                                dispatch.afterDispatch(dispatchContext);
                            }
                        }
                    }
                }
            });
        }
    }


    /**
     * 请求头的统一处理
     * @param context 上下文
     */
    protected void beforeDispatch(final NginxRequestDispatchContext context) {
        // v0.17.0 占位符管理类
        final INginxPlaceholderManager placeholderManager = context.getNginxConfig().getNginxPlaceholderManager();
        // 提前处理内置的各种参数
        placeholderManager.beforeDispatch(context);


        //1. 当前的配置
        NginxUserServerLocationConfig locationConfig = context.getCurrentUserServerLocationConfig();
        if(locationConfig == null) {
            return;
        }


        // 基础变量

        List<NginxCommonConfigEntry> directives = getAllDirectives(context);
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
                                dispatch.beforeDispatch(dispatchContext);
                            }
                        }
                    }
                }
            }, true);
        }
    }

    private List<NginxCommonConfigEntry> getAllDirectives(final NginxRequestDispatchContext context) {
        List<NginxCommonConfigEntry> resultList = new ArrayList<>();

        NginxUserConfig nginxUserConfig = context.getNginxConfig().getNginxUserConfig();

        // 基础
        if(CollectionUtil.isNotEmpty((nginxUserConfig.getConfigEntryList()))) {
            resultList.addAll(nginxUserConfig.getConfigEntryList());
        }

        // http
        NginxUserHttpConfig nginxUserHttpConfig = nginxUserConfig.getHttpConfig();
        List<NginxCommonConfigEntry> httpConfigEntryList = nginxUserHttpConfig.getConfigEntryList();
        if(CollectionUtil.isNotEmpty(httpConfigEntryList)) {
            resultList.addAll(httpConfigEntryList);
        }

        //server
        if(CollectionUtil.isNotEmpty((context.getCurrentNginxUserServerConfig().getConfigEntryList()))) {
            resultList.addAll(context.getCurrentNginxUserServerConfig().getConfigEntryList());
        }


        //location
        NginxUserServerLocationConfig locationConfig = context.getCurrentUserServerLocationConfig();
        List<NginxCommonConfigEntry> directives = locationConfig.getConfigEntryList();
        if(CollectionUtil.isNotEmpty(directives)) {
            resultList.addAll(directives);
        }

        return resultList;
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


    protected void processForSet(NginxCommonConfigEntry configParam) {

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
                throw new Nginx4jException("占位符未初始化" + value);
            }

            // 设置值
            String actualValueStr = actualValue.toString();
            logger.debug("占位符替换 value={}, actualValueStr={}", value, actualValueStr);
            return actualValueStr;
        }

        // 原始值
        return value;
    }

    /**
     * 写入并且刷新，便于统一的管理
     *
     * @param ctx   channel 上下文
     * @param object 对象
     * @param context 上下文
     * @since 0.16.0
     * @return channel
     */
    protected ChannelFuture writeAndFlush(final ChannelHandlerContext ctx,
                                          final Object object,
                                          final NginxRequestDispatchContext context) {
        beforeWriteAndFlush(ctx, object, context);

        // 基本处理逻辑
        ChannelFuture lastContentFuture = ctx.writeAndFlush(object);

        afterWriteAndFlush(ctx, object, context);

        return lastContentFuture;
    }

    protected void beforeWriteAndFlush(final ChannelHandlerContext ctx,
                                       final Object object,
                                       final NginxRequestDispatchContext context) {
        this.beforeWrite(ctx, object, context);
        ctx.flush();
    }

    protected void afterWriteAndFlush(final ChannelHandlerContext ctx,
                                       final Object object,
                                       final NginxRequestDispatchContext context) {
        this.afterWrite(ctx, object, context);
        ctx.flush();
    }


    /**
     * 写入并且刷新，便于统一的管理
     *
     * @param ctx   channel 上下文
     * @param object 对象
     * @param context 上下文
     * @since 0.16.0
     * @return channel
     */
    protected ChannelFuture write(final ChannelHandlerContext ctx,
                                          final Object object,
                                          final NginxRequestDispatchContext context) {
        // 设置响应
        if(object instanceof HttpResponse) {
            HttpResponse httpResponse = (HttpResponse) object;
            context.setHttpResponse(httpResponse);
        }

        beforeWrite(ctx, object, context);

        // 基本处理逻辑
        ChannelFuture lastContentFuture = ctx.writeAndFlush(object);

        afterWrite(ctx, object, context);

        return lastContentFuture;
    }



    protected void beforeWrite(final ChannelHandlerContext ctx,
                                       final Object object,
                                       final NginxRequestDispatchContext context) {
        // 参数管理类
        final INginxParamManager paramManager = context.getNginxConfig().getNginxParamManager();

        // v0.19.0 占位符管理类
        final INginxPlaceholderManager placeholderManager = context.getNginxConfig().getNginxPlaceholderManager();
        // 提前处理内置的各种参数
        placeholderManager.beforeWrite(context);

        //1. 当前的配置
        NginxUserServerLocationConfig locationConfig = context.getCurrentUserServerLocationConfig();
        if(locationConfig == null) {
            return;
        }

        List<NginxCommonConfigEntry> directives = getAllDirectives(context);
        if(CollectionUtil.isEmpty(directives)) {
            return;
        }

        // 处理
        for(NginxCommonConfigEntry configParam : directives) {
            LifecycleWriteContext baseContext = new LifecycleWriteContext();
            baseContext.setContext(context);
            baseContext.setConfigParam(configParam);
            baseContext.setCtx(ctx);
            baseContext.setObject(object);

            processNginxCommonConfigEntry(configParam, context, baseContext, new Consumer<LifecycleBaseContext>() {
                @Override
                public void accept(LifecycleBaseContext baseContext) {
                    final INginxParamManager paramManager = context.getNginxConfig().getNginxParamManager();
                    List<INginxParamLifecycleWrite> writeList = paramManager.getWriteList();
                    if(CollectionUtil.isNotEmpty(writeList)) {
                        for(INginxParamLifecycleWrite write : writeList) {
                            LifecycleWriteContext writeContext = (LifecycleWriteContext) baseContext;
                            if(write.match(writeContext)) {
                                write.beforeWrite(writeContext);
                            }
                        }
                    }
                }
            });
        }
    }

    protected void afterWrite(final ChannelHandlerContext ctx,
                                      final Object object,
                                      final NginxRequestDispatchContext context) {
        //1. 当前的配置
        NginxUserServerLocationConfig locationConfig = context.getCurrentUserServerLocationConfig();
        if (locationConfig == null) {
            return;
        }

        List<NginxCommonConfigEntry> directives = getAllDirectives(context);
        if (CollectionUtil.isEmpty(directives)) {
            return;
        }

        // 处理
        for (NginxCommonConfigEntry configParam : directives) {
            LifecycleWriteContext baseContext = new LifecycleWriteContext();
            baseContext.setContext(context);
            baseContext.setConfigParam(configParam);
            baseContext.setCtx(ctx);
            baseContext.setObject(object);

            processNginxCommonConfigEntry(configParam, context, baseContext, new Consumer<LifecycleBaseContext>() {
                @Override
                public void accept(LifecycleBaseContext baseContext) {
                    final INginxParamManager paramManager = context.getNginxConfig().getNginxParamManager();
                    List<INginxParamLifecycleWrite> writeList = paramManager.getWriteList();
                    if (CollectionUtil.isNotEmpty(writeList)) {
                        for (INginxParamLifecycleWrite write : writeList) {
                            LifecycleWriteContext writeContext = (LifecycleWriteContext) baseContext;
                            if(write.match(writeContext)) {
                                write.beforeWrite(writeContext);
                            }
                        }
                    }
                }
            });
        }
    }


    /**
     * 完成处理
     *
     * @param ctx   channel 上下文
     * @param object 对象
     * @param context 上下文
     * @since 0.19.0
     */
    protected void complete(final ChannelHandlerContext ctx,
                                     final Object object,
                                     final NginxRequestDispatchContext context) {
        beforeComplete(ctx, object, context);

        // 基本处理逻辑
//        ChannelFuture lastContentFuture = ctx.writeAndFlush(object);

        afterComplete(ctx, object, context);
    }

    protected void beforeComplete(final ChannelHandlerContext ctx,
                               final Object object,
                               final NginxRequestDispatchContext context) {
        // 参数管理类
        final INginxParamManager paramManager = context.getNginxConfig().getNginxParamManager();

        // v0.19.0 占位符管理类
        final INginxPlaceholderManager placeholderManager = context.getNginxConfig().getNginxPlaceholderManager();
        // 提前处理内置的各种参数
        placeholderManager.beforeComplete(context);

        //1. 当前的配置
        NginxUserServerLocationConfig locationConfig = context.getCurrentUserServerLocationConfig();
        if(locationConfig == null) {
            return;
        }

        List<NginxCommonConfigEntry> directives = getAllDirectives(context);
        if(CollectionUtil.isEmpty(directives)) {
            return;
        }

        // 处理
        for (NginxCommonConfigEntry configParam : directives) {
            LifecycleCompleteContext baseContext = new LifecycleCompleteContext();
            baseContext.setContext(context);
            baseContext.setConfigParam(configParam);
            baseContext.setCtx(ctx);
            baseContext.setObject(object);

            processNginxCommonConfigEntry(configParam, context, baseContext, new Consumer<LifecycleBaseContext>() {
                @Override
                public void accept(LifecycleBaseContext baseContext) {
                    final INginxParamManager paramManager = context.getNginxConfig().getNginxParamManager();
                    List<INginxParamLifecycleComplete> completeList = paramManager.getCompleteList();
                    if (CollectionUtil.isNotEmpty(completeList)) {
                        for (INginxParamLifecycleComplete complete : completeList) {
                            LifecycleCompleteContext completeContext = (LifecycleCompleteContext) baseContext;
                            if(complete.match(completeContext)) {
                                complete.beforeComplete(completeContext);
                            }
                        }
                    }
                }
            });
        }
    }

    protected void afterComplete(final ChannelHandlerContext ctx,
                              final Object object,
                              final NginxRequestDispatchContext context) {
        // 参数管理类
        final INginxParamManager paramManager = context.getNginxConfig().getNginxParamManager();

        //1. 当前的配置
        NginxUserServerLocationConfig locationConfig = context.getCurrentUserServerLocationConfig();
        if(locationConfig == null) {
            return;
        }

        List<NginxCommonConfigEntry> directives = getAllDirectives(context);
        if(CollectionUtil.isEmpty(directives)) {
            return;
        }

        // 处理
        for (NginxCommonConfigEntry configParam : directives) {
            LifecycleCompleteContext baseContext = new LifecycleCompleteContext();
            baseContext.setContext(context);
            baseContext.setConfigParam(configParam);
            baseContext.setCtx(ctx);
            baseContext.setObject(object);

            processNginxCommonConfigEntry(configParam, context, baseContext, new Consumer<LifecycleBaseContext>() {
                @Override
                public void accept(LifecycleBaseContext baseContext) {
                    final INginxParamManager paramManager = context.getNginxConfig().getNginxParamManager();
                    List<INginxParamLifecycleComplete> completeList = paramManager.getCompleteList();
                    if (CollectionUtil.isNotEmpty(completeList)) {
                        for (INginxParamLifecycleComplete complete : completeList) {
                            LifecycleCompleteContext completeContext = (LifecycleCompleteContext) baseContext;
                            if(complete.match(completeContext)) {
                                complete.beforeComplete(completeContext);
                            }
                        }
                    }
                }
            });
        }
    }

}
