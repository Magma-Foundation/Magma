package com.meti.node;

import com.meti.util.None;
import com.meti.util.Option;
import com.meti.util.Some;

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
                .filter(entry-> factory.accepts(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
