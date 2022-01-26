package com.meti.app.compile.node.attribute;

import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.text.Input;

public record InputAttribute(Input value) implements Attribute {
    @Override
    public Input asInput() {
        return value;
    }

    @Override
    public JSONNode toJSON() {
        return new ObjectNode().addString("input", value.toOutput().compute());
    }
}
