package com.meti.app.compile.feature;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.NodeAttribute;

public record Return(Node value) implements Node {
    @Override
    public boolean is(Type type) {
        return type == Type.Return;
    }

    @Override
    public Option<Attribute> apply(Attribute.Type type) {
        return type == Attribute.Type.Value ?
                new Some<>(new NodeAttribute(value)) :
                new None<>();
    }
}
