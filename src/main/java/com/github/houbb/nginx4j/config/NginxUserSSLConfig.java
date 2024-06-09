package com.github.houbb.nginx4j.config;

/**
 * 用户配置
 *
 * @since 0.18.0
 */
public class NginxUserSSLConfig extends NginxCommonUserConfig {

    // ssl_certificate 配置 SSL 证书的路径。
    private String sslCertificate;

    // ssl_certificate_key 配置 SSL 证书的私钥路径。
    private String sslCertificateKey;

    // ssl_protocols 配置允许使用的 SSL/TLS 协议版本。
    private String sslProtocols;

    // ssl_ciphers 配置 SSL 加密算法。
    private String sslCiphers;

    // ssl_prefer_server_ciphers 设置为 `on` 以使用服务器端的 SSL 加密算法。
    private String sslPreferServerCiphers;

    // ssl_session_cache 配置 SSL 会话缓存。
    private String sslSessionCache;

    // ssl_session_timeout 配置 SSL 会话的超时时间。
    private String sslSessionTimeout;

    // ssl_session_tickets 启用或禁用 SSL 会话票据。
    private String sslSessionTickets;

    // ssl_session_ticket_key 配置 SSL 会话票据密钥。
    private String sslSessionTicketKey;

    // ssl_dhparam 配置 Diffie-Hellman 参数文件的路径。
    private String sslDhparam;

    // ssl_ecdh_curve 配置 ECDH 曲线。
    private String sslEcdhCurve;

    // ssl_stapling 启用或禁用 OCSP Stapling。
    private String sslStapling;

    // ssl_stapling_verify 启用或禁用 OCSP Stapling 验证。
    private String sslStaplingVerify;

    // ssl_trusted_certificate 配置用于验证客户端证书的 CA 证书。
    private String sslTrustedCertificate;

    // ssl_verify_client 启用或禁用客户端证书验证。
    private String sslVerifyClient;

    // ssl_verify_depth 配置客户端证书链的验证深度。
    private String sslVerifyDepth;

    // ssl_client_certificate 配置服务器信任的 CA 证书列表。
    private String sslClientCertificate;

    // ssl_password_file 配置 SSL 密钥文件的密码。
    private String sslPasswordFile;

    // ssl_crl 配置用于验证客户端证书的 CRL 文件。
    private String sslCrl;

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

    public String getSslPreferServerCiphers() {
        return sslPreferServerCiphers;
    }

    public void setSslPreferServerCiphers(String sslPreferServerCiphers) {
        this.sslPreferServerCiphers = sslPreferServerCiphers;
    }

    public String getSslSessionCache() {
        return sslSessionCache;
    }

    public void setSslSessionCache(String sslSessionCache) {
        this.sslSessionCache = sslSessionCache;
    }

    public String getSslSessionTimeout() {
        return sslSessionTimeout;
    }

    public void setSslSessionTimeout(String sslSessionTimeout) {
        this.sslSessionTimeout = sslSessionTimeout;
    }

    public String getSslSessionTickets() {
        return sslSessionTickets;
    }

    public void setSslSessionTickets(String sslSessionTickets) {
        this.sslSessionTickets = sslSessionTickets;
    }

    public String getSslSessionTicketKey() {
        return sslSessionTicketKey;
    }

    public void setSslSessionTicketKey(String sslSessionTicketKey) {
        this.sslSessionTicketKey = sslSessionTicketKey;
    }

    public String getSslDhparam() {
        return sslDhparam;
    }

    public void setSslDhparam(String sslDhparam) {
        this.sslDhparam = sslDhparam;
    }

    public String getSslEcdhCurve() {
        return sslEcdhCurve;
    }

    public void setSslEcdhCurve(String sslEcdhCurve) {
        this.sslEcdhCurve = sslEcdhCurve;
    }

    public String getSslStapling() {
        return sslStapling;
    }

    public void setSslStapling(String sslStapling) {
        this.sslStapling = sslStapling;
    }

    public String getSslStaplingVerify() {
        return sslStaplingVerify;
    }

    public void setSslStaplingVerify(String sslStaplingVerify) {
        this.sslStaplingVerify = sslStaplingVerify;
    }

    public String getSslTrustedCertificate() {
        return sslTrustedCertificate;
    }

    public void setSslTrustedCertificate(String sslTrustedCertificate) {
        this.sslTrustedCertificate = sslTrustedCertificate;
    }

    public String getSslVerifyClient() {
        return sslVerifyClient;
    }

    public void setSslVerifyClient(String sslVerifyClient) {
        this.sslVerifyClient = sslVerifyClient;
    }

    public String getSslVerifyDepth() {
        return sslVerifyDepth;
    }

    public void setSslVerifyDepth(String sslVerifyDepth) {
        this.sslVerifyDepth = sslVerifyDepth;
    }

    public String getSslClientCertificate() {
        return sslClientCertificate;
    }

    public void setSslClientCertificate(String sslClientCertificate) {
        this.sslClientCertificate = sslClientCertificate;
    }

    public String getSslPasswordFile() {
        return sslPasswordFile;
    }

    public void setSslPasswordFile(String sslPasswordFile) {
        this.sslPasswordFile = sslPasswordFile;
    }

    public String getSslCrl() {
        return sslCrl;
    }

    public void setSslCrl(String sslCrl) {
        this.sslCrl = sslCrl;
    }

}
