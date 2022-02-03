package com.meti.app.compile.node.attribute;

import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;

public record BooleanAttribute(boolean value) implements Attribute {
    @Override
    public boolean asBoolean() {
        return value;
    }

    @Override
    public JSONNode toJSON() {
        return new ObjectNode().addObject("state", value);
    }
}
