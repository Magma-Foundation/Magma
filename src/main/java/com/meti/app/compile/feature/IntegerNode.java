package com.meti.app.compile.feature;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.IntegerAttribute;

public record IntegerNode(int value) implements Node {
    @Override
    public Option<Attribute> apply(Attribute.Type type) {
        return type == Attribute.Type.Value ?
                new Some<>(new IntegerAttribute(value)) :
                new None<>();
    }

    @Override
    public boolean is(Import.Type type) {
        return type == Node.Type.Integer;
    }
}
