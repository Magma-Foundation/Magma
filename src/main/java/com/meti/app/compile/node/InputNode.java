package com.meti.app.compile.node;

import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.InputAttribute;
import com.meti.app.compile.text.Input;

public record InputNode(Input input) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new InputAttribute(input);
        throw new AttributeException(this, type);
    }

    @Override
    public boolean is(Type type) {
        return type == Node.Type.Input;
    }
}
