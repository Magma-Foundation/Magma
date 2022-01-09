package com.meti.compile;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.node.Node;

record Return(Node value) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if(type == Attribute.Type.Value) return new NodeAttribute(value);
        throw new AttributeException(type);
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return new Return(attribute.asNode());
    }

    @Override
    public boolean is(Type type) {
        return type == Node.Type.Return;
    }
}
