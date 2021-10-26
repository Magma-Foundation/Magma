package com.meti.feature;

import com.meti.attribute.Attribute;
import com.meti.attribute.IntegerAttribute;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

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
