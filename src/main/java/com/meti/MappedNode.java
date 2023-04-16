package com.meti;

import java.util.Map;
import java.util.Optional;

public class MappedNode implements Node {
    private final String identity;
    private final Map<String, Attribute> attributes;

    public MappedNode(String identity, Map<String, Attribute> attributes) {
        this.identity = identity;
        this.attributes = attributes;
    }

    @Override
    public boolean is(Object key) {
        return identity.equals(key);
    }

    @Override
    public Optional<Attribute> apply(Object key) {
        if(!(key instanceof String)) return Optional.empty();

        if(attributes.containsKey(key)) {
            return Optional.of(attributes.get(key));
        } else {
            return Optional.empty();
        }
    }
}
