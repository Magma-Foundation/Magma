package com.meti.feature;

import com.meti.Attribute;

public interface Node {
    boolean is(Node.Type type);

    enum Type {
        Integer, Return, Import
    }

    Attribute apply(Attribute.Type type);
}
