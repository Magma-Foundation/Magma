package com.meti.app.compile.node.attribute;

import com.meti.api.json.JSONNode;
import com.meti.app.compile.node.Node;

public record NodeAttribute(Node node) implements Attribute {
    @Override
    public Node asNode() {
        return node;
    }

    @Override
    public JSONNode toJSON() {
        throw new UnsupportedOperationException(getClass() + " cannot be converted into JSON yet.");
    }
}
