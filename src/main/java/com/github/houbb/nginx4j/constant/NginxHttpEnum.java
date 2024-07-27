package com.github.houbb.nginx4j.constant;

/**
 * nginx http 枚举
 * @since
 */
public enum NginxHttpEnum {
    HTTP("http://", 80),
    HTTPS("http://", 443),
    ;

    private final String type;

    private final int defaultPort;


    NginxHttpEnum(String type, int defaultPort) {
        this.type = type;
        this.defaultPort = defaultPort;
    }

    public String getType() {
        return type;
    }

    public int getDefaultPort() {
        return defaultPort;
    }

    @Override
    public String toString() {
        return "NginxHttpEnum{" +
                "type='" + type + '\'' +
                ", defaultPort=" + defaultPort +
                "} " + super.toString();
    }

    public static int getDefaultPortByUrl(final String url) {
        String lowerCase = url.toLowerCase();
        if(lowerCase.startsWith(HTTPS.type)) {
            return HTTPS.defaultPort;
        }
        return HTTP.defaultPort;
    }

}
