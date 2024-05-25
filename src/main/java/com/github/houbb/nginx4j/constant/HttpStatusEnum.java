package com.github.houbb.nginx4j.constant;

public enum HttpStatusEnum {

    CONTINUE(100, "100 Continue", "Continue"),
    SWITCHING_PROTOCOLS(101, "101 Switching Protocols", "Switching Protocols"),
    OK(200, "200 OK", "ok"),
    CREATED(201, "201 Created", "Created"),
    ACCEPTED(202, "202 Accepted", "Accepted"),
    NON_AUTHORITATIVE_INFORMATION(203, "203 Non-Authoritative Information", "Non-Authoritative Information"),
    NO_CONTENT(204, "204 No Content", "No Content"),
    RESET_CONTENT(205, "205 Reset Content", "Reset Content"),
    PARTIAL_CONTENT(206, "206 Partial Content", "Partial Content"),
    MULTIPLE_CHOICES(300, "300 Multiple Choices", "Multiple Choices"),
    MOVED_PERMANENTLY(301, "301 Moved Permanently", "Moved Permanently"),
    FOUND(302, "302 Found", "Found"),
    SEE_OTHER(303, "303 See Other", "See Other"),
    NOT_MODIFIED(304, "304 Not Modified", "Not Modified"),
    USE_PROXY(305, "305 Use Proxy", "Use Proxy"),
    TEMPORARY_REDIRECT(307, "307 Temporary Redirect", "Temporary Redirect"),
    BAD_REQUEST(400, "400 Bad Request", "400 Bad Request: The request could not be understood by the server due to malformed syntax."),
    UNAUTHORIZED(401, "401 Unauthorized", "Unauthorized"),
    PAYMENT_REQUIRED(402, "402 Payment Required", "Payment Required"),
    FORBIDDEN(403, "403 Forbidden", "Forbidden"),
    NOT_FOUND(404, "404 Not Found", "404 Not Found: The requested resource was not found on this server."),
    METHOD_NOT_ALLOWED(405, "405 Method Not Allowed", "Method Not Allowed"),
    NOT_ACCEPTABLE(406, "406 Not Acceptable", "Not Acceptable"),
    PROXY_AUTHENTICATION_REQUIRED(407, "407 Proxy Authentication Required", "Proxy Authentication Required"),
    REQUEST_TIMEOUT(408, "408 Request Timeout", "Request Timeout"),
    CONFLICT(409, "409 Conflict", "Conflict"),
    GONE(410, "410 Gone", "Gone"),
    LENGTH_REQUIRED(411, "411 Length Required", "Length Required"),
    PRECONDITION_FAILED(412, "412 Precondition Failed", "Precondition Failed"),
    PAYLOAD_TOO_LARGE(413, "413 Payload Too Large", "Payload Too Large"),
    URI_TOO_LONG(414, "414 URI Too Long", "URI Too Long"),
    UNSUPPORTED_MEDIA_TYPE(415, "415 Unsupported Media Type", "Unsupported Media Type"),
    RANGE_NOT_SATISFIABLE(416, "416 Range Not Satisfiable", "Range Not Satisfiable"),
    EXPECTATION_FAILED(417, "417 Expectation Failed", "Expectation Failed"),
    INTERNAL_SERVER_ERROR(500, "500 Internal Server Error", "500 Internal Server Error: The server encountered an unexpected condition that prevented it from fulfilling the request."),
    NOT_IMPLEMENTED(501, "501 Not Implemented", "Not Implemented"),
    BAD_GATEWAY(502, "502 Bad Gateway", "Bad Gateway"),
    SERVICE_UNAVAILABLE(503, "503 Service Unavailable", "Service Unavailable"),
    GATEWAY_TIMEOUT(504, "504 Gateway Timeout", "Gateway Timeout"),
    HTTP_VERSION_NOT_SUPPORTED(505, "505 HTTP Version Not Supported", "HTTP Version Not Supported");

    private final int code;
    private final String desc;
    private final String defaultDesc;

    HttpStatusEnum(int code, String desc, String defaultDesc) {
        this.code = code;
        this.desc = desc;
        this.defaultDesc = defaultDesc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getDefaultDesc() {
        return defaultDesc;
    }
}
