package com.meti.app.compile.feature;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;

public record Return(Node value) implements Node {
    @Override
    public boolean is(Type type) {
        return type == Type.Return;
    }

    public Option<Attribute> apply1(Attribute.Type type) {
        return type == Attribute.Type.Value ?
                new Some<>(new NodeAttribute(value)) :
                new None<>();
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new NodeAttribute(value);
        throw new AttributeException("Unknown type: " + type);
    }
}
