package com.meti.app.node;

import com.meti.app.attribute.Attribute;
import com.meti.app.attribute.IntAttribute;

public record IntegerNode(int value) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) {
        return new IntAttribute(value);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Int;
    }
}
