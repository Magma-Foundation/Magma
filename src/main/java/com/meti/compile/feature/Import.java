package com.meti.compile.feature;

import com.meti.compile.Node;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.StringAttribute;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

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