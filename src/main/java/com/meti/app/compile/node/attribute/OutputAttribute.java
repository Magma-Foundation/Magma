package com.meti.app.compile.node.attribute;

import com.meti.api.json.JSONNode;
import com.meti.app.compile.text.Output;

public record OutputAttribute(Output value) implements Attribute {
    @Override
    public Output asOutput() {
        return value;
    }

    @Override
    public JSONNode toJSON() {
        throw new UnsupportedOperationException(getClass() + " cannot be converted into JSON yet.");
    }
}
