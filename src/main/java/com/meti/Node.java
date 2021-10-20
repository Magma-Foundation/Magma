package com.meti;

public interface Node {
    Attribute apply(Attribute.Group group);

    boolean is(Type type);

    enum Type {
        Import,
        Field,
        Function,
        Structure,
    }
}
