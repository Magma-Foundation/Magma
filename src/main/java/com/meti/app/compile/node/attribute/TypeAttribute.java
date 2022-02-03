package com.meti.app.compile.node.attribute;

import com.meti.api.json.JSONException;
import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;

public class TypeAttribute extends AbstractAttribute implements Attribute {
    private final com.meti.app.compile.node.Type value;

    public TypeAttribute(com.meti.app.compile.node.Type value) {
        this.value = value;
    }

    @Override
    public JSONNode toJSON() throws JSONException {
        return new ObjectNode().addJSONable("type", value);
    }

    @Override
    public com.meti.app.compile.node.Type asType() {
        return value;
    }
}
