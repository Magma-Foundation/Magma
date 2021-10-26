package com.meti.feature;

import com.meti.Attribute;

public record Import(String value) implements Node {
    @Override
    public boolean is(Node.Type type) {
        return type == Type.Import;
    }

    @Override
    public Attribute apply(Attribute.Type type) {
        if (type == Attribute.Type.Value) return new StringAttribute(value);
        else throw new UnsupportedOperationException();
    }
}