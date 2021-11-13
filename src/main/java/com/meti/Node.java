package com.meti;

import java.util.stream.Stream;

public interface Node {
    Attribute apply(Attribute.Type type);

    boolean is(Type type);

    default Stream<Attribute.Type> stream(Attribute.Group group) {
        return Stream.empty();
    }

    default Node with(Node value) {
        return this;
    }

    enum Type {
        Int,
        Content, Return
    }
}
