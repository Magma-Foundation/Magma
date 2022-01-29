package com.meti.app.compile.node;

import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;

public class EmptyNode extends AbstractNode {
    public static final EmptyNode EmptyNode_ = new EmptyNode();

    private EmptyNode() {
    }

    @Override
    public JSONNode toJSON() {
        return new ObjectNode().addObject("empty", true);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Empty;
    }
}
