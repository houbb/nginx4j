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

- gzip 压缩

- sendfile 零拷贝特性

- http keep-alive 特性

## 拓展阅读

[从零手写实现 nginx-01-为什么不能有 java 版本的 nginx?](https://houbb.github.io/2018/11/22/nginx-write-01-how-to)

[从零手写实现 nginx-02-nginx 的核心能力](https://houbb.github.io/2018/11/22/nginx-write-02-basic-http)

[从零手写实现 nginx-03-nginx 基于 Netty 实现](https://houbb.github.io/2018/11/22/nginx-write-03-basic-http-netty)

[从零手写实现 nginx-04-基于 netty http 出入参优化处理](https://houbb.github.io/2018/11/22/nginx-write-04-netty-http-optimize)

[从零手写实现 nginx-05-MIME类型（Multipurpose Internet Mail Extensions，多用途互联网邮件扩展类型）](https://houbb.github.io/2018/11/22/nginx-write-05-mime-type)

[从零手写实现 nginx-06-文件夹自动索引](https://houbb.github.io/2018/11/22/nginx-write-06-dir-list)

[从零手写实现 nginx-07-大文件下载](https://houbb.github.io/2018/11/22/nginx-write-07-big-file)

[从零手写实现 nginx-08-范围查询](https://houbb.github.io/2018/11/22/nginx-write-08-range)

[从零手写实现 nginx-09-文件压缩](https://houbb.github.io/2018/11/22/nginx-write-09-comparess)

[从零手写实现 nginx-10-sendfile 零拷贝](https://houbb.github.io/2018/11/22/nginx-write-10-sendfile)

[从零手写实现 nginx-11-file+range 合并](https://houbb.github.io/2018/11/22/nginx-write-11-rile-and-range-merge)

# 变更日志

> [变更日志](CHANGE_LOG.md)

# 快速开始

## maven 依赖

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>nginx4j</artifactId>
    <version>0.11.0</version>
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

## gzip

```java
NginxGzipConfig gzipConfig = new NginxGzipConfig();
    gzipConfig.setGzip("on");
    gzipConfig.setGzipMinLength(256);
    gzipConfig.setGzipTypes(Arrays.asList(
            "text/plain",
            "text/css",
            "text/javascript",
            "application/json",
            "application/javascript",
            "application/xml+rss"
    ));

Nginx4jBs.newInstance()
                .nginxGzipConfig(gzipConfig)
                .init()
                .start();
```

可以开启 gzip 的处理。

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
- [x] sendFile 特性支持
- [x] range 的代码合并到 file
- [x] http keep-alive
- [ ] 压缩更好的实现方式？ zlib 算法 + 实现优化？
- [ ] CORS
- [ ] rewrite 请求头信息重写
- [ ] ETag 和 Last-Modified
- [ ] http2
- [ ] http3
- [ ] ssl/https
- [ ] 常见请求头/headers/cookie 的处理

## 反向代理

- [ ] reverse-proxy
- [ ] load-balance

## system

- [ ] cache
- [ ] filter 过滤器
- [ ] listener 监听器
- [ ] rateLimit 限流