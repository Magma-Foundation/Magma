package com.meti.app.compile.node.attribute;

import com.meti.api.json.JSONNode;

public record IntegerAttribute(int value) implements Attribute {

    @Override
    public int asInteger() {
        return value;
    }

    @Override
    public JSONNode toJSON() {
        throw new UnsupportedOperationException(getClass() + " cannot be converted into JSON yet.");
    }
}
