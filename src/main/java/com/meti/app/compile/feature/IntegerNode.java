package com.meti.app.compile.feature;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.IntegerAttribute;

public record IntegerNode(int value) implements Node {
    public Option<Attribute> apply1(Attribute.Type type) {
        return type == Attribute.Type.Value ?
                new Some<>(new IntegerAttribute(value)) :
                new None<>();
    }

    @Override
    public boolean is(Import.Type type) {
        return type == Node.Type.Integer;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new IntegerAttribute(value);
        throw new AttributeException("Unknown type: " + type);
    }
}
