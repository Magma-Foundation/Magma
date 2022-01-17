package com.meti.compile.magma;

import com.meti.compile.node.Node;

public class ImplicitType implements Node {
    public static final ImplicitType ImplicitType_ = new ImplicitType();

    private ImplicitType() {
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Implicit;
    }
}
