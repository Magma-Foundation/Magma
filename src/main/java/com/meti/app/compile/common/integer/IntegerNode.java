package com.meti.app.compile.common.integer;

import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.IntegerAttribute;

public record IntegerNode(int value) implements Node {
    @Override
    public boolean is(Category category) {
        return category == Node.Category.Integer;
    }

    @Override
    public Attribute apply(Attribute.Category category) throws AttributeException {
        if (category == Attribute.Category.Value) return new IntegerAttribute(value);
        throw new AttributeException(category);
    }

    @Override
    public JSONNode toJSON() {
        return new ObjectNode().addObject("value", value);
    }
}
