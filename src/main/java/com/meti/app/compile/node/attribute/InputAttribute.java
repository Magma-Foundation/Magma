package com.meti.app.compile.node.attribute;

import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.Output;

public record InputAttribute(Input value) implements Attribute {
    @Override
    public Input asInput() {
        return value;
    }

    @Override
    public Output asOutput() throws AttributeException {
        return value.toOutput();
    }

    @Override
    public JSONNode toJSON() {
        return new ObjectNode().addString("input", value.toOutput().compute());
    }
}
