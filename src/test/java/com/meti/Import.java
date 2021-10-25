package com.meti;

public record Import(String value) implements Node {
    @Override
    public boolean is(Type type) {
        return type == Type.Import;
    }

    @Override
    public Attribute apply(Attribute.Type type) {
        if (type == Attribute.Type.Value) return new StringAttribute(value);
        else throw new UnsupportedOperationException();
    }

    enum Type {
        Integer, Import
    }
}