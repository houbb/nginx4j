# 项目简介

```
 /\_/\  
( o.o ) 
 > ^ <
```

nginx4j 是 nginx 的java 版本。

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.houbb/nginx4j/badge.svg)](http://mvnrepository.com/artifact/com.github.houbb/nginx4j)
[![Build Status](https://www.travis-ci.org/houbb/nginx4j.svg?branch=master)](https://www.travis-ci.org/houbb/nginx4j?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/houbb/nginx4j/badge.svg?branch=master)](https://coveralls.io/github/houbb/nginx4j?branch=master)

# 特性

- 静态网资源支持

- 基于 netty 的 nio 高性能

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

# ROAD-MAP

- [ ] 基于 netty 实现
- [ ] index.html
- [ ] 404 403 等常见页面
