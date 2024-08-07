# 项目简介

nginx4j 是基于 netty 实现的 nginx 的java 版本。

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.houbb/nginx4j/badge.svg)](http://mvnrepository.com/artifact/com.github.houbb/nginx4j)
[![Build Status](https://www.travis-ci.org/houbb/nginx4j.svg?branch=master)](https://www.travis-ci.org/houbb/nginx4j?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/houbb/nginx4j/badge.svg?branch=master)](https://coveralls.io/github/houbb/nginx4j?branch=master)

如果你想知道 servlet 如何处理的，可以参考我的另一个项目：

>  [简易版 tomcat](https://github.com/houbb/minicat)

# 特性

- 完全兼容 nginx.conf 的配置文件格式

- 基于 netty 的 nio 高性能处理

- 静态网资源支持

- 默认 index.html

- 404

- 文件夹的自动索引

- 大文件的下载支持

- gzip 压缩

- sendfile 零拷贝特性

- http keep-alive 特性

- 支持多 server 

- 请求头的修改+响应头的修改

- 常见占位符 `$` 的内置支持

- cookie 的操作处理 proxy_cookie_domain/proxy_cookie_flags/proxy_cookie_path 内置支持 

- proxy_pass 反向代理实现

# 变更日志

> [变更日志](CHANGE_LOG.md)

# 快速开始

## maven 依赖

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>nginx4j</artifactId>
    <version>0.27.0</version>
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

    # 文件传输设置
    sendfile on;  # 开启高效文件传输
    # Keepalive超时设置
    keepalive_timeout 65;

    # 定义服务器块
    server {
        listen 8080;
        server_name 192.168.1.12:8080;  # 服务器域名

        # 单独为这个 server 启用 sendfile
        sendfile on;

        # 静态文件的根目录
        root D:\data\nginx4j;  # 静态文件存放的根目录
        index index.html index.htm;  # 默认首页

        # 如果需要为这个 server 单独配置 gzip，可以覆盖全局配置
        gzip on;
        gzip_types text/plain text/css application/json application/javascript text/xml application/xml application/xml+rss text/javascript;

        # 默认匹配
        location / {
            proxy_set_header X-DEFINE-PARAM myDefineParam;
            proxy_set_header X-DEFINE-HOST 127.0.0.1;

            # 增加或修改响应头 这里就提现了一些占位符的强大之处。下一次可以考虑支持
            add_header X-Response-Time 2024-06-08;

            # 删除响应头
            proxy_hide_header X-Unwanted-Header;

            # 占位符测试 v0.17.0
            set $myVal 1000;
            add_header X-MY-VAL $myVal;
            add_header X-MY-HOST $host;
        }
    }

    # 定义服务器块2
    server {
        listen 8081;
        server_name 192.168.1.12:8081;  # 服务器域名

        # 单独为这个 server 启用 sendfile
        sendfile on;

        # 静态文件的根目录
        root D:\data\nginx4j;  # 静态文件存放的根目录
        index index.txt; # 默认首页

        # 默认匹配
        location / {
            proxy_set_header X-DEFINE-PARAM myDefineParam;
            proxy_set_header X-DEFINE-HOST 127.0.0.2;

            # 增加或修改响应头 这里就提现了一些占位符的强大之处。下一次可以考虑支持
            add_header X-Response-Time 2024-06-09;

            # 删除响应头
            proxy_hide_header X-Unwanted-Header;
        }
    }

}
```


### 启动代码

```java
// 指定配置文件的位置
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

[从零手写实现 nginx-17-nginx 默认配置优化](https://houbb.github.io/2018/11/22/nginx-write-17-nginx-conf-global-default)

[从零手写实现 nginx-18-nginx 请求头+响应头操作](https://houbb.github.io/2018/11/22/nginx-write-18-nginx-conf-header-oper)

[从零手写实现 nginx-19-nginx cors](https://houbb.github.io/2018/11/22/nginx-write-19-cors)

[从零手写实现 nginx-20-nginx 占位符 placeholder](https://houbb.github.io/2018/11/22/nginx-write-20-placeholder)

[从零手写实现 nginx-21-nginx modules 模块信息概览](https://houbb.github.io/2018/11/22/nginx-write-21-modules-overview)

[从零手写实现 nginx-22-nginx modules 分模块加载优化](https://houbb.github.io/2018/11/22/nginx-write-22-modules-load)

[从零手写实现 nginx-23-nginx cookie 的操作处理](https://houbb.github.io/2018/11/22/nginx-write-23-cookie-oper)

[从零手写实现 nginx-24-nginx IF 指令](https://houbb.github.io/2018/11/22/nginx-write-24-directives-if)

[从零手写实现 nginx-25-nginx map 指令](https://houbb.github.io/2018/11/22/nginx-write-25-directives-map)

[从零手写实现 nginx-26-nginx rewrite 指令](https://houbb.github.io/2018/11/22/nginx-write-26-directives-rewrite)

[从零手写实现 nginx-27-nginx return 指令](https://houbb.github.io/2018/11/22/nginx-write-27-directives-return)

[从零手写实现 nginx-28-nginx error_pages 指令](https://houbb.github.io/2018/11/22/nginx-write-28-directives-error-pages)

[从零手写实现 nginx-29-nginx try_files 指令](https://houbb.github.io/2018/11/22/nginx-write-29-directives-try_files)

[从零手写实现 nginx-30-nginx proxy_pass upstream 指令](https://houbb.github.io/2018/11/22/nginx-write-30-proxy-pass)

[从零手写实现 nginx-31-nginx load-balance 负载均衡](https://houbb.github.io/2018/11/22/nginx-write-31-load-balance)

[从零手写实现 nginx-32-nginx load-balance 算法 java 实现](https://houbb.github.io/2018/11/22/nginx-write-32-load-balance-java-impl)

[从零手写实现 nginx-33-nginx http proxy_pass 测试验证](https://houbb.github.io/2018/11/22/nginx-write-33-http-proxy-pass-test)

[从零手写实现 nginx-34-proxy_pass 配置加载处理](https://houbb.github.io/2018/11/22/nginx-write-34-http-proxy-pass-config-load)

[从零手写实现 nginx-35-proxy_pass netty 如何实现？](https://houbb.github.io/2018/11/22/nginx-write-35-http-proxy-pass-netty)

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
- [x] 配置的标准 POJO
- [x] nginx.conf 的解析=》POJO
- [x] http 全局的默认配置属性
- [x] 请求头信息重写
- [x] CORS 这个还是让用户处理，不过可以单独写一篇文章
- [x] $ 占位符的实现
- [x] 常见 cookie 的处理
- [x] if 指令的支持
- [x] map 变量修改指令
- [x] rewrite 指令，重写 URL
- [x] return 返回指令
- [x] error_page 自定义错误页面
- [x] try_files 文件处理指令
- [ ] 更多 directive 指令实现
- [ ] 更多文件格式的内置支持？
- [ ] ETag 和 Last-Modified + cache 相关
- [ ] 压缩更好的实现方式？ zlib 算法 + 实现优化？
- [ ] http2
- [ ] http3
- [ ] ssl/https
- [ ] 安全 访问限制

## 反向代理

- [x] reverse-proxy
- [x] load-balance

## system

- [ ] cache
- [ ] rateLimit 限流
- [ ] filter 过滤器
- [ ] listener 监听器
