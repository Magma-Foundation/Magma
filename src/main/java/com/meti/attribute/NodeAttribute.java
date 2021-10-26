package com.meti.attribute;

import com.meti.feature.Node;

public record NodeAttribute(Node value) implements Attribute {
    @Override
    public Node asNode() {
        return value;
    }
}
