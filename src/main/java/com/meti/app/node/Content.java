package com.meti.app.node;

import com.meti.app.attribute.Attribute;
import com.meti.app.attribute.StringAttribute;

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
