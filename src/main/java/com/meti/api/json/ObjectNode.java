package com.meti.api.json;

import com.meti.api.collect.java.Map;
import com.meti.api.collect.map.Maps;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.node.JSONable;

public class ObjectNode implements JSONNode {
    private final Map<String, String> internalMap;

    public ObjectNode() {
        this(Maps.empty());
    }

    public ObjectNode(Map<String, String> internalMap) {
        this.internalMap = internalMap;
    }

    public ObjectNode addJSON(String name, JSONable value) throws JSONException {
        return addObject(name, value.toJSON());
    }

    public ObjectNode addObject(String name, Object value) {
        return new ObjectNode(internalMap.put(name, value.toString()));
    }

    public ObjectNode addString(String name, String value) {
        return addObject(name, "\"" + value + "\"");
    }

    @Override
    public String toString() {
        try {
            return internalMap.streamKeys().map(key -> "\"" + key + "\":" + internalMap.apply(key)).foldRight((current, next) -> current + "," + next).map(value -> "{" + value + "}").orElse("{}");
        } catch (StreamException e) {
            return "";
        }
    }
}
