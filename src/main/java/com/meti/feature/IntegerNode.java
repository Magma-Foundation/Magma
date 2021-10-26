package com.meti.feature;

import com.meti.Attribute;
import com.meti.None;
import com.meti.Option;
import com.meti.Some;

public record IntegerNode(int value) implements Node {
    @Override
    public Option<Attribute> apply(Attribute.Type type) {
        return type == Attribute.Type.Value ?
                new Some<>(new IntegerAttribute(value)) :
                new None<>();
    }

    @Override
    public boolean is(Import.Type type) {
        return type == Node.Type.Integer;
    }
}
