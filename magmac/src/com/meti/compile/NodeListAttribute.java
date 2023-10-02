package com.meti.compile;

import com.meti.api.collect.List;

public record NodeListAttribute(List<Node> values) implements Attribute {
    public static Attribute from(List<Node> values) {
        return new NodeListAttribute(values);
    }
}
