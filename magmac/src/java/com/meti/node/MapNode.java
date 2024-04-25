package com.meti.node;

import com.meti.collect.JavaString;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapNode implements Node {
    private final JavaString name;
    private final Map<String, Attribute> attributeMap;

    public MapNode(JavaString name, Map<String, Attribute> attributeMap) {
        this.name = name;
        this.attributeMap = attributeMap;
    }

    public static NodePrototype Builder() {
        return new MapNodePrototype(Collections.emptyMap());
    }

    @Override
    public Option<Attribute> apply(String name) {
        if (attributeMap.containsKey(name)) {
            return new Some<>(attributeMap.get(name));
        } else {
            return new None<>();
        }
    }

    @Override
    public Option<Node> with(String name, Attribute attribute) {
        if (attributeMap.containsKey(name)) {
            var copy = new HashMap<>(attributeMap);
            copy.put(name, attribute);
            return new Some<>(new MapNode(this.name, copy));
        } else {
            return new None<>();
        }
    }

}
