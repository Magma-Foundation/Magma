package com.meti.attribute;

import com.meti.Node;

public record NodeAttribute(Node node) implements Attribute {
    @Override
    public Node asNode() {
        return node;
    }
}
