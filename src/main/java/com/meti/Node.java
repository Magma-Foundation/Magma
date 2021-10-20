package com.meti;

import java.util.List;

public interface Node {
    default String name() {
        throw new UnsupportedOperationException();
    }

    boolean is(Type type);

    default String value() {
        throw new UnsupportedOperationException("Cannot render node of type: " + getClass());
    }

    default List<? extends Node> members() {
        throw new UnsupportedOperationException();
    }

    default String type() {
        throw new UnsupportedOperationException();
    }

    default Node body() {
        throw new UnsupportedOperationException();
    }

    default Node identity() {
        throw new UnsupportedOperationException();
    }

    enum Type {
        Structure,
        Function, Import
    }
}
