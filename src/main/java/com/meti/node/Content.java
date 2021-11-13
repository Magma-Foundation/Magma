package com.meti.node;

import com.meti.Attribute;
import com.meti.StringAttribute;

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
