package com.github.houbb.nginx4j.config;

import com.github.odiszapc.nginxparser.NgxBlock;
import com.github.odiszapc.nginxparser.NgxConfig;
import com.github.odiszapc.nginxparser.NgxEntry;
import com.github.odiszapc.nginxparser.NgxParam;

import java.io.IOException;
import java.util.List;

public class NginxConfigParserTest {

    public static void main(String[] args) throws IOException {
        NgxConfig conf = NgxConfig.read("D:\\github\\nginx4j\\src\\test\\resources\\nginx-demo.conf");

        // 基本信息
        NgxParam pidParam = conf.findParam("pid");
        System.out.println(pidParam.getValue());;

        NgxParam worker_connectionsParam = conf.findParam("events", "worker_connections");
        System.out.println(worker_connectionsParam.getValue());

        // 模块下多级
        NgxParam listen = conf.findParam("http", "server", "listen"); // 示例2
        System.out.println(listen.getValue()); // "8889"

        // 首先获取 block
        List<NgxEntry> servers = conf.findAll(NgxConfig.BLOCK, "http", "server"); // 示例3
        for (NgxEntry entry : servers) {
            NgxBlock ngxBlock = (NgxBlock) entry;
            String name = ngxBlock.getName();

            // value
            String value = ngxBlock.findParam("listen").getValue(); // 第一次迭代返回"on"，第二次迭代返回"off"
            System.out.println(name + "---" + value);
        }
    }

}
