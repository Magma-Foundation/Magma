package com.meti.compile;

import com.meti.api.collect.List;

public record NodeListAttribute(List<? extends Node> values) implements Attribute {
    public static Attribute from(List<? extends Node> values) {
        return new NodeListAttribute(values);
    }
}
