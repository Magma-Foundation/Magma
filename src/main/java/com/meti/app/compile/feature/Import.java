package com.meti.app.compile.feature;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.StringAttribute;

public record Import(String value) implements Node {
    @Override
    public boolean is(Node.Type type) {
        return type == Type.Import;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new StringAttribute(value);
        throw new AttributeException("Unknown type: " + type);
    }
}