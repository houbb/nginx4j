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

- 支持多 server 

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

[从零手写实现 nginx-11-file+range 合并](https://houbb.github.io/2018/11/22/nginx-write-11-file-and-range-merge)

[从零手写实现 nginx-12-keep-alive 连接复用](https://houbb.github.io/2018/11/22/nginx-write-12-keepalive)

[从零手写实现 nginx-13-nginx.conf 配置文件介绍](https://houbb.github.io/2018/11/22/nginx-write-13-nginx-conf-intro)

[从零手写实现 nginx-14-nginx.conf 和 hocon 格式有关系吗？](https://houbb.github.io/2018/11/22/nginx-write-14-nginx-conf-hocon)

[从零手写实现 nginx-15-nginx.conf 如何通过 java 解析处理？](https://houbb.github.io/2018/11/22/nginx-write-15-nginx-conf-parser)

[从零手写实现 nginx-16-nginx 支持配置多个 server](https://houbb.github.io/2018/11/22/nginx-write-16-nginx-conf-multi-server)

# 变更日志

> [变更日志](CHANGE_LOG.md)

# 快速开始

## maven 依赖

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>nginx4j</artifactId>
    <version>0.14.0</version>
</dependency>
```

## 启动测试

### 配置文件

```conf
# nginx.conf

# 定义运行Nginx的用户和组
user nginx;

# 主进程的PID文件存放位置
pid /var/run/nginx.pid;

# 事件模块配置
events {
    worker_connections 1024;  # 每个工作进程的最大连接数
}

# HTTP模块配置
http {
    include /etc/nginx/mime.types;  # MIME类型配置文件
    default_type application/octet-stream;  # 默认的MIME类型

    # 访问日志配置
    access_log /var/log/nginx/access.log;  # 访问日志文件路径
    # 错误日志配置
    error_log /var/log/nginx/error.log;  # 错误日志文件路径

    # 文件传输设置
    sendfile on;  # 开启高效文件传输
    tcp_nopush on;  # 防止网络拥塞

    # Keepalive超时设置
    keepalive_timeout 65;

    # 定义服务器块
    server {
        listen 80;  # 监听80端口
        server_name localhost;  # 服务器域名

        # 单独为这个 server 启用 sendfile
        sendfile on;

        # 静态文件的根目录
        root D:\data\nginx4j;  # 静态文件存放的根目录
        index index.html index.htm;  # 默认首页

        # 如果需要为这个 server 单独配置 gzip，可以覆盖全局配置
        gzip on;
        gzip_disable "msie6";
        gzip_vary on;
        gzip_proxied any;
        gzip_comp_level 6;
        gzip_buffers 16 8k;
        gzip_http_version 1.1;
        gzip_types text/plain text/css application/json application/javascript text/xml application/xml application/xml+rss text/javascript;

        # 定义location块，处理对根目录的请求
        location / {
            try_files $uri $uri/ =404;  # 尝试提供请求的文件，如果不存在则404
        }
    }

    server {
        listen 443 ssl;
        server_name  secure-example.com;

        ssl_certificate     /etc/nginx/ssl/secure-example.com.crt;
        ssl_certificate_key /etc/nginx/ssl/secure-example.com.key;

        location / {
            root   /var/www/secure-example.com;
            index  index.html index.htm;
        }
    }

}
```


### 启动代码


```java
NginxUserConfig nginxUserConfig = NginxUserConfigLoaders.configFile("D:\\github\\nginx4j\\src\\main\\resources\\nginx.conf").load();

Nginx4jBs.newInstance()
.nginxUserConfig(nginxUserConfig)
.init()
.start();
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
NginxUserConfig nginxUserConfig = NginxUserConfigLoaders.configFile("D:\\github\\nginx4j\\src\\main\\resources\\nginx.conf").load();

 Nginx4jBs.newInstance()
 .nginxUserConfig(nginxUserConfig)
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
- [ ] 配置的标准 POJO
- [ ] nginx.conf 的解析=》POJO
- [ ] 更多文件格式的内置支持？
- [ ] http 全局的默认配置属性
- [ ] CORS
- [ ] rewrite 请求头信息重写
- [ ] ETag 和 Last-Modified + cache
- [ ] 压缩更好的实现方式？ zlib 算法 + 实现优化？
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
- 