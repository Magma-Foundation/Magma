package com.meti.app.compile.node;

import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.attribute.OutputAttribute;
import com.meti.app.compile.text.Output;

public record OutputNode(Output output) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new OutputAttribute(output);
        throw new AttributeException(type);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Output;
    }
}
