package com.github.houbb.nginx4j.support.request.dispatch.http;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.nginx4j.config.NginxCommonConfigParam;
import com.github.houbb.nginx4j.config.NginxUserServerLocationConfig;
import com.github.houbb.nginx4j.config.param.INginxParamHandle;
import com.github.houbb.nginx4j.config.param.INginxParamManager;
import com.github.houbb.nginx4j.constant.NginxConst;
import com.github.houbb.nginx4j.exception.Nginx4jException;
import com.github.houbb.nginx4j.support.placeholder.INginxPlaceholderManager;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatch;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

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
        // 参数管理类
        final INginxParamManager paramManager = context.getNginxConfig().getNginxParamManager();

        //1. 当前的配置
        NginxUserServerLocationConfig locationConfig = context.getCurrentUserServerLocationConfig();
        if(locationConfig == null) {
            return;
        }

        List<NginxCommonConfigParam> directives = locationConfig.getDirectives();
        if(CollectionUtil.isEmpty(directives)) {
            return;
        }

        // 处理
        for(NginxCommonConfigParam configParam : directives) {
            List<INginxParamHandle> handleList = paramManager.paramHandleList(configParam, context);
            if(CollectionUtil.isNotEmpty(handleList)) {
                for(INginxParamHandle paramHandle : handleList) {
                    paramHandle.afterDispatch(configParam, context);
                }
            }
        }
    }


    /**
     * 请求头的统一处理
     * @param context 上下文
     */
    protected void beforeDispatch(final NginxRequestDispatchContext context) {
        // 参数管理类
        final INginxParamManager paramManager = context.getNginxConfig().getNginxParamManager();

        // v0.17.0 占位符管理类
        final INginxPlaceholderManager placeholderManager = context.getNginxConfig().getNginxPlaceholderManager();
        // 提前处理内置的各种参数
        placeholderManager.init(context);


        //1. 当前的配置
        NginxUserServerLocationConfig locationConfig = context.getCurrentUserServerLocationConfig();
        if(locationConfig == null) {
            return;
        }

        List<NginxCommonConfigParam> directives = locationConfig.getDirectives();
        if(CollectionUtil.isEmpty(directives)) {
            return;
        }

        // 处理
        for(NginxCommonConfigParam configParam : directives) {
            // 占位符处理
            placeholderHandle(configParam, placeholderManager, context);

            List<INginxParamHandle> handleList = paramManager.paramHandleList(configParam, context);
            if(CollectionUtil.isNotEmpty(handleList)) {
                for(INginxParamHandle paramHandle : handleList) {
                    paramHandle.beforeDispatch(configParam, context);
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
    protected void placeholderHandle(NginxCommonConfigParam configParam,
                                     final INginxPlaceholderManager placeholderManager,
                                     final NginxRequestDispatchContext context) {
        String name = configParam.getName();
        if(name.equals("set")) {
            logger.warn("暂时不处理 set 指令对应的操作符替换，后续可考虑改进。");
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

        //1. 当前的配置
        NginxUserServerLocationConfig locationConfig = context.getCurrentUserServerLocationConfig();
        if(locationConfig == null) {
            return;
        }

        List<NginxCommonConfigParam> directives = locationConfig.getDirectives();
        if(CollectionUtil.isEmpty(directives)) {
            return;
        }

        // 处理
        for(NginxCommonConfigParam configParam : directives) {
            List<INginxParamHandle> handleList = paramManager.paramHandleList(configParam, context);
            if(CollectionUtil.isNotEmpty(handleList)) {
                for(INginxParamHandle paramHandle : handleList) {
                    paramHandle.beforeWrite(configParam, ctx, object, context);
                }
            }
        }
    }

    protected void afterWrite(final ChannelHandlerContext ctx,
                                      final Object object,
                                      final NginxRequestDispatchContext context) {
        // 参数管理类
        final INginxParamManager paramManager = context.getNginxConfig().getNginxParamManager();

        //1. 当前的配置
        NginxUserServerLocationConfig locationConfig = context.getCurrentUserServerLocationConfig();
        if(locationConfig == null) {
            return;
        }

        List<NginxCommonConfigParam> directives = locationConfig.getDirectives();
        if(CollectionUtil.isEmpty(directives)) {
            return;
        }

        // 处理
        for(NginxCommonConfigParam configParam : directives) {
            List<INginxParamHandle> handleList = paramManager.paramHandleList(configParam, context);
            if(CollectionUtil.isNotEmpty(handleList)) {
                for(INginxParamHandle paramHandle : handleList) {
                    paramHandle.afterWrite(configParam, ctx, object, context);
                }
            }
        }
    }

}
