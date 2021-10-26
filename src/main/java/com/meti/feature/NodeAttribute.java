package com.meti.feature;

import com.meti.Attribute;

public record NodeAttribute(Node value) implements Attribute {
    @Override
    public Node asNode() {
        return value;
    }
}
