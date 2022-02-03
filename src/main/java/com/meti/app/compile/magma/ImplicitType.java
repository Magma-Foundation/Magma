package com.meti.app.compile.magma;

import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.Type;

public class ImplicitType implements Type {
    public static final ImplicitType ImplicitType_ = new ImplicitType();

    private ImplicitType() {
    }

    @Override
    public boolean is(Category category) {
        return category == Category.Implicit;
    }

    @Override
    public JSONNode toJSON() {
        return new ObjectNode().addObject("implicit", true);
    }
}
