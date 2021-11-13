package com.meti.node;

import com.meti.attribute.Attribute;
import com.meti.attribute.StringAttribute;

public record Content(String value) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) {
        return new StringAttribute(value);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Content;
    }
}
