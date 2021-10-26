package com.meti.app.compile.node;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.InputAttribute;

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
