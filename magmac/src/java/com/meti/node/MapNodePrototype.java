package com.meti.node;

import com.meti.collect.JavaString;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record MapNodePrototype(Map<String, Attribute> attributeMap) implements NodePrototype {
    public MapNodePrototype() {
        this(Collections.emptyMap());
    }

    public MapNodePrototype(Map<String, Attribute> attributeMap) {
        this.attributeMap = attributeMap;
    }

    @Override
    public NodePrototype withString(String key, JavaString value) {
        var copy = new HashMap<>(attributeMap);
        copy.put(key, new StringAttribute(value));
        return new MapNodePrototype(copy);
    }

    @Override
    public Node complete(JavaString name) {
        return new MapNode(name, attributeMap);
    }

    @Override
    public NodePrototype withListOfStrings(String key, List<JavaString> values) {
        var copy = new HashMap<>(attributeMap);
        copy.put(key, new StringListAttribute(values));
        return new MapNodePrototype(copy);
    }

    @Override
    public Stream<Map.Entry<String, Attribute>> entries() {
        return this.attributeMap.entrySet().stream();
    }

    @Override
    public NodePrototype merge(NodePrototype other) {
        var copy = new HashMap<>(this.attributeMap);
        copy.putAll(other.entries().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        return new MapNodePrototype(copy);
    }
}
