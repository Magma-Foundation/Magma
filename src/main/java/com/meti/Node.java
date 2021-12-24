package com.meti;

import com.meti.option.Option;

public interface Node {
    Option<Attribute> getValue();

    boolean is(Type type);

    default Node with(Node child) {
        return this;
    }

    enum Type {
        Integer,
        Content, Return
    }
}
