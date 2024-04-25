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

    public static Builder Builder(JavaString name) {
        return new Builder(name, Collections.emptyMap());
    }

    @Override
    public Option<Attribute> apply(String name) {
        if (attributeMap.containsKey(name)) {
            return new Some<>(attributeMap.get(name));
        } else {
            return new None<>();
        }
    }

    public record Builder(JavaString name, Map<String, Attribute> attributeMap) {
        public Builder withString(String key, JavaString value) {
            var copy = new HashMap<>(attributeMap);
            copy.put(key, new StringAttribute(value));
            return new Builder(name, copy);
        }

        public MapNode complete() {
            return new MapNode(name, attributeMap);
        }
    }
}
