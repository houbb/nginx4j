package com.github.houbb.nginx4j.config;

import com.github.houbb.nginx4j.config.parser.INginxParserBlock;
import com.github.houbb.nginx4j.config.parser.INginxParserConfig;
import com.github.houbb.nginx4j.config.parser.INginxParserParam;
import com.github.houbb.nginx4j.config.parser.NginxParserConfigDefault;

import java.io.IOException;
import java.util.List;

public class NginxConfigDefaultParserTest {

    public static void main(String[] args) throws IOException {
        INginxParserConfig conf = new NginxParserConfigDefault("D:\\github\\nginx4j\\src\\test\\resources\\nginx-demo.conf");
        conf.load();

        // 基本信息
        INginxParserParam pidParam = conf.findParam("pid");
        System.out.println(pidParam.getValue());;

        INginxParserParam worker_connectionsParam = conf.findParam("events", "worker_connections");
        System.out.println(worker_connectionsParam.getValue());

        // 模块下多级
        INginxParserParam listen = conf.findParam("http", "server", "listen"); // 示例2
        System.out.println(listen.getValue()); // "8889"

        // 首先获取 block
        List<INginxParserBlock> servers = conf.findBlocks("http", "server"); // 示例3
        for (INginxParserBlock entry : servers) {
            String name = entry.getName();

            // value
            String value = entry.findParam("listen").getValue(); // 第一次迭代返回"on"，第二次迭代返回"off"
            System.out.println(name + "---" + value);
        }
    }

}
