package com.github.houbb.nginx4j.config.parser;

public class NginxParserParamDefault extends AbstractNginxParserEntry implements INginxParserParam {

    @Override
    public String getType() {
        return NginxParserEntryType.PARAM.getCode();
    }

}
