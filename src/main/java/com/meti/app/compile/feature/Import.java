package com.meti.app.compile.feature;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.StringAttribute;

public record Import(String value) implements Node {
    @Override
    public boolean is(Node.Type type) {
        return type == Type.Import;
    }

    public Option<Attribute> apply1(Attribute.Type type) {
        return type == Attribute.Type.Value ?
                new Some<>(new StringAttribute(value)) :
                new None<>();
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new StringAttribute(value);
        throw new AttributeException("Unknown type: " + type);
    }
}