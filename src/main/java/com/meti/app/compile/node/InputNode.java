package com.meti.app.compile.node;

import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.InputAttribute;
import com.meti.app.compile.text.Input;

import java.util.Objects;

public class InputNode extends AbstractNode {
    private final Input input;

    public InputNode(Input input) {
        this.input = input;
    }

    @Override
    public int hashCode() {
        return Objects.hash(input);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InputNode inputNode)) return false;
        return Objects.equals(input, inputNode.input);
    }

    @Override
    public boolean is(Role role) {
        return role == Role.Input;
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
}
