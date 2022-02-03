package com.meti.app.compile.node.attribute;

import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.text.Output;

public record OutputAttribute(Output value) implements Attribute {
    @Override
    public Output asOutput() {
        return value;
    }

    @Override
    public JSONNode toJSON() {
        return new ObjectNode().addString("output", value.compute());
    }
}
