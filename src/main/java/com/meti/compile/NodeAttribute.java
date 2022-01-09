package com.meti.compile;

import com.meti.Attribute;

public record NodeAttribute(Node node) implements Attribute {
    @Override
    public Node asNode() {
        return node;
    }
}
