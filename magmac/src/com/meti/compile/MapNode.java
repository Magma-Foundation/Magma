package com.meti.compile;

import com.meti.api.collect.Map;
import com.meti.api.option.Option;

public record MapNode(String type, Map<String, String> map) implements Node {
    @Override
    public boolean is(String type) {
        return this.type.equals(type);
    }

    @Override
    public Option<String> getString(String name) {
        return map.get(name);
    }
}
