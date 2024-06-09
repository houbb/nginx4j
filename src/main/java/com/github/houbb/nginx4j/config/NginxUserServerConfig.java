package com.github.houbb.nginx4j.config;

import java.util.List;

/**
 * 用户配置
 *
 * @since 0.12.0
 */
public class NginxUserServerConfig extends NginxCommonUserConfig {

    // include 包含其他配置文件。
    private String include;

    // default_type 设置默认的 MIME 类型。
    private String defaultType;

    // log_format 定义日志格式。
    private String logFormat;

    // access_log 配置访问日志路径和格式。
    private String accessLog;

    // error_log 配置错误日志路径和级别。
    private String errorLog;

    private long gzipMinLength;

    // gzip 启用或禁用 Gzip 压缩。
    private String gzip;

    // gzip_types 配置哪些 MIME 类型的响应需要进行 Gzip 压缩。
    private List<String> gzipTypes;

    // client_max_body_size 设置请求体的最大大小。
    private String clientMaxBodySize;

    // keepalive_timeout 配置 Keep-Alive 连接超时时间。
    private String keepaliveTimeout;

    // proxy_cache_path 配置反向代理的缓存路径和参数。
    private String proxyCachePath;

    // proxy_set_header 设置发送给后端服务器的 HTTP 头字段。
    private String proxySetHeader;

    // proxy_pass 设置反向代理到后端服务器。
    private String proxyPass;

    // root 设置请求的根目录。
    private String root;

    // index 设置默认的索引文件。
    private List<String> indexList;

    // try_files 配置尝试查找文件的规则。
    private String tryFiles;

    // error_page 配置错误页面。
    private String errorPage;

    // expires 设置响应的过期时间。
    private String expires;

    // add_header 添加 HTTP 响应头字段。
    private String addHeader;

    // ssl_certificate 配置 SSL 证书文件路径。
    private String sslCertificate;

    // ssl_certificate_key 配置 SSL 证书私钥文件路径。
    private String sslCertificateKey;

    // ssl_protocols 配置支持的 SSL 协议版本。
    private String sslProtocols;

    // ssl_ciphers 配置支持的 SSL 加密算法。
    private String sslCiphers;

    // server_name 配置虚拟主机的域名。
    private String serverName;

    // listen 配置监听的端口和 IP 地址。
    private int listen;

    // resolver 配置域名解析器地址。
    private String resolver;

    /**
     * 文件编码
     */
    private String charset;



    private List<NginxUserServerLocationConfig> locations;

    private NginxUserServerLocationConfig defaultLocation;

    public NginxUserServerLocationConfig getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(NginxUserServerLocationConfig defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    public List<NginxUserServerLocationConfig> getLocations() {
        return locations;
    }

    public void setLocations(List<NginxUserServerLocationConfig> locations) {
        this.locations = locations;
    }

    /**
     * 是否启用 zero-copy
     */
    private String sendFile;

    public long getGzipMinLength() {
        return gzipMinLength;
    }

    public void setGzipMinLength(long gzipMinLength) {
        this.gzipMinLength = gzipMinLength;
    }

    public String getSendFile() {
        return sendFile;
    }

    public void setSendFile(String sendFile) {
        this.sendFile = sendFile;
    }

    public String getInclude() {
        return include;
    }

    public void setInclude(String include) {
        this.include = include;
    }

    public String getDefaultType() {
        return defaultType;
    }

    public void setDefaultType(String defaultType) {
        this.defaultType = defaultType;
    }

    public String getLogFormat() {
        return logFormat;
    }

    public void setLogFormat(String logFormat) {
        this.logFormat = logFormat;
    }

    public String getAccessLog() {
        return accessLog;
    }

    public void setAccessLog(String accessLog) {
        this.accessLog = accessLog;
    }

    public String getErrorLog() {
        return errorLog;
    }

    public void setErrorLog(String errorLog) {
        this.errorLog = errorLog;
    }

    public String getGzip() {
        return gzip;
    }

    public void setGzip(String gzip) {
        this.gzip = gzip;
    }

    public List<String> getGzipTypes() {
        return gzipTypes;
    }

    public void setGzipTypes(List<String> gzipTypes) {
        this.gzipTypes = gzipTypes;
    }

    public String getClientMaxBodySize() {
        return clientMaxBodySize;
    }

    public void setClientMaxBodySize(String clientMaxBodySize) {
        this.clientMaxBodySize = clientMaxBodySize;
    }

    public String getKeepaliveTimeout() {
        return keepaliveTimeout;
    }

    public void setKeepaliveTimeout(String keepaliveTimeout) {
        this.keepaliveTimeout = keepaliveTimeout;
    }

    public String getProxyCachePath() {
        return proxyCachePath;
    }

    public void setProxyCachePath(String proxyCachePath) {
        this.proxyCachePath = proxyCachePath;
    }

    public String getProxySetHeader() {
        return proxySetHeader;
    }

    public void setProxySetHeader(String proxySetHeader) {
        this.proxySetHeader = proxySetHeader;
    }

    public String getProxyPass() {
        return proxyPass;
    }

    public void setProxyPass(String proxyPass) {
        this.proxyPass = proxyPass;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public List<String> getIndexList() {
        return indexList;
    }

    public void setIndexList(List<String> indexList) {
        this.indexList = indexList;
    }

    public String getTryFiles() {
        return tryFiles;
    }

    public void setTryFiles(String tryFiles) {
        this.tryFiles = tryFiles;
    }

    public String getErrorPage() {
        return errorPage;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getAddHeader() {
        return addHeader;
    }

    public void setAddHeader(String addHeader) {
        this.addHeader = addHeader;
    }

    public String getSslCertificate() {
        return sslCertificate;
    }

    public void setSslCertificate(String sslCertificate) {
        this.sslCertificate = sslCertificate;
    }

    public String getSslCertificateKey() {
        return sslCertificateKey;
    }

    public void setSslCertificateKey(String sslCertificateKey) {
        this.sslCertificateKey = sslCertificateKey;
    }

    public String getSslProtocols() {
        return sslProtocols;
    }

    public void setSslProtocols(String sslProtocols) {
        this.sslProtocols = sslProtocols;
    }

    public String getSslCiphers() {
        return sslCiphers;
    }

    public void setSslCiphers(String sslCiphers) {
        this.sslCiphers = sslCiphers;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getListen() {
        return listen;
    }

    public void setListen(int listen) {
        this.listen = listen;
    }

    public String getResolver() {
        return resolver;
    }

    public void setResolver(String resolver) {
        this.resolver = resolver;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

}
