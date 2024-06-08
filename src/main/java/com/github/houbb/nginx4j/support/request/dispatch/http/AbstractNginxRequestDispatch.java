package com.github.houbb.nginx4j.support.request.dispatch.http;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.nginx4j.config.NginxUserConfigParam;
import com.github.houbb.nginx4j.config.NginxUserServerLocationConfig;
import com.github.houbb.nginx4j.config.param.INginxParamHandle;
import com.github.houbb.nginx4j.config.param.INginxParamManager;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatch;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class AbstractNginxRequestDispatch implements NginxRequestDispatch {

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

        List<NginxUserConfigParam> directives = locationConfig.getDirectives();
        if(CollectionUtil.isEmpty(directives)) {
            return;
        }

        // 处理
        for(NginxUserConfigParam configParam : directives) {
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

        //1. 当前的配置
        NginxUserServerLocationConfig locationConfig = context.getCurrentUserServerLocationConfig();
        if(locationConfig == null) {
            return;
        }

        List<NginxUserConfigParam> directives = locationConfig.getDirectives();
        if(CollectionUtil.isEmpty(directives)) {
            return;
        }

        // 处理
        for(NginxUserConfigParam configParam : directives) {
            List<INginxParamHandle> handleList = paramManager.paramHandleList(configParam, context);
            if(CollectionUtil.isNotEmpty(handleList)) {
                for(INginxParamHandle paramHandle : handleList) {
                    paramHandle.beforeDispatch(configParam, context);
                }
            }
        }
    }

    /**
     * 写入并且刷新，便于统一的管理
     *
     * @param ctx   channel 上下文
     * @param object 对象
     * @param context 上下文
     * @since 0.16.0
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

        List<NginxUserConfigParam> directives = locationConfig.getDirectives();
        if(CollectionUtil.isEmpty(directives)) {
            return;
        }

        // 处理
        for(NginxUserConfigParam configParam : directives) {
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

        List<NginxUserConfigParam> directives = locationConfig.getDirectives();
        if(CollectionUtil.isEmpty(directives)) {
            return;
        }

        // 处理
        for(NginxUserConfigParam configParam : directives) {
            List<INginxParamHandle> handleList = paramManager.paramHandleList(configParam, context);
            if(CollectionUtil.isNotEmpty(handleList)) {
                for(INginxParamHandle paramHandle : handleList) {
                    paramHandle.afterWrite(configParam, ctx, object, context);
                }
            }
        }
    }

}
