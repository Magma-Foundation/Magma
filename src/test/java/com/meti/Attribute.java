package com.meti;

public interface Attribute {
    default String asString() {
        throw new UnsupportedOperationException();
    }

    default int asInteger() {
        throw new UnsupportedOperationException();
    }

    enum Type {
        Value
    }
}
