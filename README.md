# 项目简介

```
 /\_/\  
( o.o ) 
 > ^ <
```

nginx4j 是基于 netty 实现的 nginx 的java 版本。

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.houbb/nginx4j/badge.svg)](http://mvnrepository.com/artifact/com.github.houbb/nginx4j)
[![Build Status](https://www.travis-ci.org/houbb/nginx4j.svg?branch=master)](https://www.travis-ci.org/houbb/nginx4j?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/houbb/nginx4j/badge.svg?branch=master)](https://coveralls.io/github/houbb/nginx4j?branch=master)

如果你想知道 servlet 如何处理的，可以参考我的另一个项目：

>  [简易版 tomcat](https://github.com/houbb/minicat)

# 特性

- 基于 netty 的 nio 高性能

- 静态网资源支持

- 默认 index.html

- 404

- 文件夹的自动索引

- 大文件的下载支持

# 变更日志

> [变更日志](CHANGE_LOG.md)

# 快速开始

## maven 依赖

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>nginx4j</artifactId>
    <version>0.8.0</version>
</dependency>
```

## 启动测试

```java
Nginx4jBs.newInstance().init().start();
```

启动日志：

```
[DEBUG] [2024-05-24 23:40:37.573] [main] [c.g.h.l.i.c.LogFactory.setImplementation] - Logging initialized using 'class com.github.houbb.log.integration.adaptors.stdout.StdOutExImpl' adapter.
[INFO] [2024-05-24 23:40:37.576] [main] [c.g.h.n.s.s.NginxServerSocket.start] - [Nginx4j] listen on port=8080
```

页面访问：[http://127.0.0.1:8080](http://127.0.0.1:8080)

响应：

```
Welcome to nginx4j!
```

## 其他页面

访问 [http://localhost:8080/1.txt](http://localhost:8080/1.txt)

将返回对应的文件内容：

```
hello nginx4j!
```

## 不存在

http://localhost:8080/asdfasdf

返回：

```
404 Not Found: The requested resource was not found on this server.
```

# ROAD-MAP

## static

- [x] 基于 netty 实现
- [x] index.html
- [x] 404 403 等常见页面
- [x] 基于 netty 的请求/响应封装
- [x] 各种文件类型的请求头处理
- [x] 文件夹的自动索引
- [x] 大文件的分段传输？chunk
- [x] range 范围请求
- [x] 请求的压缩 gzip 等常见压缩算法
- [ ] 常见请求头/headers/cookie 的处理
- [ ] rewrite 请求头信息重写
- [ ] ETag 和 Last-Modified
- [ ] CORS
- [ ] http2
- [ ] http3
- [ ] ssl/https

## 反向代理

- [ ] reverse-proxy
- [ ] load-balance

## system

- [ ] cache
- [ ] filter 过滤器
- [ ] listener 监听器
- [ ] rateLimit 限流