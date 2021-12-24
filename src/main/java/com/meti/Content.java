package com.meti;

import com.meti.option.Option;
import com.meti.option.Some;

public record Content(Input input) implements Node {
    @Override
    public Option<Attribute> getValue() {
        return new Some<>(new InputAttribute(input));
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Content;
    }
}
