package com.meti.app.compile.node;

import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.OutputAttribute;
import com.meti.app.compile.text.Output;

public record OutputNode(Output output) implements Node {
    @Override
    public boolean is(Role role) {
        return role == Role.Output;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new OutputAttribute(output);
        throw new AttributeException(type);
    }

    @Override
    public JSONNode toJSON() {
        return new ObjectNode().addString("output", output.compute());
    }
}
