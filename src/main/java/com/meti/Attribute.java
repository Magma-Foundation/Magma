package com.meti;

import com.meti.feature.Node;

public interface Attribute {
    default String asString() {
        throw new UnsupportedOperationException();
    }

    default int asInteger() {
        throw new UnsupportedOperationException();
    }

    default Node asNode() {
        throw new UnsupportedOperationException();
    }

    enum Type {
        Value
    }
}
