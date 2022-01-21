package com.meti.app.compile.scope;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.InputAttribute;
import com.meti.app.compile.text.Input;

public record StructureType(Input name) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Name) return new InputAttribute(name);
        throw new AttributeException(type);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Structure;
    }
}
