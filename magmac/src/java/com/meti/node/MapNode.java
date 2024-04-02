package com.meti.node;

import com.meti.java.RenderableBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record MapNode(String name, Map<String, Attribute> attributes) implements Node {
    public static Builder Builder(String name) {
        return new Builder(name, Collections.emptyMap());
    }

    @Override
    public Optional<Attribute> apply(String prefix) {
        if (attributes.containsKey(prefix)) {
            return Optional.of(attributes.get(prefix));
        } else {
            return Optional.empty();
        }
    }

    public record Builder(String name, Map<String, Attribute> attributes) implements RenderableBuilder {
        public Builder string(String name, String value) {
            var copy = new HashMap<>(attributes);
            copy.put(name, new StringAttribute(value));
            return new Builder(name, copy);
        }

        @Override
        public Node build() {
            return new MapNode(name, attributes);
        }
    }
}
