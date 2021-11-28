package com.meti.app.attribute;

import com.meti.app.node.Node;

public record NodeAttribute(Node value) implements Attribute {
    @Override
    public Node asNode() {
        return value;
    }
}
