package com.meti.app.compile.node;

import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.InputAttribute;
import com.meti.app.compile.text.Input;

public final class InputNode extends AbstractNode {
    private final Input input;

    public InputNode(Input input) {
        this.input = input;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new InputAttribute(input);
        throw new AttributeException(this, type);
    }

    @Override
    public JSONNode toJSON() {
        return new ObjectNode().addString("input", input.toOutput().compute());
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Input;
    }
}
