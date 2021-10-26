package com.meti.feature;

import com.meti.Attribute;

public record IntegerNode(int value) implements Node {
    @Override
    public boolean is(Import.Type type) {
        return type == Node.Type.Integer;
    }

    @Override
    public Attribute apply(Attribute.Type type) {
        if (type == Attribute.Type.Value) return new IntegerAttribute(value);
        throw new UnsupportedOperationException();
    }
}
