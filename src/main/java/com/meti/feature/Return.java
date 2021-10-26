package com.meti.feature;

import com.meti.attribute.Attribute;
import com.meti.attribute.NodeAttribute;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record Return(Node value) implements Node {
    @Override
    public boolean is(Type type) {
        return type == Type.Return;
    }

    @Override
    public Option<Attribute> apply(Attribute.Type type) {
        return type == Attribute.Type.Value ?
                new Some<>(new NodeAttribute(value)) :
                new None<>();
    }
}
