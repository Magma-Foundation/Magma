package com.meti.api.json;

import com.meti.api.collect.java.Map;
import com.meti.api.collect.map.Maps;
import com.meti.api.collect.stream.StreamException;

public class ObjectNode implements JSONNode {
    private final Map<String, String> internalMap;

    public ObjectNode() {
        this(Maps.empty());
    }

    public ObjectNode(Map<String, String> internalMap) {
        this.internalMap = internalMap;
    }

    public ObjectNode appendString(String name, String value) {
        return appendObject(name, "\"" + value + "\"");
    }

    public ObjectNode appendObject(String name, Object value) {
        return new ObjectNode(internalMap.put(name, value.toString()));
    }

    @Override
    public String format() {
        var value = toString();
        var buffer = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            var c = value.charAt(i);
            if (c == '{') {
                buffer.append(c).append("\n\t");
            } else if (c == ':') {
                buffer.append(" : ");
            } else if (c == '}') {
                buffer.append("\n").append(c);
            } else {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    @Override
    public String toString() {
        try {
            return internalMap.streamKeys()
                    .map(key -> "\"" + key + "\":" + internalMap.apply(key))
                    .foldRight((current, next) -> current + "," + next)
                    .map(value -> "{" + value + "}")
                    .orElse("{}");
        } catch (StreamException e) {
            return "";
        }
    }
}
