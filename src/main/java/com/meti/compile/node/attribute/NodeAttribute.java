package com.meti.compile.node.attribute;

import com.meti.compile.node.Node;

public record NodeAttribute(Node value) implements Attribute {
    @Override
    public Node asNode() {
        return value;
    }
}
