package com.meti.compile.common.bool;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.BooleanAttribute;
import com.meti.compile.node.Node;

public enum Boolean implements Node {
    True(true),
    False(false);

    private final boolean state;


    Boolean(boolean state) {
        this.state = state;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new BooleanAttribute(state);
        throw new AttributeException(type);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Boolean;
    }
}
