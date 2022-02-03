package com.meti.app.compile.scope;

import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.AbstractNode;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.InputAttribute;
import com.meti.app.compile.text.Input;

public final class StructureType extends AbstractNode {
    private final Input name;

    public StructureType(Input name) {
        this.name = name;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Name) return new InputAttribute(name);
        throw new AttributeException(type);
    }

    @Override
    public boolean is(Category category) {
        return category == Category.Structure;
    }

    @Override
    public JSONNode toJSON() {
        return new ObjectNode().addString("name", name.toOutput().compute());
    }
}
