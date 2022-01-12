package com.meti.compile.magma;

import com.meti.compile.node.Node;

public class ImplicitType implements Node {
    @Override
    public boolean is(Type type) {
        return type == Type.Implicit;
    }
}
