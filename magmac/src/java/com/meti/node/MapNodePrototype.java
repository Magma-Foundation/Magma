package com.meti.node;

import com.meti.collect.JavaString;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
