package com.meti.compile.node;

import com.meti.compile.node.attribute.Attribute;
import com.meti.compile.node.attribute.InputAttribute;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record Content(Input input) implements Node {
    @Override
    public boolean is(Type type) {
        return type == Type.Content;
    }

    @Override
    public Option<Attribute> apply(Attribute.Type type) {
        return type == Attribute.Type.Value
                ? new Some<>(new InputAttribute(input))
                : new None<>();
    }
}
