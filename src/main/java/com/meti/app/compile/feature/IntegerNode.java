package com.meti.app.compile.feature;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.IntegerAttribute;

public record IntegerNode(int value) implements Node {
    @Override
    public boolean is(Import.Type type) {
        return type == Node.Type.Integer;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new IntegerAttribute(value);
        throw new AttributeException("Unknown type: " + type);
    }
}
