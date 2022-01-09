package com.meti.compile;

import com.meti.Attribute;
import com.meti.InputAttribute;

record Return(Node value) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if(type == Attribute.Type.Value) return new InputAttribute(getValueAsString());
        throw new AttributeException("No attribute exists of name: " + type);
    }

    public Node getValueAsNode() {
        return value;
    }

    private Input getValueAsString() throws AttributeException {
        throw new AttributeException("Node has no value as string.");
    }

    @Override
    public boolean is(Type type) {
        return type == Node.Type.Return;
    }

    @Override
    public Node withValue(Node child) {
        return new Return(child);
    }
}
