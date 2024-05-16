package com.meti.compile;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.node.Attribute;
import com.meti.node.StringAttribute;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public record Node(Map<String, Attribute> attributes) {
    public Node() {
        this(Collections.emptyMap());
    }

    public Node withSlice(String key, String value) {
        var copy = new HashMap<>(attributes());
        copy.put(key, new StringAttribute(value));
        return new Node(copy);
    }

    public Node withString(String key, JavaString value) {
        return withSlice(key, value.value());
    }

    public Option<Attribute> apply(String key) {
        if (attributes.containsKey(key)) {
            return new Some<>(attributes.get(key));
        } else {
            return new None<>();
        }
    }
}