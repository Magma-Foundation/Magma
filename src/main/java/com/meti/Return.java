package com.meti;

import com.meti.feature.Node;
import com.meti.feature.NodeAttribute;

public record Return(Node value) implements Node {
    @Override
    public boolean is(Type type) {
        return type == Type.Return;
    }

    @Override
    public Attribute apply(Attribute.Type type) {
        if (type == Attribute.Type.Value) return new NodeAttribute(value);
        throw new UnsupportedOperationException();
    }
}
