package com.meti.compile.attribute;

import com.meti.compile.Node;

public record NodeAttribute(Node value) implements Attribute {
    @Override
    public Node asNode() {
        return value;
    }
}
