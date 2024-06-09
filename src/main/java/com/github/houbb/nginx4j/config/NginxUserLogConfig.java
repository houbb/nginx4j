package com.github.houbb.nginx4j.config;

/**
 * 用户配置
 *
 * @since 0.18.0
 */
public class NginxUserLogConfig extends NginxCommonUserConfig {

    // access_log 配置访问日志的路径和格式。
    private String accessLog;

    // error_log 配置错误日志的路径和级别。
    private String errorLog;

    // log_format 定义自定义日志格式。
    private String logFormat;

    // open_log_file_cache 设置日志文件的缓存参数。
    private String openLogFileCache;

    // log_not_found 启用或禁用 404 错误日志记录。
    private boolean logNotFound;

    // log_subrequest 启用或禁用子请求日志记录。
    private boolean logSubrequest;

    // access_log off 禁用访问日志记录。
    private boolean accessLogOff;

    // error_log off 禁用错误日志记录。
    private boolean errorLogOff;

    // buffered 设置是否启用日志缓冲。
    private boolean buffered;

    // flush 设置日志缓冲区的刷新频率。
    private String flush;

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

    public String getLogFormat() {
        return logFormat;
    }

    public void setLogFormat(String logFormat) {
        this.logFormat = logFormat;
    }

    public String getOpenLogFileCache() {
        return openLogFileCache;
    }

    public void setOpenLogFileCache(String openLogFileCache) {
        this.openLogFileCache = openLogFileCache;
    }

    public boolean isLogNotFound() {
        return logNotFound;
    }

    public void setLogNotFound(boolean logNotFound) {
        this.logNotFound = logNotFound;
    }

    public boolean isLogSubrequest() {
        return logSubrequest;
    }

    public void setLogSubrequest(boolean logSubrequest) {
        this.logSubrequest = logSubrequest;
    }

    public boolean isAccessLogOff() {
        return accessLogOff;
    }

    public void setAccessLogOff(boolean accessLogOff) {
        this.accessLogOff = accessLogOff;
    }

    public boolean isErrorLogOff() {
        return errorLogOff;
    }

    public void setErrorLogOff(boolean errorLogOff) {
        this.errorLogOff = errorLogOff;
    }

    public boolean isBuffered() {
        return buffered;
    }

    public void setBuffered(boolean buffered) {
        this.buffered = buffered;
    }

    public String getFlush() {
        return flush;
    }

    public void setFlush(String flush) {
        this.flush = flush;
    }

}
