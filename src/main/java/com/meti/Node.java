package com.meti;

public interface Node {
    default String value() {
        throw new UnsupportedOperationException();
    }
}
