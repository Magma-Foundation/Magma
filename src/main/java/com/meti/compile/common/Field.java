package com.meti.compile.common;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.TextAttribute;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.node.Text;
import com.meti.compile.node.Node;

public record Field(Text name, Node type) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Name -> new TextAttribute(name);
            case Type -> new NodeAttribute(this.type);
            default -> throw new AttributeException(type);
        };
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return new Field(name, attribute.asNode());
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Field;
    }
}
