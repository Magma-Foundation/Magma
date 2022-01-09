package com.meti.compile.common.integer;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.IntegerAttribute;
import com.meti.compile.node.Node;

record IntegerNode(int value) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new IntegerAttribute(value);
        throw new AttributeException(type);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Integer;
    }
}
