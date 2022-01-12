package com.meti.compile.common.string;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.TextAttribute;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;

public record String(Text value) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Value) return new TextAttribute(value);
        throw new AttributeException(type);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.String;
    }
}
