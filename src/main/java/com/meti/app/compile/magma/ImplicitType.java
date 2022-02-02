package com.meti.app.compile.magma;

import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.Node;

public class ImplicitType implements Node {
    public static final ImplicitType ImplicitType_ = new ImplicitType();

    private ImplicitType() {
    }

    @Override
    public boolean is(Role role) {
        return role == Role.Implicit;
    }

    @Override
    public JSONNode toJSON() {
        return new ObjectNode().addObject("implicit", true);
    }
}
