package com.meti.app.compile.common.integer;

import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.IntegerAttribute;

public record IntegerNode(int value) implements Node {
    @Override
    public boolean is(Role role) {
        return role == Role.Integer;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new IntegerAttribute(value);
        throw new AttributeException(type);
    }

    @Override
    public JSONNode toJSON() {
        return new ObjectNode().addObject("value", value);
    }
}
