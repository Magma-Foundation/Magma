package com.meti.app.compile.node;

import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.attribute.InputAttribute;
import com.meti.app.compile.text.Input;

public record InputNode(Input input) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new InputAttribute(input);
        throw new AttributeException(type);
    }

    @Override
    public boolean is(Type type) {
        return type == Node.Type.Input;
    }
}
