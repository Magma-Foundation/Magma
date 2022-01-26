package com.meti.app.compile.scope;

import com.meti.api.json.JSONNode;
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

    @Override
    public JSONNode toJSON() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "{" +
               "\n\t\"name\":" + name +
               '}';
    }
}
