package com.meti.compile.attribute;

import com.meti.compile.node.Node;

public record NodeAttribute(Node node) implements Attribute {
    @Override
    public Node asNode() {
        return node;
    }
}
