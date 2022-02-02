package com.meti.app.compile.node.attribute;

import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.Node;

public final class NodeAttribute extends AbstractAttribute {
    private final Node node;

    public NodeAttribute(Node node) {
        this.node = node;
    }

    @Override
    public JSONNode toJSON() {
        return new ObjectNode().addObject("node", node);
    }

    @Override
    public Node asNode() {
        return node;
    }
}
