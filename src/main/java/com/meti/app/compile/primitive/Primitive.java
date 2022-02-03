package com.meti.app.compile.primitive;

import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.Type;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.InputAttribute;
import com.meti.app.compile.text.RootText;

public enum Primitive implements Type {
    Bool,
    Void;

    @Override
    public boolean is(Category category) {
        return category == Category.Primitive;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Name) return new InputAttribute(new RootText(name()));
        throw new AttributeException(type);
    }

    @Override
    public JSONNode toJSON() {
        return new ObjectNode().addString("value", name());
    }

    @Override
    public String toString() {
        return "\"" + name() + "\"";
    }
}
