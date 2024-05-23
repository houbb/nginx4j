# 项目简介

```
 /\_/\  
( o.o ) 
 > ^ <
```

nginx4j 是 nginx 的简单 java 版本。

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.houbb/nginx4j/badge.svg)](http://mvnrepository.com/artifact/com.github.houbb/nginx4j)
[![Build Status](https://www.travis-ci.org/houbb/nginx4j.svg?branch=master)](https://www.travis-ci.org/houbb/nginx4j?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/houbb/nginx4j/badge.svg?branch=master)](https://coveralls.io/github/houbb/nginx4j?branch=master)

# 特性

- 简单的启动实现/netty 支持

- servlet 支持

- 静态网页支持

- filter/listener 支持

- wars 支持

# 变更日志

> [变更日志](CHANGE_LOG.md)

# 快速开始

## maven 依赖

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>nginx4j</artifactId>
    <version>0.1.0</version>
</dependency>
```

## 启动测试

运行测试类 `nginx4jBootstrapMain#main`

```java
nginx4jBootstrap bootstrap = new nginx4jBootstrap();
bootstrap.start();
```

启动日志：

```
[INFO] [2024-04-03 11:09:15.178] [main] [c.g.h.m.s.s.WebXmlServletManager.register] - [nginx4j] register servlet, url=/my, servlet=com.github.houbb.nginx4j.support.servlet.Mynginx4jHttpServlet
[INFO] [2024-04-03 11:09:15.180] [Thread-0] [c.g.h.m.b.nginx4jBootstrap.startSync] - [nginx4j] start listen on port 8080
[INFO] [2024-04-03 11:09:15.180] [Thread-0] [c.g.h.m.b.nginx4jBootstrap.startSync] - [nginx4j] visit url http://127.0.0.1:8080
```

页面访问：[http://127.0.0.1:8080](http://127.0.0.1:8080)

响应：

```
http://127.0.0.1:8080
```

## 测试

servlet: http://127.0.0.1:8080/my

html: http://127.0.0.1:8080/index.html

# ROAD-MAP

- [x] servlet 标准支持
- [x] 请求线程池支持
- [x] NIO 实现
- [x] netty 实现
- [x] 加载 war 包
- [x] listener 的实现
- [] error/welcome 页面？
- [] printWriter 等兼容不够优雅
- [] 内嵌支持？
- [] session
- [] 注解的解析支持
