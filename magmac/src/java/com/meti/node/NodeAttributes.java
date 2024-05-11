package com.meti.node;

import com.meti.api.None;
import com.meti.api.Option;
import com.meti.api.Some;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class NodeAttributes {
    private final Map<String, Attribute> map;

    public NodeAttributes(Map<String, ? extends Attribute> map) {
        this.map = new HashMap<>(map);
    }

    public static NodeAttributes fromStrings(Map<String, String> map) {
        var copy = new HashMap<String, Attribute>();
        for (var stringStringEntry : map.entrySet()) {
            copy.put(stringStringEntry.getKey(), new StringAttribute(stringStringEntry.getValue()));
        }

        return new NodeAttributes(copy);
    }

    public static Builder NodeAttributesBuilder() {
        return new Builder(Collections.emptyMap());
    }

    public Option<Attribute> apply(String name) {
        return map.containsKey(name)
                ? new Some<>(map.get(name))
                : new None<>();
    }

    public NodeAttributes add(NodeAttributes other) {
        var copy = new HashMap<>(map);
        copy.putAll(other.map);
        return new NodeAttributes(copy);
    }

    public NodeAttributes with(String key, Attribute value) {
        var copy = new HashMap<>(map);
        copy.put(key, value);
        return new NodeAttributes(copy);
    }

    public <T> List<String> filter(AttributeFactory<T> factory) {
        return map.entrySet()
                .stream()
                .filter(entry -> factory.accepts(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public record Builder(Map<String, Attribute> attributeMap) {
        public Builder withString(String key, String value) {
            return with(key, new StringAttribute(value));
        }

        public Builder withNode(String key, String nodeName, NodeAttributes attributes) {
            return with(key, new NodeAttribute(new MapNode(nodeName, attributes)));
        }

        public Builder with(String key, Attribute attribute) {
            var copy = new HashMap<>(attributeMap);
            copy.put(key, attribute);
            return new Builder(copy);
        }

        public NodeAttributes complete() {
            return new NodeAttributes(attributeMap);
        }
    }
}
