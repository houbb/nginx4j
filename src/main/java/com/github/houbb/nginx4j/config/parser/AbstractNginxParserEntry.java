package com.github.houbb.nginx4j.config.parser;

import com.github.houbb.heaven.util.util.CollectionUtil;

import java.util.List;

public abstract class AbstractNginxParserEntry implements INginxParserEntry {

    private String name;

    private List<String> values;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public String getValue() {
        if(CollectionUtil.isEmpty(values)) {
            return null;
        }

        return values.get(0);
    }

}
