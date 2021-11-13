package com.meti;

public interface Node {
    Attribute apply();

    boolean is(Type type);

    default Node with(Node value) {
        return this;
    }

    enum Type {
        Int,
        Content, Return
    }
}
