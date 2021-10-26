package com.meti.feature;

import com.meti.Attribute;
import com.meti.None;
import com.meti.Option;
import com.meti.Some;

public record Import(String value) implements Node {
    @Override
    public boolean is(Node.Type type) {
        return type == Type.Import;
    }

    @Override
    public Option<Attribute> apply(Attribute.Type type) {
        return type == Attribute.Type.Value ?
                new Some<>(new StringAttribute(value)) :
                new None<>();
    }
}