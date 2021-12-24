package com.meti;

import com.meti.option.Option;
import com.meti.option.Some;

public record IntegerNode(int value) implements Node {
    @Override
    public Option<Attribute> getValue() {
        return new Some<>(new IntegerAttribute(value));
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Integer;
    }
}
