package com.github.houbb.nginx4j.config;

import com.github.houbb.nginx4j.support.index.NginxIndexContent;
import com.github.houbb.nginx4j.support.request.convert.NginxRequestConvertor;
import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatch;
import com.github.houbb.nginx4j.support.response.NginxResponse;

import java.util.List;

public class NginxConfig {

    /**
     * 文件编码
     */
    private String charset;


    /**
     * http 服务监听端口
     */
    private int httpServerListen;

    /**
     * 根路径
     */
    private String httpServerRoot;

    /**
     * 欢迎路径
     */
    private List<String> httpServerIndexList;

    private NginxIndexContent nginxIndexContent;

    private NginxRequestDispatch nginxRequestDispatch;

    private NginxRequestConvertor nginxRequestConvertor;

    private NginxResponse nginxResponse;

    public NginxResponse getNginxResponse() {
        return nginxResponse;
    }

    public void setNginxResponse(NginxResponse nginxResponse) {
        this.nginxResponse = nginxResponse;
    }

    public NginxRequestConvertor getNginxRequestConvertor() {
        return nginxRequestConvertor;
    }

    public void setNginxRequestConvertor(NginxRequestConvertor nginxRequestConvertor) {
        this.nginxRequestConvertor = nginxRequestConvertor;
    }

    public NginxRequestDispatch getNginxRequestDispatch() {
        return nginxRequestDispatch;
    }

    public void setNginxRequestDispatch(NginxRequestDispatch nginxRequestDispatch) {
        this.nginxRequestDispatch = nginxRequestDispatch;
    }

    public NginxIndexContent getNginxIndexContent() {
        return nginxIndexContent;
    }

    public void setNginxIndexContent(NginxIndexContent nginxIndexContent) {
        this.nginxIndexContent = nginxIndexContent;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public int getHttpServerListen() {
        return httpServerListen;
    }

    public void setHttpServerListen(int httpServerListen) {
        this.httpServerListen = httpServerListen;
    }

    public String getHttpServerRoot() {
        return httpServerRoot;
    }

    public void setHttpServerRoot(String httpServerRoot) {
        this.httpServerRoot = httpServerRoot;
    }

    public List<String> getHttpServerIndexList() {
        return httpServerIndexList;
    }

    public void setHttpServerIndexList(List<String> httpServerIndexList) {
        this.httpServerIndexList = httpServerIndexList;
    }

    @Override
    public String toString() {
        return "NginxConfig{" +
                "charset='" + charset + '\'' +
                ", httpServerListen=" + httpServerListen +
                ", httpServerRoot='" + httpServerRoot + '\'' +
                ", httpServerIndexList=" + httpServerIndexList +
                '}';
    }

}
