package com.meti;

public class IntegerNode implements Node {
    private final int value;

    public IntegerNode(int value) {
        this.value = value;
    }

    @Override
    public boolean is(Import.Type type) {
        return type == Import.Type.Integer;
    }

    @Override
    public Attribute apply(Attribute.Type type) {
        if (type == Attribute.Type.Value) return new IntegerAttribute(value);
        throw new UnsupportedOperationException();
    }
}
