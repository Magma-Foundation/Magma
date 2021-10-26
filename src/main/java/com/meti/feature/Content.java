package com.meti.feature;

import com.meti.Input;
import com.meti.attribute.Attribute;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record Content(Input value) implements Node {
    @Override
    public Option<Attribute> apply(Attribute.Type type) {
        return type == Attribute.Type.Value
                ? new Some<>(new InputAttribute(value))
                : new None<>();
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Content;
    }
}
