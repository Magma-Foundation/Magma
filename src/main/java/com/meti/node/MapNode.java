package com.meti.node;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MapNode implements Node {
    private final String identifier;
    private final Map<String, Attribute> attributes;

    public MapNode(String identifier, Map<String, Attribute> attributes) {
        this.identifier = identifier;
        this.attributes = new HashMap<>(attributes);
    }

    public MapNode(String identifier) {
        this(identifier, Collections.emptyMap());
    }

    @Override
    public Optional<Node> with(Object key, Attribute value) {
        if (key instanceof String && attributes.containsKey(key)) {
            var copy = new HashMap<>(attributes);
            copy.put((String) key, value);
            return Optional.of(new MapNode(identifier, copy));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Attribute> apply(Object key) {
        if (key instanceof String && attributes.containsKey(key)) {
            return Optional.of(attributes.get(key));
        }

        return Optional.empty();
    }

    @Override
    public String render() {
        throw new UnsupportedOperationException();
    }
}
