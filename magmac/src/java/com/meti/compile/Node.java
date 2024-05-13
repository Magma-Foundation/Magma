package com.meti.compile;

import com.meti.node.Attribute;
import com.meti.node.StringAttribute;

import java.util.HashMap;
import java.util.Map;

public record Node(Map<String, Attribute> node) {
    Node withString(String key, String value) {
        var copy = new HashMap<>(node());
        copy.put(key, new StringAttribute(value));
        return new Node(copy);
    }
}