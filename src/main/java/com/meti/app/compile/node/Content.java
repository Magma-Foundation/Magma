package com.meti.app.compile.node;

import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.InputAttribute;

public record Content(Input input) implements Node {
    @Override
    public boolean is(Type type) {
        return type == Type.Content;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new InputAttribute(input);
        throw new AttributeException("Unknown type: " + type);
    }
}
